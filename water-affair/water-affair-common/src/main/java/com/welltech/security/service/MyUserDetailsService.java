package com.welltech.security.service;

import com.welltech.security.dao.LoginUserDao;

import com.welltech.security.entity.WtRole;
import com.welltech.security.entity.WtUser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    LoginUserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username){ //重写loadUserByUsername 方法获得 userdetails 类型用户

        WtUser user = userDao.findByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 
//        if(user.xx==xx){throw new LockedException("帐户被锁");}
//        if(user.xx==xx){throw new CredentialExpiredException("密码过期");}
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //用于添加用户的权限。
        for(WtRole role:user.getRoles())
        {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            System.out.println("用户角色为:"+role.getRoleName());
        }
//        User returnUser = null;
//        try{
//        	returnUser = new User(user.getUsername(),user.getPassword(), authorities);
//        }catch(BadCredentialsException bce){
//        	System.out.println("密码错误");
//        }
		return new User(user.getUsername(),user.getPassword(), authorities);
    }
}
