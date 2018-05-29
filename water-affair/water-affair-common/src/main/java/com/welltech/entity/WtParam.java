package com.welltech.entity;

/**
 * 参数实体类
 * @author wangxin
 *
 */
public class WtParam {

	/**
	 * id
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
	 * 单位
	 */
	private String unit;
	
	/**
	 * 是否显示
	 * 1：显示 2：不显示
	 */
	private String display;

	/**
	 * 参数类型
	 * 1：整型
	 * 2：浮点型
	 */
	private String roundType;

	/**
	 * 保留小数
	 */
	private String round;

	/**
	 *
	 */
	private String quotaType;

	/**
	 * 黑臭等级
	 */
	private String heichou;

	/**
	 * 地表等级
	 */
	private String dibiao;
	/**
	 * 是否参与评价
	 */
	private String involved;

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

	public String getRoundType() {
		return roundType;
	}

	public void setRoundType(String roundType) {
		this.roundType = roundType;
	}

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}

	public String getQuotaType() {
		return quotaType;
	}

	public void setQuotaType(String quotaType) {
		this.quotaType = quotaType;
	}

	public String getHeichou() {
		return heichou;
	}

	public void setHeichou(String heichou) {
		this.heichou = heichou;
	}

	public String getDibiao() {
		return dibiao;
	}

	public void setDibiao(String dibiao) {
		this.dibiao = dibiao;
	}

	public String getInvolved() {
		return involved;
	}

	public void setInvolved(String involved) {
		this.involved = involved;
	}
	
}
