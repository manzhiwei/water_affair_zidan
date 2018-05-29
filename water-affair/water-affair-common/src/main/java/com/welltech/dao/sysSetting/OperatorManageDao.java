/**
 * 
 */
package com.welltech.dao.sysSetting;

import java.util.List;

import com.welltech.dto.WtUserDto;
import com.welltech.entity.WtRole;
import com.welltech.entity.WtUser;
import com.welltech.entity.WtUserRole;

/**
 * Created by Zhujia at 2017年7月27日 上午10:13:44
 */
public interface OperatorManageDao {

	/**
	 * 查找所有用户及其角色
	 * @return
	 */
	List<WtUserDto> findAllUserRole();

	/**
	 * 查找所有角色
	 * @return
	 */
	List<WtRole> findAllRole();

	/**
	 * 根据id查找用户信息
	 * @param id
	 * @return
	 */
	WtUser findUserById(String id);
	
	/**
	 * 根据用户名查找用户信息
	 * @param username
	 * @return
	 */
	WtUser findUserByUsername(String username);

	/**
	 * 根据id删除用户
	 * @param id
	 */
	void deleteUserById(String id);

	/**
	 * 删除用户角色关联关系
	 * @param id
	 */
	void deleteUserRoleByUserId(String id);

	/**
	 * 新增操作员
	 * @param wtUser
	 */
	void addUser(WtUser wtUser);

	/**
	 * 添加用户权限关联
	 * @param userrole
	 */
	void addUserRole(WtUserRole userrole);

	/**
	 * 获取最大的用户id值
	 * @return
	 */
	int getMaxUserId();

	/**
	 * 根据用户ID查找用户及角色
	 * @param id
	 * @return
	 */
	WtUserDto findUserDtoById(String id);

	/**
	 * 更新用户操作
	 * @param user
	 */
	void updateUser(WtUser user);

}
