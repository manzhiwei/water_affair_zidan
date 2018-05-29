/**
 * 
 */
package com.welltech.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welltech.common.util.ErrorCodeEnum;
import com.welltech.common.util.MD5Util;
import com.welltech.common.util.ReturnEntity;
import com.welltech.security.entity.Msg;
import com.welltech.security.entity.WtUser;
import com.welltech.service.UserService;

/**
 * Created by Zhujia at 2017年7月24日 上午9:49:22
 */
@Controller
public class UserController {
	
	@Autowired
	UserService userService;

	@RequestMapping(value = { "/editPwd" })
	@ResponseBody
    public ReturnEntity<String> login(HttpServletRequest request,String oldPwd, String newPwd, String confirmPwd) {
		ReturnEntity<String> re = new ReturnEntity<String>();
		HttpSession session = request.getSession();
		WtUser user = (WtUser) ( session.getAttribute("user"));
		String encodePwd = userService.getUserInfoByUserId(user.getId().toString()).getPassword();
		if(!MD5Util.encode(oldPwd).equals(encodePwd)){	//原密码输入正确
			re.setReturnCode(ErrorCodeEnum.ERROR_01.getCode());
			return re;
		}
		userService.updatePwd(user.getId().toString(),MD5Util.encode(newPwd));
		return re;	//原密码不正确
	}
}
