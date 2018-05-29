/**
 * 
 */
package com.welltech.security.config;

import java.io.IOException;  
import java.util.Date;  
  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;  
import org.springframework.beans.factory.InitializingBean;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.dao.DataAccessException;  
import org.springframework.security.core.Authentication;  
import org.springframework.security.web.DefaultRedirectStrategy;  
import org.springframework.security.web.RedirectStrategy;  
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;  
import org.springframework.transaction.annotation.Transactional;  

import com.welltech.security.entity.WtUser;  
import com.welltech.security.dao.LoginUserDao;
  
/**
 * Created by Zhujia at 2017年8月4日 下午3:07:48
 */
@Component
public class SimpleLoginSuccessHandler implements AuthenticationSuccessHandler,InitializingBean {

	protected Log logger = LogFactory.getLog(getClass());  
    
    private String defaultTargetUrl="/index";  
      
    private boolean forwardToDestination = false;  
      
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();  
      
    @Autowired  
    private LoginUserDao loginUserDao;  
      
    /* (non-Javadoc) 
     * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication) 
     */  
    @Override  
    @Transactional(readOnly=false,propagation= Propagation.REQUIRED,rollbackFor={Exception.class})  
    public void onAuthenticationSuccess(HttpServletRequest request,  
            HttpServletResponse response, Authentication authentication)  
            throws IOException, ServletException {  
          
        this.saveLoginInfo(request, authentication);  
        
//        super.onAuthenticationSuccess(request, response, authentication);  //spring security 版本不一致
        if(this.forwardToDestination){  
            logger.info("Login success,Forwarding to "+this.defaultTargetUrl);  
              
            request.getRequestDispatcher(this.defaultTargetUrl).forward(request, response);  
        }else{  
            logger.info("Login success,Redirecting to "+this.defaultTargetUrl);  
              
            this.redirectStrategy.sendRedirect(request, response, this.defaultTargetUrl);  
        }  
    }  
      
    @Transactional(readOnly=false,propagation= Propagation.REQUIRED,rollbackFor={Exception.class})  
    public void saveLoginInfo(HttpServletRequest request,Authentication authentication){  
        WtUser user = (WtUser)authentication.getPrincipal();  
        try {  
            String ip = this.getIpAddress(request);  
            Date date = new Date();  
            //获取登录信息并进行保存
//            user.setLastLogin(date);  
//            user.setLoginIp(ip); 
//            loginUserDao.saveLoginInfo(user);
            WtUser wtUser = loginUserDao.findByUserName(user.getUsername());
            request.getSession().setAttribute("user", wtUser);
//            System.out.println("管理员 " + userDetails.getName() + " 登录"); 
        } catch (DataAccessException e) {  
            if(logger.isWarnEnabled()){  
                logger.info("无法更新用户登录信息至数据库");  
            }  
        }  
    }  
      
    public String getIpAddress(HttpServletRequest request){    
        String ip = request.getHeader("x-forwarded-for");    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("Proxy-Client-IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("WL-Proxy-Client-IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_CLIENT_IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getRemoteAddr();    
        }    
        return ip;    
    }  
  
    public void setDefaultTargetUrl(String defaultTargetUrl) {  
        this.defaultTargetUrl = defaultTargetUrl;  
    }  
  
    public void setForwardToDestination(boolean forwardToDestination) {  
        this.forwardToDestination = forwardToDestination;  
    }  
  
    /* (non-Javadoc) 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet() 
     */  
    @Override  
    public void afterPropertiesSet() throws Exception {  
//        if(StringUtils.isEmpty(defaultTargetUrl))  
//            throw new InitializationException("You must configure defaultTargetUrl");  
//          
    }    
      
}
