package com.welltech.security.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.welltech.common.util.MD5Util;
import com.welltech.security.service.MyAuthenticationProvider;
import com.welltech.security.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource
	private MyAuthenticationProvider provider;
	
//    @Autowired  
    private MySecurityFilter mySecurityFilter;
    
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

	@Bean
	UserDetailsService customUserService() { // 注册UserDetailsService 的bean
		return new MyUserDetailsService();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
//				.addFilterBefore(mySecurityFilter, MySecurityFilter.class)//在正确的位置添加我们自定义的过滤器  
				.authorizeRequests()
				.antMatchers("/doc/**", "/css/**", "/img/**", "/images/**", "/email_templates/**", "/font-awesome/**",
						"/fonts/**", "/js/**", "/locales/**")
				.permitAll()
				.anyRequest()
				.authenticated()
			.and()
				.formLogin()
				.failureUrl("/loginError")
				.loginPage("/login")
				.defaultSuccessUrl("/index", true)
				.permitAll()
				.successHandler(loginSuccessHandler)
			.and()
				.logout()
				.permitAll()
				.logoutUrl("/logout")
				// .antMatchers("/admin/**").hasRole("ROLE_ADMIN")
				.logoutSuccessUrl("/login")
				.invalidateHttpSession(true)
				.deleteCookies("aid")
			.and()
				.rememberMe()
				.tokenValiditySeconds(1209600);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// 将验证过程交给自定义验证工具
		// auth.authenticationProvider(provider);
		// auth.inMemoryAuthentication().withUser("admin").password("123456").roles("USER");
		auth.userDetailsService(customUserService()).passwordEncoder(new PasswordEncoder() {

			@Override
			public String encode(CharSequence rawPassword) {
				return MD5Util.encode((String) rawPassword);
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return encodedPassword.equals(MD5Util.encode((String) rawPassword));
			}
		}); // user Details Service验证
	}

}
