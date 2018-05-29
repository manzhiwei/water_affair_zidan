package com.welltech.dto;

import com.welltech.entity.WtWaterLevel;

import java.util.List;

public class WaterLevelList {

    private String param;

    private List<WtWaterLevelDto> water;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public List<WtWaterLevelDto> getWater() {
        return water;
    }

    public void setWater(List<WtWaterLevelDto> water) {
        this.water = water;
    }
}
