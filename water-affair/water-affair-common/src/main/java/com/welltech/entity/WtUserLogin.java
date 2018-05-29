/**
 * 
 */
package com.welltech.entity;

import java.sql.Date;

/**
 * Created by Zhujia at 2017年8月14日 下午3:44:49
 */
public class WtUserLogin {
	
	/**
	 * 主键
	 */
	private int id;
	
	/**
	 * 用户id
	 */
	private int userId;
	
	/**
	 * 登录状态:
	 * 1:成功,0:失败
	 */
	private String loginStatus;

	/**
	 * 登录时间
	 */
	private Date loginTime; 
	
	/**
	 * 登录ip
	 */
	private String loginIp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
}
