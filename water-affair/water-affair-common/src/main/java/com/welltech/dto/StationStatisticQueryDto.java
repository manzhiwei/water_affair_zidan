package com.welltech.dto;

public class StationStatisticQueryDto {

    private String srcParam;

    private String param;

    private String heichouCase;

    private String dibiaoCase;

    private String whereCase;

    public String getSrcParam() {
        return srcParam;
    }

    public void setSrcParam(String srcParam) {
        this.srcParam = srcParam;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getHeichouCase() {
        return heichouCase;
    }

    public void setHeichouCase(String heichouCase) {
        this.heichouCase = heichouCase;
    }

    public String getDibiaoCase() {
        return dibiaoCase;
    }

    public void setDibiaoCase(String dibiaoCase) {
        this.dibiaoCase = dibiaoCase;
    }

    public String getWhereCase() {
        return whereCase;
    }

    public void setWhereCase(String whereCase) {
        this.whereCase = whereCase;
    }
}
