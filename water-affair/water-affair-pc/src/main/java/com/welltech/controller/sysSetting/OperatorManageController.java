/**
 * 
 */
package com.welltech.controller.sysSetting;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welltech.common.util.ErrorCodeEnum;
import com.welltech.common.util.MD5Util;
import com.welltech.common.util.OperateCodeEnum;
import com.welltech.common.util.ReturnEntity;
import com.welltech.common.util.Tree;
import com.welltech.dto.WtUserDto;
import com.welltech.entity.WtMenu;
import com.welltech.entity.WtRole;
import com.welltech.entity.WtUser;
import com.welltech.service.history.OperateHistoryServiceImpl;
import com.welltech.service.impl.LoginServiceImpl;
import com.welltech.service.sysSetting.OperatorManageService;

/**
 * Created by Zhujia at 2017年7月27日 上午9:57:15
 */
@Controller
@RequestMapping("/syssetting/om")
public class OperatorManageController {

	@Autowired
	private OperatorManageService operatorManageService;
	
	@Autowired
	private OperateHistoryServiceImpl operateHistoryServiceImpl;
	
	@Autowired
	private LoginServiceImpl loginServiceImpl;
	
	/**
	 * 操作员管理
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "page" })
    public String operatorManage(Map<String,Object> map) {
		List<WtUserDto> userDto = operatorManageService.findAllUserRole();
		List<WtRole> roles = operatorManageService.findAllRole();
		map.put("users",userDto); 
		map.put("roles",roles); 
        return "sysSetting/operatorManage";
    }
	
	/**
	 * 操作员管理
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "getUserById" })
	@ResponseBody
	public ReturnEntity<WtUserDto> operatorManage(String id) {
		ReturnEntity<WtUserDto> re = new ReturnEntity<>();
		WtUserDto user = operatorManageService.findUserDtoById(id);
		if(null==user){
			re.setReturnCode(ErrorCodeEnum.ERROR_02.getCode());
			return re;
		}
		re.setReturnData(user);
		return re;
	}
	
	/**
	 * 添加操作员
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "addUser" })
	@ResponseBody
    public ReturnEntity<String> addUser(HttpServletRequest request, Map<String,Object> map,WtUser wtUser,String[] roleId,String roleName) {
		ReturnEntity<String> re = new ReturnEntity<>();
		com.welltech.security.entity.WtUser wtuser = (com.welltech.security.entity.WtUser) request.getSession().getAttribute("user");
		WtUser user = operatorManageService.findUserByUsername(wtUser.getUsername());
		if(null!=user && null==wtUser.getId()){
			//保存用户操作,但是用户名已存在
			re.setReturnCode(ErrorCodeEnum.ERROR_07.getCode());
			return re;
		}else if(null!=wtUser.getId()){
			//更新用户操作
			user.setUsername(wtUser.getUsername().trim());
			user.setRealName(wtUser.getRealName().trim());
			user.setCellphone(wtUser.getCellphone().trim());
			if(wtUser.getPassword()!=null&&!"".equals(wtUser.getPassword())) {
				user.setPassword(MD5Util.encode(wtUser.getPassword()));
			}
			operatorManageService.updateUser(user,roleId);
			operateHistoryServiceImpl.addOperate(OperateCodeEnum.OPERATE_05, wtuser.getId(), user.getUsername(),roleName);
			//更新菜单
			List<WtMenu> nodes = loginServiceImpl.getMenuByUserId(wtuser.getId());
			Tree tree = new Tree(nodes); 
			String html = tree.buildTree();
			request.getSession().setAttribute("menu", html);
		}else{
			operatorManageService.addUser(wtUser,roleId);
			operateHistoryServiceImpl.addOperate(OperateCodeEnum.OPERATE_04, wtuser.getId(), wtUser.getUsername(),roleName);
		}
		return re;
    }
	
	/**
	 * 删除用户
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "deleteUser" })
	@ResponseBody
    public ReturnEntity<String> deleteUser(Map<String,Object> map,String id) {
		ReturnEntity<String> re = new ReturnEntity<>();
		WtUser user = operatorManageService.findUserById(id);
		if(null==user){
			re.setReturnCode(ErrorCodeEnum.ERROR_02.getCode());
			return re;
		}
		//TODO 无法删除当前登录状态用户
		operatorManageService.deleteUser(id);
		return re;
    }
	
}
