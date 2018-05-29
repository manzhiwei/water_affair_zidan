/**
 * 
 */
package com.welltech.common.util;

/**
 * Created by Zhujia at 2017年7月24日 下午11:01:40
 */
public enum ErrorCodeEnum {
	
	ERROR_01("ERROR_01", "原密码输入错误"),
	ERROR_02("ERROR_02", "用户不存在"),
	ERROR_03("ERROR_03", "角色不存在"),
	ERROR_04("ERROR_04", "局域不存在"),
	ERROR_05("ERROR_05", "坐标格式错误"),
	ERROR_06("ERROR_06", "测点不存在"),
	ERROR_07("ERROR_07", "该用户名已存在"),
	ERROR_08("ERROR_08", "该角色名已存在"),
	ERROR_09("ERROR_09", "该序列号已存在"),
	ERROR_99("ERROR_99", "系统异常,请联系管理员");
	
	private String code = null;
    private String value = null;

    private ErrorCodeEnum(String _code, String _value) {
        this.code = _code;
        this.value = _value;
    }

    public static ErrorCodeEnum getEnumByKey(String key) {
        for (ErrorCodeEnum e : ErrorCodeEnum.values()) {
            if (e.getCode().equals(key)) {
                return e;
            }
        }
        return null;
    }

    /** 获取code */
    public String getCode() {
        return code;
    }

	/** 获取值 */
	public String getValue() {
		return value;
	}

}
