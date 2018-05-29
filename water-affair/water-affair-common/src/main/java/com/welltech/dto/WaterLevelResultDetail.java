package com.welltech.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 水质类别结果详情
 * @author wangxin
 *
 */
public class WaterLevelResultDetail {
	
	/**
	 * 时间
	 */
	private Date time;

	/**
	 * 结果：false超标、true正常
	 */
	private boolean finalResult = true;
	
	/**
	 * 参数
	 */
	private String param;
	
	/**
	 * 参数名
	 */
	private String paramName;
	
	/**
	 * 数值
	 */
	private BigDecimal value;
	
	/**
	 * 单位
	 */
	private String unit;
	
	/**
	 * 评价标准参数
	 */
	private String standardParam;
	
	/**
	 * 是否显示
	 */
	private boolean display;
	
	/**
	 * 是否评价
	 */
	private boolean involved;
	
	/**
	 * 标准等级
	 */
	private String standardLevel;
	
	/**
	 * 评价等级编号
	 */
	private String resultCode;
	
	/**
	 * 评价等级
	 */
	private String resultName;
	
	/**
	 * 上限：可能为空
	 */
	private BigDecimal upperLimit;
	
	/**
	 * 下限：可能为空
	 */
	private BigDecimal lowerLimit;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public boolean isFinalResult() {
		return finalResult;
	}

	public void setFinalResult(boolean finalResult) {
		this.finalResult = finalResult;
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

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getStandardParam() {
		return standardParam;
	}

	public void setStandardParam(String standardParam) {
		this.standardParam = standardParam;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public boolean isInvolved() {
		return involved;
	}

	public void setInvolved(boolean involved) {
		this.involved = involved;
	}

	public String getStandardLevel() {
		return standardLevel;
	}

	public void setStandardLevel(String standardLevel) {
		this.standardLevel = standardLevel;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultName() {
		return resultName;
	}

	public void setResultName(String resultName) {
		this.resultName = resultName;
	}

	public BigDecimal getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(BigDecimal upperLimit) {
		this.upperLimit = upperLimit;
	}

	public BigDecimal getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(BigDecimal lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	@Override
	public String toString() {
		return "WaterLevelResultDetail [time=" + time + ", finalResult=" + finalResult + ", param=" + param
				+ ", paramName=" + paramName + ", value=" + value + ", unit=" + unit + ", standardParam="
				+ standardParam + ", display=" + display + ", involved=" + involved + ", standardLevel=" + standardLevel
				+ ", resultCode=" + resultCode + ", resultName=" + resultName + ", upperLimit=" + upperLimit
				+ ", lowerLimit=" + lowerLimit + "]";
	}

}
