/**
 * 
 */
package com.welltech.dto;

import java.util.Date;

/**
 * 故障dto
 * Created by Zhujia at 2017年8月23日 下午5:51:09
 */
public class WtStationBreakdownDto {

	/**
	 * 故障主键
	 */
	private Integer id;
	
	/**
	 * 站点id
	 */
	private Integer stationId;
	
	/**
	 * 区域id
	 */
	private Integer companyId;
	
	/**
	 * 区域名称
	 */
	private String companyName;
	
	/**
	 * 测点名称
	 */
	private String point;
	
	/**
	 * 登记人
	 */
	private String createUser;
	
	/**
	 * 登记时间
	 */
	private Date createTime;
	
	/**
	 * 故障类型
	 */
	private String breakdownType;
	
	/**
	 * 登记人手机号
	 */
	private String createUserPhone;
	
	/**
	 * 故障描述
	 */
	private String desc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBreakdownType() {
		return breakdownType;
	}

	public void setBreakdownType(String breakdownType) {
		this.breakdownType = breakdownType;
	}

	public String getCreateUserPhone() {
		return createUserPhone;
	}

	public void setCreateUserPhone(String createUserPhone) {
		this.createUserPhone = createUserPhone;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
