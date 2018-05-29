/**
 * 
 */
package com.welltech.entity;

/**
 * 测点设备参数信息
 * Created by Zhujia at 2017年8月18日 下午4:01:06
 */
public class WtStationBase {

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 测点id
	 */
	private Integer stationId;
	
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

}
