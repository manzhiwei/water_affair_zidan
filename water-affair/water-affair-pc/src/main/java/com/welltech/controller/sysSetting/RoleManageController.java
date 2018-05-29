/**
 * 
 */
package com.welltech.controller.sysSetting;

import java.util.List;
import java.util.Map;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welltech.common.util.ErrorCodeEnum;
import com.welltech.common.util.OperateCodeEnum;
import com.welltech.common.util.ReturnEntity;
import com.welltech.common.util.Tree;
import com.welltech.dto.WtMenuDto;
import com.welltech.entity.WtMenu;
import com.welltech.entity.WtRole;
import com.welltech.entity.WtUser;
import com.welltech.service.history.OperateHistoryServiceImpl;
import com.welltech.service.impl.LoginServiceImpl;
import com.welltech.service.sysSetting.RoleManageService;

/**
 * Created by Zhujia at 2017年7月27日 上午9:57:15
 */
@Controller
@RequestMapping("/syssetting/rm")
public class RoleManageController {

	@Autowired
	private RoleManageService roleManageService;
	
	@Autowired
	OperateHistoryServiceImpl operateHistoryServiceImpl;
	
	@Autowired
	LoginServiceImpl loginServiceImpl;
	
	/**
	 * 角色管理
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "page" })
    public String roleManage(Map<String,Object> map) {
		List<WtRole> roles = roleManageService.findAllRole();
		map.put("roles",roles); 
        return "sysSetting/roleManage";
    }
	
	/**
	 * 获取角色菜单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/getRoleMenu" })
	@ResponseBody
    public ReturnEntity<List<WtMenuDto>> getRoleMenu(String roleId) {
		ReturnEntity<List<WtMenuDto>> re = new ReturnEntity<List<WtMenuDto>>();
		List<WtMenuDto> menus =  roleManageService.getRoleMenu(roleId);
		re.setReturnData(menus);
		return re;
    }
	
	/**
	 * 获取角色菜单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/getRoleById" })
	@ResponseBody
	public ReturnEntity<WtRole> getRoleById(String roleId) {
		ReturnEntity<WtRole> re = new ReturnEntity<WtRole>();
		WtRole role =  roleManageService.getRoleById(roleId);
		re.setReturnData(role);
		return re;
	}
	
	/**
	 * 新增角色
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/addRole" })
	@ResponseBody
    public ReturnEntity<String> addUser(WtRole wtRole,String[] menuId,HttpServletRequest request) {
		ReturnEntity<String> re = new ReturnEntity<>();
		com.welltech.security.entity.WtUser user = (com.welltech.security.entity.WtUser) request.getSession().getAttribute("user");
		if(null==wtRole.getId() || 0==wtRole.getId()){
			//没有ID 该操作为保存角色
			WtRole role = roleManageService.findRoleByRolename(wtRole.getRoleName());
			if(null!=role){//角色名不能重复
				re.setReturnCode(ErrorCodeEnum.ERROR_08.getCode());
				return re;
			}
			roleManageService.addRole(wtRole,menuId);
			operateHistoryServiceImpl.addOperate(OperateCodeEnum.OPERATE_07, user.getId(), wtRole.getRoleName());
		}else{
			//有ID 该操作为更新角色
			WtRole otherRole = roleManageService.findRoleByRolenameExpRoleId(wtRole);
			WtRole role = roleManageService.findRoleById(wtRole.getId().toString());
			if(null!=otherRole){
				re.setReturnCode(ErrorCodeEnum.ERROR_08.getCode());
				return re;
			}
			role.setRoleName(wtRole.getRoleName());
			role.setRoleDescription(wtRole.getRoleDescription());
			roleManageService.updateRole(role,menuId);
			//更新菜单
			List<WtMenu> nodes = loginServiceImpl.getMenuByUserId(user.getId());
			Tree tree = new Tree(nodes); 
			String html = tree.buildTree();
			request.getSession().setAttribute("menu", html);
			
			operateHistoryServiceImpl.addOperate(OperateCodeEnum.OPERATE_08, user.getId(), wtRole.getRoleName());
		}
		return re;
    }
	
	/**
	 * 修改角色
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/editRole" })
	@ResponseBody
    public ReturnEntity<String> editUser(Map<String,Object> map) {
		ReturnEntity<String> re = new ReturnEntity<>();
		return re;
    }
	
	/**
	 * 删除角色
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/deleteRole" })
	@ResponseBody
    public ReturnEntity<String> deleteUser(Map<String,Object> map,String id) {
		ReturnEntity<String> re = new ReturnEntity<>();
		WtRole role = roleManageService.findRoleById(id);
		if(null==role){
			re.setReturnCode(ErrorCodeEnum.ERROR_03.getCode());
			return re;
		}
		//TODO 无法删除当前登录状态用户
		roleManageService.deleteRole(id);
		return re;
    }
	
}
