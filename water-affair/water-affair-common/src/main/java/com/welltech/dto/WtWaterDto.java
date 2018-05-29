/**
 * 
 */
package com.welltech.dto;

import java.util.List;

/**
 * 水质参数配置dto
 * Created by Zhujia at 2017年8月6日 下午10:38:14
 */
public class WtWaterDto {

	/**
	 * 参数
	 */
	private String param;
	
	/**
	 * 参数名字
	 */
	private String paramName;
	
	/**
	 * 指标dto
	 */
	private List<WtParamDto> paramDto;

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

	public List<WtParamDto> getParamDto() {
		return paramDto;
	}

	public void setParamDto(List<WtParamDto> paramDto) {
		this.paramDto = paramDto;
	}
	
	
	
}
