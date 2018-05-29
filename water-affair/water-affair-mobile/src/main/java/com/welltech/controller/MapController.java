package com.welltech.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welltech.service.index.IndexDataService;
import com.welltech.service.realtime.MapService;

@Controller
public class MapController {

	@Autowired
	private MapService mapService;
	
	@Autowired
	private IndexDataService indexDataService;
	
	@RequestMapping("/map")
	public String map(){
		return "mobile/map";
	}
	
    /**
     * 获取分类等级数据
     */
    @RequestMapping(value = {"/mapData/levelDatas"})
    @ResponseBody
    public Map<String, Object> levelDatas(String typeCode){
    	return mapService.getDataOfMapButtons();
    }
    
    @RequestMapping(value = {"/mapData/stationData"})
    @ResponseBody
    public Map<String, Object> stationData(Integer stationId){
    	return mapService.getStationData(stationId);
    }
	
    @RequestMapping(value = {"/mapData/realtimeNetwork"})
	@ResponseBody
    public Map<String, Object> realtimeNetwork(){
		Map<String, Object> result = new HashMap<>();
		result.put("data", indexDataService.getRealtimeMonitoring());
		return result;
	}
    
    @RequestMapping(value = {"/mapData/company"})
    @ResponseBody
    public Map<String, Object> company(){
		Map<String, Object> result = new HashMap<>();
		result.put("data", mapService.getCompany());
		return result;
	}
    
}
