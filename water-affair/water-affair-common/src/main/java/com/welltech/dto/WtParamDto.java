/**
 * 
 */
package com.welltech.dto;

import java.util.List;

import com.welltech.entity.WtWaterLevel;

/**
 * 按指标组分类的指标范围
 * Created by Zhujia at 2017年8月6日 下午10:39:07
 */
public class WtParamDto {
	
	/**
	 * id
	 */
	private Integer id;
	
	/**
	 * 参数
	 */
	private String param;
	
	/**
	 * 参数名
	 */
	private String paramName;
	
	/**
	 * 水指标
	 */
	private String waterRange;

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

	public String getWaterRange() {
		return waterRange;
	}

	public void setWaterRange(String waterRange) {
		this.waterRange = waterRange;
	}

}
