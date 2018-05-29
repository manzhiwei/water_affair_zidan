package com.welltech.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.welltech.common.util.OperateCodeEnum;
import com.welltech.service.history.OperateHistoryServiceImpl;

/**
 * Created by peter on 17-7-13.
 */
@Controller
public class RootController {
	
	@Autowired
	OperateHistoryServiceImpl operateHistoryServiceImpl;

	@RequestMapping(value = {"/"})
	public RedirectView redirecIndex(){
		RedirectView redirectView = new RedirectView("/index");
		return redirectView;
	}
	
    @RequestMapping(value = { "/login" })
    public String login(HttpServletRequest request) {
    	System.out.println("1.username:"+request.getParameter("username"));
        return "login";
    }

    @RequestMapping(value = { "/loginError" })
    public String loginError(Model model) {
    	model.addAttribute("loginError", true);
    	return "login";
    }

    @RequestMapping(value = {"/index"})
    public String index(HttpServletRequest request){
    	HttpSession session = request.getSession();
    	User user = (User) ((SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getPrincipal();
    	return  "redirect:/map.html";
    }

    @RequestMapping(value = { "/logoutRedirect" }, method = RequestMethod.GET)
    public String logoutRedirect(HttpServletRequest request) {
    	com.welltech.security.entity.WtUser user = (com.welltech.security.entity.WtUser) request.getSession().getAttribute("user");
    	operateHistoryServiceImpl.addOperate(OperateCodeEnum.OPERATE_06, user.getId(), user.getUsername());
        return "redirect:/logout";
    }
    
    @RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
    public String logout(){
    	return "login";
    }
    
    @RequestMapping(value = { "/error" }, method = RequestMethod.GET)
    public String error(){
        return "500";
    }

}
