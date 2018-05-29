package com.welltech.dto;

import java.math.BigDecimal;
import java.util.Map;

import com.welltech.entity.WtDataRaw;

public class WtDataRawDto extends WtDataRaw{
	
	/**
	 * 测点id
	 */
	private Integer pointId;
	
	/**
	 * 测点
	 */
	private String point;
	
	/**
	 * 项目编号
	 */
	private String projectCode;
	
	/**
	 * 卡号
	 */
	private String cardNo;
	
	/**
	 * 单位名称
	 */
	private String companyName;
	
	/**
	 * 网关设备序列编号
	 */
	private String gatewaySerial;
	
	/**
	 * 电池电量
	 */
	private BigDecimal battery;
	
	/**
	 * 电池总量
	 */
	private BigDecimal fullBattery;
	
	/**
	 * 网络信号
	 */
	private BigDecimal network;
	
	/**
	 * 参数-数值键值
	 */
	private Map<String, Object> paramValues;

	public Integer getPointId() {
		return pointId;
	}

	public void setPointId(Integer pointId) {
		this.pointId = pointId;
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

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getGatewaySerial() {
		return gatewaySerial;
	}

	public void setGatewaySerial(String gatewaySerial) {
		this.gatewaySerial = gatewaySerial;
	}

	public BigDecimal getBattery() {
		return battery;
	}

	public void setBattery(BigDecimal battery) {
		this.battery = battery;
	}

	public BigDecimal getFullBattery() {
		return fullBattery;
	}

	public void setFullBattery(BigDecimal fullBattery) {
		this.fullBattery = fullBattery;
	}

	public BigDecimal getNetwork() {
		return network;
	}

	public void setNetwork(BigDecimal network) {
		this.network = network;
	}

	public Map<String, Object> getParamValues() {
		return paramValues;
	}

	public void setParamValues(Map<String, Object> paramValues) {
		this.paramValues = paramValues;
	}
}
