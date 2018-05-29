/**
 * 
 */
package com.welltech.dto;

import java.sql.Date;

/**
 * Created by Zhujia at 2017年7月29日 下午5:06:02
 */
public class WtStationDto {

	private Integer id;
	
	private String companyName;
	
	/**
	 * 检测点
	 */
	private String point;
	
	/**
	 * 地理位置经度
	 */
	private String longitude;
	
	/**
	 * 地理位置纬度
	 */
	private String latitude;
	
	/**
	 * 安装位置经度
	 */
	private String installLongitude;
	
	/**
	 * 安装位置纬度
	 */
	private String installLatitude;
	
	/**
	 * 安装时间
	 */
	private Date installTime;
	
	/**
	 * 手机卡号
	 */
	private String cardNo;
	
	/**
	 * 项目编号
	 */
	private String projectCode;
	
	/**
	 * 网关设备序列编号
	 */
	private String gatewaySerial;

	/**
	 * 测点评价分类
	 */
	private String stationJudgeType;

	/**
	 * 测点属性
	 */
	private String stationStandard;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	
	public String getInstallLongitude() {
		return installLongitude;
	}

	public void setInstallLongitude(String installLongitude) {
		this.installLongitude = installLongitude;
	}

	public String getInstallLatitude() {
		return installLatitude;
	}

	public void setInstallLatitude(String installLatitude) {
		this.installLatitude = installLatitude;
	}

	public Date getInstallTime() {
		return installTime;
	}

	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getGatewaySerial() {
		return gatewaySerial;
	}

	public void setGatewaySerial(String gatewaySerial) {
		this.gatewaySerial = gatewaySerial;
	}

	public String getStationJudgeType() {
		return stationJudgeType;
	}

	public void setStationJudgeType(String stationJudgeType) {
		this.stationJudgeType = stationJudgeType;
	}

	public String getStationStandard() {
		return stationStandard;
	}

	public void setStationStandard(String stationStandard) {
		this.stationStandard = stationStandard;
	}

}
