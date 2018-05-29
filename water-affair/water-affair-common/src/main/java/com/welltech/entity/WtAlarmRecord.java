package com.welltech.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 报警记录
 * @author WangXin
 *
 */
public class WtAlarmRecord {
	
	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 报警时间
	 */
	private Date alarmTime;
	
	/**
	 * 单位id
	 */
	private Integer companyId;
	
	/**
	 * 单位名称
	 */
	private String companyName;
	
	/**
	 * 测点id
	 */
	private Integer stationId;
	
	/**
	 * 测点
	 */
	private String point;
	
	/**
	 * 项目名称
	 */
	private String projectCode;
	
	/**
	 * 报警上限
	 */
	private BigDecimal alarmMax;
	
	/**
	 * 报警下限
	 */
	private BigDecimal alarmMin;
	
	/**
	 * 类型编号
	 */
	private String typeCode;
	
	/**
	 * 类型名称
	 */
	private String typeValue;
	
	/**
	 * 报警描述
	 */
	private String description;
	
	/**
	 * 报警参数
	 */
	private String alarmParam;
	
	/**
	 * 报警参数名称
	 */
	private String alarmParamName;

	/**
	 * 删除标记
	 */
	private String deleteFlag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
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

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public BigDecimal getAlarmMax() {
		return alarmMax;
	}

	public void setAlarmMax(BigDecimal alarmMax) {
		this.alarmMax = alarmMax;
	}

	public BigDecimal getAlarmMin() {
		return alarmMin;
	}

	public void setAlarmMin(BigDecimal alarmMin) {
		this.alarmMin = alarmMin;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAlarmParam() {
		return alarmParam;
	}

	public void setAlarmParam(String alarmParam) {
		this.alarmParam = alarmParam;
	}

	public String getAlarmParamName() {
		return alarmParamName;
	}

	public void setAlarmParamName(String alarmParamName) {
		this.alarmParamName = alarmParamName;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Override
	public String toString() {
		return "WtAlarmRecord{" +
				"id=" + id +
				", alarmTime=" + alarmTime +
				", companyId=" + companyId +
				", companyName='" + companyName + '\'' +
				", stationId=" + stationId +
				", point='" + point + '\'' +
				", projectCode='" + projectCode + '\'' +
				", alarmMax=" + alarmMax +
				", alarmMin=" + alarmMin +
				", typeCode='" + typeCode + '\'' +
				", typeValue='" + typeValue + '\'' +
				", description='" + description + '\'' +
				", alarmParam='" + alarmParam + '\'' +
				", alarmParamName='" + alarmParamName + '\'' +
				", deleteFlag='" + deleteFlag + '\'' +
				'}';
	}
}
