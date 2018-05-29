package com.welltech.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welltech.service.index.UiElementService;
import com.welltech.service.statistics.StationStatisticService;

@Controller
public class StationController {

	@Autowired
	private StationStatisticService stationStatisticService;
	
	@Autowired
	private UiElementService uiElementService;
	
	@RequestMapping("/station")
	public String station(Model model, Integer stationId){
		try {
			if(stationId == null){
				stationId = uiElementService.findAllPointDtos().get(0).getId();
			}
		} catch(NullPointerException e){
			return "404";
		}
		model.addAttribute("stationId", stationId);
		model.addAllAttributes(stationStatisticService.getMobileStationData(stationId));
		return "mobile/station";
	}
	
	@RequestMapping("/search")
	@ResponseBody
	public Map<String,Object> search(String point){
		Map<String, Object> map = new HashMap<>();
		map.put("result", stationStatisticService.findWtStationsBySearchingPoint(point));
		return map;
	}

	@RequestMapping("/stationList")
	public String stationList(Model model, Integer stationId){
		model.addAttribute("stationId", stationId);
		model.addAttribute("datas", stationStatisticService.getAllStationByCompany());
		return "mobile/stationList";
	}

	@RequestMapping("/stationParamData")
	@ResponseBody
	public Map<String,Object> stationParamData(@RequestParam String param, @RequestParam String mcuId, @RequestParam String type, @RequestParam Long startTime){
		Map<String,Object> map = new HashMap<>();
		if(param.matches("^[p][1-9]?\\d$")){
			//匹配p1~p32
			map.put("datas", stationStatisticService.getWtDataRowsOfMobileChart(param, mcuId, type, new Date(startTime)));
		}
		return map;
	}

}
