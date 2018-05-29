package com.welltech.dto;

import java.util.Date;

/**
 * Created by wangxin on 2017/12/18.
 */
public class WtDeviceDto {

    /**
     * 控制器地址
     */
    private String controllerAddress;

    /**
     * 控制器id
     */
    private String mcuId;

    /**
     * 连接时间
     */
    private Date connectTime;

    /**
     * 站点名称
     */
    private String stationName;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 手机卡号
     */
    private String cardNo;

    /**
     * 项目编号
     */
    private String projectCode;
    /**
     * 位置经度
     */
    private String longitude;
    /**

     * 位置纬度
     */
    private String latitude;

    public String getControllerAddress() {
        return controllerAddress;
    }

    public void setControllerAddress(String controllerAddress) {
        this.controllerAddress = controllerAddress;
    }

    public String getMcuId() {
        return mcuId;
    }

    public void setMcuId(String mcuId) {
        this.mcuId = mcuId;
    }

    public Date getConnnectTime() {
        return connectTime;
    }

    public void setConnnectTime(Date connectTime) {
        this.connectTime = connectTime;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

}
