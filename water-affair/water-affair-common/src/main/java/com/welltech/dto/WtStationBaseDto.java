/**
 *
 */
package com.welltech.dto;

import java.util.Date;

/**
 * Created by Zhujia at 2017年8月20日 下午10:39:11
 */
public class WtStationBaseDto {

	/**
	 * station主键
	 */
	private Integer stationId;

	/**
	 * 测点名称
	 */
	private String point;

	/**
	 * 经度
	 */
	private String longitude;

	/**
	 * 纬度
	 */
	private String latitude;

	/**
	 * 安装经度
	 */
	private String installLongitude;

	/**
	 * 安装纬度
	 */
	private String installLatitude;

	/**
	 * 区域id
	 */
	private String companyId;

	/**
	 * 手机号
	 */
	private String cardNo;

	/**
	 * 项目编号
	 */
	private String projectCode;

	/**
	 * 设备序列号
	 */
	private String gatewaySerial;

	/**
	 * 安装时间
	 */
	private Date installTime;

	/**
	 * base主键
	 */
	private Integer stationBaseId;

	/**
	 * 供电延时
	 */
	private Integer powerDelay;

	/**
	 * 采集时间
	 */
	private Integer collectionTime;

	/**
	 * 串口问询间隔
	 */
	private Integer serialQueryInterval;

	/**
	 * 采串口定时间隔
	 */
	private Integer serialInterval;

	/**
	 * 蓄电池电压低报警
	 */
	private Integer lowBatteryAlarm;

	/**
	 * 蓄电池电压低报警幅度
	 */
	private Integer lowBatteryAlarmRange;

	/**
	 * 蓄电池电压过低报警
	 */
	private Integer lowBatteryAlarm2;

	/**
	 * 蓄电池电压过低报警幅度
	 */
	private Integer lowBatteryAlarmRange2;

	/**
	 * 电压偏移量
	 */
	private Integer voltageOffset;

	/**
	 * 历史记录存储定时间隔
	 */
	private Integer recordStorageInterval;

	/**
	 * 历史记录上报定时间隔
	 */
	private Integer recordUploadInterval;

	/**
	 * 实时数上报间隔定时间隔
	 */
	private Integer realtimeUploadInterval;

	/**
	 * 电池满量
	 */
	private Integer fullBattery;

	private String stationJudgeType;

	private String stationStandard;
	
	private Integer transferCycle;

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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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

	public Integer getStationBaseId() {
		return stationBaseId;
	}

	public void setStationBaseId(Integer stationBaseId) {
		this.stationBaseId = stationBaseId;
	}

	public Integer getPowerDelay() {
		return powerDelay;
	}

	public void setPowerDelay(Integer powerDelay) {
		this.powerDelay = powerDelay;
	}

	public Integer getCollectionTime() {
		return collectionTime;
	}

	public void setCollectionTime(Integer collectionTime) {
		this.collectionTime = collectionTime;
	}

	public Integer getSerialQueryInterval() {
		return serialQueryInterval;
	}

	public void setSerialQueryInterval(Integer serialQueryInterval) {
		this.serialQueryInterval = serialQueryInterval;
	}

	public Integer getSerialInterval() {
		return serialInterval;
	}

	public void setSerialInterval(Integer serialInterval) {
		this.serialInterval = serialInterval;
	}

	public Integer getLowBatteryAlarm() {
		return lowBatteryAlarm;
	}

	public void setLowBatteryAlarm(Integer lowBatteryAlarm) {
		this.lowBatteryAlarm = lowBatteryAlarm;
	}

	public Integer getLowBatteryAlarmRange() {
		return lowBatteryAlarmRange;
	}

	public void setLowBatteryAlarmRange(Integer lowBatteryAlarmRange) {
		this.lowBatteryAlarmRange = lowBatteryAlarmRange;
	}

	public Integer getLowBatteryAlarm2() {
		return lowBatteryAlarm2;
	}

	public void setLowBatteryAlarm2(Integer lowBatteryAlarm2) {
		this.lowBatteryAlarm2 = lowBatteryAlarm2;
	}

	public Integer getLowBatteryAlarmRange2() {
		return lowBatteryAlarmRange2;
	}

	public void setLowBatteryAlarmRange2(Integer lowBatteryAlarmRange2) {
		this.lowBatteryAlarmRange2 = lowBatteryAlarmRange2;
	}

	public Integer getVoltageOffset() {
		return voltageOffset;
	}

	public void setVoltageOffset(Integer voltageOffset) {
		this.voltageOffset = voltageOffset;
	}

	public Integer getRecordStorageInterval() {
		return recordStorageInterval;
	}

	public void setRecordStorageInterval(Integer recordStorageInterval) {
		this.recordStorageInterval = recordStorageInterval;
	}

	public Integer getRecordUploadInterval() {
		return recordUploadInterval;
	}

	public void setRecordUploadInterval(Integer recordUploadInterval) {
		this.recordUploadInterval = recordUploadInterval;
	}

	public Integer getRealtimeUploadInterval() {
		return realtimeUploadInterval;
	}

	public void setRealtimeUploadInterval(Integer realtimeUploadInterval) {
		this.realtimeUploadInterval = realtimeUploadInterval;
	}

	public Integer getFullBattery() {
		return fullBattery;
	}

	public void setFullBattery(Integer fullBattery) {
		this.fullBattery = fullBattery;
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

	public Integer getTransferCycle() {
		return transferCycle;
	}

	public void setTransferCycle(Integer transferCycle) {
		this.transferCycle = transferCycle;
	}
	
}
