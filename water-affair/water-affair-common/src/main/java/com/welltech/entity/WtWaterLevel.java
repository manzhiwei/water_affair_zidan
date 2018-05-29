/**
 * 
 */
package com.welltech.entity;

/**
 * 水质类型等级
 * Created by Zhujia at 2017年8月6日 下午4:11:34
 */
public class WtWaterLevel {

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 参数
	 */
	private String param;
	
	/**
	 * 参数名称
	 */
	private String paramName;
	
	/**
	 * 类型编号
	 * 1：黑臭三分，2：地表水质
	 */
	private String typeCode;
	
	/**
	 * 类型名称
	 */
	private String typeName;
	
	/**
	 * 分类等级
	 */
	private String level;
	
	/**
	 * 水质等级编号
	 */
	private String levelCode;
	
	/**
	 * 是否有上限
	 * 1：是，0：否
	 */
	private String hasUpper = "0";
	
	/**
	 * 上限值
	 */
	private double upperLimit;
	
	/**
	 * 是否包含上限值
	 */
	private String containUpper = "0";
	
	/**
	 * 是否有下限
	 * 1：是，0：否
	 */
	private String hasLower = "0";
	
	/**
	 * 下限值
	 */
	private double  lowerLimit;
	
	/**
	 * 是否包含下限值
	 */
	private String containLower = "0";

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

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getHasUpper() {
		return hasUpper;
	}

	public void setHasUpper(String hasUpper) {
		this.hasUpper = hasUpper;
	}

	public double getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(double upperLimit) {
		this.upperLimit = upperLimit;
	}

	public String getContainUpper() {
		return containUpper;
	}

	public void setContainUpper(String containUpper) {
		this.containUpper = containUpper;
	}

	public String getHasLower() {
		return hasLower;
	}

	public void setHasLower(String hasLower) {
		this.hasLower = hasLower;
	}

	public double getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(double lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public String getContainLower() {
		return containLower;
	}

	public void setContainLower(String containLower) {
		this.containLower = containLower;
	}

	@Override
	public String toString() {
		return "WtWaterLevel [id=" + id + ", param=" + param + ", paramName=" + paramName + ", typeCode=" + typeCode
				+ ", typeName=" + typeName + ", level=" + level + ", hasUpper=" + hasUpper + ", upperLimit="
				+ upperLimit + ", containUpper=" + containUpper + ", hasLower=" + hasLower + ", lowerLimit="
				+ lowerLimit + ", containLower=" + containLower + "]";
	}
	
}
