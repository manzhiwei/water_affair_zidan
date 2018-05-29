/**
 * 
 */
package com.welltech.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by Zhujia at 2017年8月22日 下午4:27:35
 */
public class WtStationBreakdown {
	
	/**
	 * 主键id
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
	 * 登记人
	 */
	private String createUser;
	
	/**
	 * 登记时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	/**
	 * 故障类型:
	 * 1:设备修改 
	 * 2:电极维护
	 */
	private String breakdownType;
	
	/**
	 * 登记人电话
	 */
	private String createUserPhone;
	
	/**
	 * 水样采集时间
	 */
	private String collectionTime;
	
	/**
	 * 标准值
	 */
	private String standard;
	
	/**
	 * 测量值
	 */
	private String measured;
	
	/**
	 * 误差
	 */
	private String deviation;
	
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

	public String getCollectionTime() {
		return collectionTime;
	}

	public void setCollectionTime(String collectionTime) {
		this.collectionTime = collectionTime;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getMeasured() {
		return measured;
	}

	public void setMeasured(String measured) {
		this.measured = measured;
	}

	public String getDeviation() {
		return deviation;
	}

	public void setDeviation(String deviation) {
		this.deviation = deviation;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
