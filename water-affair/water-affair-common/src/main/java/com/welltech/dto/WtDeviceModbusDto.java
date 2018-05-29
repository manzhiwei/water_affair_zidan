package com.welltech.dto;

import java.util.Date;

/**
 * 传感器参数
 */
public class WtDeviceModbusDto {

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
     * 数据格式编号
     */
    private String formatCode;

    /**
     * 数据格式名称
     */
    private String formatName;

    /**
     * 通道号
     */
    private String passWay;

    private String modbusAdr;

    private String modbusCmd;

    private String modbusStra;

    private String modbusNum;

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

    public String getFormatCode() {
        return formatCode;
    }

    public void setFormatCode(String formatCode) {
        this.formatCode = formatCode;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public String getPassWay() {
        return passWay;
    }

    public void setPassWay(String passWay) {
        this.passWay = passWay;
    }

    public String getModbusAdr() {
        return modbusAdr;
    }

    public void setModbusAdr(String modbusAdr) {
        this.modbusAdr = modbusAdr;
    }

    public String getModbusCmd() {
        return modbusCmd;
    }

    public void setModbusCmd(String modbusCmd) {
        this.modbusCmd = modbusCmd;
    }

    public String getModbusStra() {
        return modbusStra;
    }

    public void setModbusStra(String modbusStra) {
        this.modbusStra = modbusStra;
    }

    public String getModbusNum() {
        return modbusNum;
    }

    public void setModbusNum(String modbusNum) {
        this.modbusNum = modbusNum;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
