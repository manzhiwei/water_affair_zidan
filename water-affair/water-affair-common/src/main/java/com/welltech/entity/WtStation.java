/**
 * 
 */
package com.welltech.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by Zhujia at 2017年7月29日 下午4:05:18
 */
public class WtStation {

	private Integer id;
	
	/**
	 * 区
	 */
	private String district;
	
	/**
	 * 镇
	 */
	private String town;
	
	/**
	 * 河道
	 */
	private String riverway;
	
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
	 * 交叉路
	 */
	private String crossway;
	
	/**
	 * 特征建筑物
	 */
	private String building;
	
	/**
	 * 其他标记物
	 */
	private String marker;
	
	/**
	 * 区域名称
	 */
	private Integer companyId;
	
	/**
	 * 传输标识
	 */
	private String transferSign;
	
	private String dataPort;
	
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
	 * 安装时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date installTime;
	
	private String installAddress;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastHeartbeatTime;
	
	private int connectStatus;

	private String stationJudgeType;

	private String stationStandard;
	
	private int transferCycle;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getRiverway() {
		return riverway;
	}

	public void setRiverway(String riverway) {
		this.riverway = riverway;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
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

	public String getCrossway() {
		return crossway;
	}

	public void setCrossway(String crossway) {
		this.crossway = crossway;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getTransferSign() {
		return transferSign;
	}

	public void setTransferSign(String transferSign) {
		this.transferSign = transferSign;
	}

	public String getDataPort() {
		return dataPort;
	}

	public void setDataPort(String dataPort) {
		this.dataPort = dataPort;
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

	public Date getInstallTime() {
		return installTime;
	}

	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
	}

	public String getInstallAddress() {
		return installAddress;
	}

	public void setInstallAddress(String installAddress) {
		this.installAddress = installAddress;
	}

	public Date getLastHeartbeatTime() {
		return lastHeartbeatTime;
	}

	public void setLastHeartbeatTime(Date lastHeartbeatTime) {
		this.lastHeartbeatTime = lastHeartbeatTime;
	}

	public int getConnectStatus() {
		return connectStatus;
	}

	public void setConnectStatus(int connectStatus) {
		this.connectStatus = connectStatus;
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

	public int getTransferCycle() {
		return transferCycle;
	}

	public void setTransferCycle(int transferCycle) {
		this.transferCycle = transferCycle;
	}
	
}
