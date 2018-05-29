package com.welltech.controller.statistics;

import java.util.Map;

import java.util.List;
import com.welltech.dto.PointDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welltech.service.index.UiElementService;
import com.welltech.service.statistics.StationStatisticService;

@Controller
public class StationStatisticController {

	@Autowired
	private UiElementService uiElementService;
	
	@Autowired
	private StationStatisticService stationStatisticService;
	
	@RequestMapping("/stationStatistic")
	public String stationStatistic(Model model){
		List<PointDto> points = uiElementService.findAllPointDtos();
		model.addAttribute("points", points);
		PointDto defaultPoint = points.isEmpty()? new PointDto() : points.get(0);
		model.addAttribute("pointId", defaultPoint.getId());
		model.addAttribute("pointName", defaultPoint.getPoint());
		return "statistics/stationStatistic";
	}
	
	@RequestMapping("/stationStatisticData")
	@ResponseBody
	public Map<String, Object> stationStatisticData(Integer stationId) {
		return stationStatisticService.getStationStatisticData(stationId);
	}
	
}
