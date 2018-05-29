/**
 * 
 */
package com.welltech.dto;

/**
 * Created by Zhujia at 2017年8月10日 下午2:16:06
 */
public class WtStationMonitorDto {

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 参数
	 */
	private String param;
	
	/**
	 * 参数别名
	 */
	private String aliasParamName;
	
	/**
	 * 别名单位
	 */
	private String aliasUnit;
	
	/**
	 * 数据类型:
	 * 0整型,1:浮点
	 */
	private String roundType;

	private String display;
	
	/**
	 * 别名显示
	 */
	private String aliasDisplay;
	
	/**
	 * 是否黑臭评价
	 */
	private String heichouDisplay;
	
	/**
	 * 黑臭评价标准
	 */
	private String heichouLevel;
	
	/**
	 * 是否地表评价
	 */
	private String dibiaoDisplay;
	
	/**
	 * 地表评价标准
	 */
	private String dibiaoLevel;
	
	/**
	 * 标准名称
	 */
	private String paramAdjust;
	
	/**
	 * 参数名
	 */
	private String paramName;
	
	/**
	 * 参数单位
	 */
	private String unit;
	
	/**
	 * 检测点id
	 */
	private Integer stationId;
	
	/**
	 * 量程上限
	 */
	private double rangeMax;
	
	/**
	 * 量程下限
	 */
	private double rangeMin;
	
	/**
	 * 告警上限
	 */
	private double alertMax;
	
	/**
	 * 告警下限
	 */
	private double alertMin;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getAliasDisplay() {
		return aliasDisplay;
	}

	public void setAliasDisplay(String aliasDisplay) {
		this.aliasDisplay = aliasDisplay;
	}

	public String getHeichouDisplay() {
		return heichouDisplay;
	}

	public void setHeichouDisplay(String heichouDisplay) {
		this.heichouDisplay = heichouDisplay;
	}

	public String getHeichouLevel() {
		return heichouLevel;
	}

	public void setHeichouLevel(String heichouLevel) {
		this.heichouLevel = heichouLevel;
	}

	public String getDibiaoDisplay() {
		return dibiaoDisplay;
	}

	public void setDibiaoDisplay(String dibiaoDisplay) {
		this.dibiaoDisplay = dibiaoDisplay;
	}

	public String getDibiaoLevel() {
		return dibiaoLevel;
	}

	public void setDibiaoLevel(String dibiaoLevel) {
		this.dibiaoLevel = dibiaoLevel;
	}

	public String getParamAdjust() {
		return paramAdjust;
	}

	public void setParamAdjust(String paramAdjust) {
		this.paramAdjust = paramAdjust;
	}

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public double getRangeMax() {
		return rangeMax;
	}

	public void setRangeMax(double rangeMax) {
		this.rangeMax = rangeMax;
	}

	public double getRangeMin() {
		return rangeMin;
	}

	public void setRangeMin(double rangeMin) {
		this.rangeMin = rangeMin;
	}

	public double getAlertMax() {
		return alertMax;
	}

	public void setAlertMax(double alertMax) {
		this.alertMax = alertMax;
	}

	public double getAlertMin() {
		return alertMin;
	}

	public void setAlertMin(double alertMin) {
		this.alertMin = alertMin;
	}

	public String getAliasParamName() {
		return aliasParamName;
	}

	public void setAliasParamName(String aliasParamName) {
		this.aliasParamName = aliasParamName;
	}

	public String getAliasUnit() {
		return aliasUnit;
	}

	public void setAliasUnit(String aliasUnit) {
		this.aliasUnit = aliasUnit;
	}

	public String getRoundType() {
		return roundType;
	}

	public void setRoundType(String roundType) {
		this.roundType = roundType;
	}
	
}
