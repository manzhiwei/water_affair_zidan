/**
 * 
 */
package com.welltech.security.config;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.welltech.common.util.Tree;
import com.welltech.dao.sysSetting.OperatorManageDao;
import com.welltech.entity.WtMenu;
import com.welltech.security.dao.LoginUserDao;
import com.welltech.security.entity.WtUser;
import com.welltech.service.impl.LoginServiceImpl;

/**
 * Created by Zhujia at 2017年8月4日 下午4:56:23
 */
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Autowired
	private LoginServiceImpl loginServiceImpl;
	
	@Autowired
	private LoginUserDao loginUserDao;

	@Override    
    public void onAuthenticationSuccess(HttpServletRequest request,    
            HttpServletResponse response, Authentication authentication) throws IOException,    
            ServletException {    
		WtUser user = new WtUser();  
        //获得授权后可得到用户信息   可使用SUserService进行数据库操作  
		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetails) {
			user.setUsername(((UserDetails) principal).getUsername());
			user.setAuthorities(((UserDetails) principal).getAuthorities());
		}
		String ip = getIpAddress(request);
//       Set<SysRole> roles = userDetails.getSysRoles();
        //输出登录提示信息    
        System.out.println("管理员 " + user.getUsername() + " 登录");    
        System.out.println("IP :"+ip);  
        //保存登录操作
        user = saveSession(request, user);
        //保存菜单
        saveMenuSession(request, user);
        loginServiceImpl.saveLoginInfo(user.getId(),ip);
        super.onAuthenticationSuccess(request, response, authentication);
    }    
      
    /**
	 * @param user
	 */
	private void saveMenuSession(HttpServletRequest request, WtUser user) {
		List<WtMenu> nodes = loginServiceImpl.getMenuByUserId(user.getId());
		Tree tree = new Tree(nodes); 
		String html = tree.buildTree();
		request.getSession().setAttribute("menu", html);
	}

	/**
	 * @param request 
     * @param user
     * @return 
	 */
	private WtUser saveSession(HttpServletRequest request, WtUser user) {
		WtUser wtUser = loginUserDao.findByUserName(user.getUsername());
		request.getSession().setAttribute("user", wtUser);
		return wtUser;
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
}
