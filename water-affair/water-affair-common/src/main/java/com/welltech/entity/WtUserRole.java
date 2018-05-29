/**
 * 
 */
package com.welltech.entity;

/**
 * 用户角色关联类
 * Created by Zhujia at 2017年8月7日 下午3:12:10
 */
public class WtUserRole {

	/**
	 * 用户ID
	 */
	private Integer userId;
	
	/**
	 * 角色ID
	 */
	private Integer roleId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
