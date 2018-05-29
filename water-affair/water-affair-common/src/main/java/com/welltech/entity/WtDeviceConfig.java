package com.welltech.entity;

public class WtDeviceConfig {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 类型
     */
    private String type;

    /**
     * 编号
     */
    private String code;

    /**
     * 参数名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 不可读
     */
    private String notRead;

    /**
     * 不可写
     */
    private String notWrite;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNotRead() {
        return notRead;
    }

    public void setNotRead(String notRead) {
        this.notRead = notRead;
    }

    public String getNotWrite() {
        return notWrite;
    }

    public void setNotWrite(String notWrite) {
        this.notWrite = notWrite;
    }
}
