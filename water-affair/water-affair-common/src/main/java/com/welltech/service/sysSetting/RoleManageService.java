/**
 * 
 */
package com.welltech.service.sysSetting;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.common.util.MD5Util;
import com.welltech.common.util.Tree;
import com.welltech.dao.sysSetting.RoleManageDao;
import com.welltech.dto.WtMenuDto;
import com.welltech.entity.WtMenu;
import com.welltech.entity.WtRole;
import com.welltech.entity.WtRoleMenu;
import com.welltech.entity.WtUserRole;
import com.welltech.security.entity.WtUser;

/**
 * Created by Zhujia at 2017年7月28日 上午9:47:27
 */
@Service
public class RoleManageService {

	@Autowired
	RoleManageDao roleManageDao;
	
	/**
	 * 查询所有角色
	 * @return
	 */
	public List<WtRole> findAllRole() {
		return roleManageDao.findAllRole();
	}

	/**
	 * 根绝id查找某个角色
	 * @param id
	 * @return
	 */
	public WtRole findRoleById(String id) {
		return roleManageDao.findRoleById(id);
	}

	/**
	 * 删除角色及其关联的菜单关系和用户关系
	 * @param id
	 */
	public void deleteRole(String id) {
		roleManageDao.deleteRoleById(id);
		roleManageDao.deleteUserRoleByRoleId(id);
		roleManageDao.deleteRoleMenuByRoleId(id);
	}

	/**
	 * @param roleId
	 * @return
	 */
	public List<WtMenuDto> getRoleMenu(String roleId) {
		if(StringUtils.isBlank(roleId)){
			//id为空,新增角色,获取所有
			return roleManageDao.getMenu();
		}
		//不为空,编辑角色,获取该角色的菜单勾选情况
		return roleManageDao.getRoleMenu(roleId);
	}

	/**
	 * @param roleId
	 * @return
	 */
	public WtRole getRoleById(String roleId) {
		return roleManageDao.findRoleById(roleId);
	}

	/**
	 * @param roleName
	 * @return
	 */
	public WtRole findRoleByRolename(String roleName) {
		return roleManageDao.findRoleByRolename(roleName);
	}

	/**
	 * @param role
	 * @param menuId
	 */
	public void updateRole(WtRole role, String[] menuId) {
		roleManageDao.deleteRoleMenuByRoleId(role.getId()+"");
		for(int i=0;i<menuId.length;i++){
			WtRoleMenu roleMenu = new WtRoleMenu();
			roleMenu.setMenuId(Integer.parseInt(menuId[i]));
			roleMenu.setRoleId(role.getId());
			roleManageDao.addRoleMenu(roleMenu);
		}
		//更新用户
		roleManageDao.updateRole(role);
	}

	/**
	 * @param wtRole
	 * @param menuId
	 */
	public void addRole(WtRole wtRole, String[] menuId) {
		int maxId = roleManageDao.getMaxRoleId()+1;
		wtRole.setId(maxId);
		wtRole.setRoleStatus("1");
		roleManageDao.addRole(wtRole);
		for(int i=0;i<menuId.length;i++){
			WtRoleMenu roleMenu = new WtRoleMenu();
			roleMenu.setMenuId(Integer.parseInt(menuId[i]));
			roleMenu.setRoleId(maxId);
			roleManageDao.addRoleMenu(roleMenu);
		}
	}

	/**
	 * @param wtRole
	 * @return
	 */
	public WtRole findRoleByRolenameExpRoleId(WtRole wtRole) {
		return roleManageDao.findRoleByRolenameExpRoleId(wtRole);
	}

}
