/**
 * 
 */
package com.welltech.common.util;

/**
 * 返回类型
 * Created by Zhujia at 2017年7月24日 下午5:47:42
 */
public class ReturnEntity<T> {
	
	private static final long serialVersionUID = 1L;

	String returnCode =	"0000";	//返回代码 默认成功
	
	private String message; // 返回消息
	
	private T returnData;	//返回数据

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
		ErrorCodeEnum erEnum = ErrorCodeEnum.getEnumByKey(returnCode);
		if(null!=erEnum){
			this.message = erEnum.getValue();
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getReturnData() {
		return returnData;
	}

	public void setReturnData(T returnData) {
		this.returnData = returnData;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
