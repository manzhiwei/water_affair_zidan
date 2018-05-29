/**
 * 
 */
package com.welltech.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by Zhujia at 2017年8月14日 下午5:48:59
 */
public class WtUserLoginDto {

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 登录状态:
	 * 1:成功,0:失败
	 */
	private String loginStatus;

	/**
	 * 登录时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
