/**
 * 
 */
package com.welltech.dao.sysSetting;

import java.util.List;

import com.welltech.dto.WtMenuDto;
import com.welltech.entity.WtRole;
import com.welltech.entity.WtRoleMenu;

/**
 * Created by Zhujia at 2017年7月28日 上午9:55:04
 */
public interface RoleManageDao {

	/**
	 * 获取所有角色
	 * @return
	 */
	List<WtRole> findAllRole();

	/**
	 * 根绝id获取角色信息
	 * @param id
	 * @return
	 */
	WtRole findRoleById(String id);

	/**
	 * 根据id删除角色
	 * @param id
	 */
	void deleteRoleById(String id);

	/**
	 * 根绝角色id删除用户角色关联关系
	 * @param id
	 */
	void deleteUserRoleByRoleId(String id);
	
	/**
	 * 根绝角色id删除角色菜单关联关系
	 * @param id
	 */
	void deleteRoleMenuByRoleId(String id);

	/**
	 * 获取所有菜单
	 * @return
	 */
	List<WtMenuDto> getMenu();

	/**
	 * 获取菜单勾选情况
	 * @param roleId
	 * @return
	 */
	List<WtMenuDto> getRoleMenu(String roleId);

	/**
	 * 获取角色表最大的id
	 * @return
	 */
	int getMaxRoleId();

	/**
	 * 保存新角色
	 * @param wtRole
	 */
	void addRole(WtRole wtRole);

	/**
	 * 保存角色和菜单的关联
	 * @param roleMenu
	 */
	void addRoleMenu(WtRoleMenu roleMenu);

	/**
	 * 更新角色
	 * @param role
	 */
	void updateRole(WtRole role);

	/**
	 * 根据角色名称查找角色
	 * @param roleName
	 * @return
	 */
	WtRole findRoleByRolename(String roleName);

	/**
	 * 查找是否有角色名重复ID不重复的角色
	 * @param wtRole
	 * @return
	 */
	WtRole findRoleByRolenameExpRoleId(WtRole wtRole);

}
