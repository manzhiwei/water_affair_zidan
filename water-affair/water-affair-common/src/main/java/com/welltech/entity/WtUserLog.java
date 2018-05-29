/**
 * 
 */
package com.welltech.entity;

import java.util.Date;

/**
 * Created by Zhujia at 2017年8月28日 下午10:17:34
 */
public class WtUserLog {

	/**
	 * 主键
	 */
	private int id;
	
	/**
	 * 用户id
	 */
	private int userId;
	
	/**
	 * 操作时间
	 */
	private Date operateTime;
	
	/**
	 * 操作名称 :
	 * 1:实时刷新,
	 * 2:读取参数,
	 * 3:设置参数,
	 * 4:新建用户,
	 * 5:修改用户,
	 * 6:注销用户,
	 * 7:新建角色,
	 * 8:修改角色
	 */
	private String operateName;
	
	/**
	 * 操作描述
	 */
	private String operateDesc;

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

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getOperateDesc() {
		return operateDesc;
	}

	public void setOperateDesc(String operateDesc) {
		this.operateDesc = operateDesc;
	}
	
}
