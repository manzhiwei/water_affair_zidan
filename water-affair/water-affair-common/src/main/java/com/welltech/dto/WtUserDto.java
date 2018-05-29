/**
 * 
 */
package com.welltech.dto;

import java.sql.Date;
import java.util.List;

import com.welltech.entity.WtRole;

/**
 * Created by Zhujia at 2017年7月26日 下午9:42:13
 */
public class WtUserDto{
	
	/**
	 * 用户ID
	 */
	private Integer id;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 姓名
	 */
	private String realName;
	
	/**
	 * 密码
	 */
	private String password;
	
	private String passwordSalt;
	
	private String cellphone;
	
	private Date createTime;
	
	private List<WtRole> roles;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<WtRole> getRoles() {
		return roles;
	}

	public void setRoles(List<WtRole> roles) {
		this.roles = roles;
	}

	
}
