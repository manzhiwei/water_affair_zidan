 package com.welltech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.welltech.framework.aop.pagination.bean.Page;
import com.welltech.framework.aop.pagination.bean.Pagination;
import com.welltech.service.index.IndexDataService;

/**
 * 首页数据
 * @author WangXin
 *
 */
@RestController
@RequestMapping("/indexRest")
public class IndexRestController {

	@Autowired
	private IndexDataService indexDataService;
	
	@RequestMapping(value = {"/realtimeNetwork"})
	public Map<String, Object> realtimeNetwork(){
		Map<String, Object> result = new HashMap<>();
		result.put("data", indexDataService.findRealtimeNetworkDto());
		return result;
	}
	
	@RequestMapping(value = {"/singleParam"})
	public Map<String, Object> singleParam(Integer draw, Integer start, Integer length, String param, String type){
		Page page = new Pagination();
		page.setDefalutPageRows(length);
		page.setCurrentPage(start / length + 1);
		List<Map<String, Object>> data = indexDataService.listSingleParam(page, param, type);
		
		Map<String, Object> result = new HashMap<>();
		result.put("draw", draw);
		result.put("recordsTotal", page.getTotalRecord());
		result.put("recordsFiltered", page.getTotalRecord());
		result.put("data", data);
		return result;
	}
	
	@RequestMapping(value = {"/dayWater"})
	public Map<String, Object> dayWater(String typeCode){
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> data=indexDataService.countWaterLevel(typeCode);
		result.put("data", data.get("data"));
		result.put("data1", data.get("data1"));
		result.put("typeCode", data.get("typeCode"));
		return result;
	}
	
	@RequestMapping(value = {"/realtimeMonitoring"})
	public Map<String, Object> realtimeMonitoring(){
		return indexDataService.getRealtimeMonitoring();
	}
	
	@RequestMapping(value = {"/overproof"})
	public Map<String, Object> overproof(Integer draw, Integer start, Integer length){
		Page page = new Pagination();
		page.setDefalutPageRows(8);
		page.setCurrentPage(1);
		List<Map<String, Object>> data = indexDataService.listOverproof(page);
		
		Map<String, Object> result = new HashMap<>();
//		result.put("draw", draw);
//		result.put("recordsTotal", page.getTotalRecord());
//		result.put("recordsFiltered", page.getTotalRecord());
		result.put("data", data);
		return result;
	}
	
	@RequestMapping(value = {"/fault"})
	public Map<String, Object> fault(Integer draw, Integer start, Integer length){
		Page page = new Pagination();
		page.setDefalutPageRows(length);
		page.setCurrentPage(start / length + 1);
		List<Map<String, Object>> data = indexDataService.fault(page);
		
		Map<String, Object> result = new HashMap<>();
		result.put("draw", draw);
		result.put("recordsTotal", page.getTotalRecord());
		result.put("recordsFiltered", page.getTotalRecord());
		result.put("data", data);
		return result;
	}
	@RequestMapping(value = {"/abnormal"})
	public Map<String, Object> abnormal(Integer draw, Integer start, Integer length){
		Page page = new Pagination();
		page.setDefalutPageRows(length);
		page.setCurrentPage(start / length + 1);
		List<Map<String, Object>> data = indexDataService.listAbnormal(page);
		
		Map<String, Object> result = new HashMap<>();
		result.put("draw", draw);
		result.put("recordsTotal", page.getTotalRecord());
		result.put("recordsFiltered", page.getTotalRecord());
		result.put("data", data);
		return result;
	}
}
