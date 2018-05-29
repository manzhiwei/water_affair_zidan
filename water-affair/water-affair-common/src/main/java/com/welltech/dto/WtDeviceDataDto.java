package com.welltech.dto;

import java.util.Date;

/**
 * 站点数据
 * Created by wangxin on 2017/12/19.
 */
public class WtDeviceDataDto {
    /**
     * 数据类型
     */
    private String type;

    /**
     * 数据编号
     */
    private String code;

    /**
     * 数据名称
     */
    private String name;

    /**
     * 数值
     */
    private String value;

    /**
     * 更新时间
     */
    private Date updateTime;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
