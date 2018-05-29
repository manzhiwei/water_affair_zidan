/**
 * 
 */
package com.welltech.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by Zhujia at 2017年8月22日 下午4:27:50
 */
public class WtStationRepair {
	
	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 故障id
	 */
	private Integer breakdownId;
	
	/**
	 * 维修人姓名
	 */
	private String repairUser;
	
	/**
	 * 维修人手机号
	 */
	private String repairPhone;
	
	/**
	 * 维修开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date repairStartTime;
	
	/**
	 * 维修结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date repairEndTime;
	
	/**
	 * 维修费用
	 */
	private double repairFee;
	
	/**
	 * 维修记录
	 */
	private String repairContent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBreakdownId() {
		return breakdownId;
	}

	public void setBreakdownId(Integer breakdownId) {
		this.breakdownId = breakdownId;
	}

	public String getRepairUser() {
		return repairUser;
	}

	public void setRepairUser(String repairUser) {
		this.repairUser = repairUser;
	}

	public String getRepairPhone() {
		return repairPhone;
	}

	public void setRepairPhone(String repairPhone) {
		this.repairPhone = repairPhone;
	}

	public Date getRepairStartTime() {
		return repairStartTime;
	}

	public void setRepairStartTime(Date repairStartTime) {
		this.repairStartTime = repairStartTime;
	}

	public Date getRepairEndTime() {
		return repairEndTime;
	}

	public void setRepairEndTime(Date repairEndTime) {
		this.repairEndTime = repairEndTime;
	}

	public double getRepairFee() {
		return repairFee;
	}

	public void setRepairFee(double repairFee) {
		this.repairFee = repairFee;
	}

	public String getRepairContent() {
		return repairContent;
	}

	public void setRepairContent(String repairContent) {
		this.repairContent = repairContent;
	}

}
