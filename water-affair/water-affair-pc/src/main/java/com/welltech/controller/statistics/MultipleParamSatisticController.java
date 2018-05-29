package com.welltech.controller.statistics;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.welltech.dto.PointDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welltech.dao.sysSetting.PortManageDao;
import com.welltech.entity.WtStation;
import com.welltech.service.index.UiElementService;
import com.welltech.service.realtime.RealtimeService;

@Controller
public class MultipleParamSatisticController {

	@Autowired
	private UiElementService uiElementService;
	
	@Autowired
	private RealtimeService realtimeService;
	
	@Autowired
	private PortManageDao portManageDao;
	
	@RequestMapping("/multipleParamStatistic")
	public String multipleParamStatistic(Model model) {
		List<PointDto> points = uiElementService.findAllPointDtos();
		model.addAttribute("points", points);
		PointDto defaultPoint = points.isEmpty()? new PointDto() : points.get(0);
		model.addAttribute("pointId", defaultPoint.getId());
		model.addAttribute("pointName", defaultPoint.getPoint());
		return "statistics/multipleParamStatistic";
	}
	
	@RequestMapping(value = {"multipleParamStatisticData"})
    @ResponseBody
    public Map<String, Object> realtimeChartData(String stationId, String[] params,
    		@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime,
    		@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endTime){
    	Map<String, Object> result = new HashMap<>();
    	WtStation station = portManageDao.findStationById(stationId);
    	if(StringUtils.isNotBlank(station.getGatewaySerial()) 
    			&& params!= null && params.length > 0){
    		result.put("data", realtimeService.findChartWtData(params, startTime, endTime, station.getGatewaySerial()));
    	}
    	return result;
    }
}
