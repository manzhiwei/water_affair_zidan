package com.welltech.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 水质结果
 * @author wangxin
 *
 */
public class WaterLevelResult {

	/**
	 * 最后结果：true合格 false 超标
	 */
	private boolean finalResult = true;
	
	/**
	 * 标准类型
	 * <li>null：默认合格</li>
	 * <li>1：黑臭</li>
	 * <li>2：地表</li>
	 */
	private String levelType;
	
	/**
	 * 最后等级
	 */
	private String resultCode;
	
	/**
	 * 最后等级名称
	 */
	private String resultName;
	
	/**
	 * 详情
	 */
	private List<WaterLevelResultDetail> details = new ArrayList<>();

	public boolean isFinalResult() {
		return finalResult;
	}

	public void setFinalResult(boolean finalResult) {
		this.finalResult = finalResult;
	}

	public String getLevelType() {
		return levelType;
	}

	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}

	public List<WaterLevelResultDetail> getDetails() {
		return details;
	}

	public void setDetails(List<WaterLevelResultDetail> details) {
		this.details = details;
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

	@Override
	public String toString() {
		return "WaterLevelResult{" +
				"finalResult=" + finalResult +
				", levelType='" + levelType + '\'' +
				", resultCode='" + resultCode + '\'' +
				", resultName='" + resultName + '\'' +
				", details=" + details +
				'}';
	}
}
