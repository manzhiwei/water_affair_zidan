package com.welltech.dto;

public class StationStatisticsResultDto {

    /**
     * 统计数
     */
    private Integer ct;

    /**
     * 参数／参照参数
     */
    private String param;

    /**
     * 参数名／参数别名
     */
    private String paramName;

    public Integer getCt() {
        return ct;
    }

    public void setCt(Integer ct) {
        this.ct = ct;
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
}
