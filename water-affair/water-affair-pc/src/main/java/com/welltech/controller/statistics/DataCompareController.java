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
import com.welltech.service.statistics.DataCompareService;

@Controller
public class DataCompareController {

	@Autowired
	private UiElementService uiElementService;
	
	@Autowired
	private DataCompareService dataCompareService;
	
	@Autowired
	private PortManageDao portManageDao;
	
	@RequestMapping("/dataCompare")
	public String dataCompare(Model model) {
		List<PointDto> points = uiElementService.findAllPointDtos();
		model.addAttribute("points", points);
		PointDto defaultPoint = points.isEmpty()? new PointDto() : points.get(0);
		model.addAttribute("pointId", defaultPoint.getId());
		model.addAttribute("pointName", defaultPoint.getPoint());
		return "statistics/dataCompare";
	}
	
	@RequestMapping(value = {"/dataCompareData"})
    @ResponseBody
    public Map<String, Object> dataCompareData(String stationId, String param,
    		@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date... startTime1
    		){
    	WtStation station = portManageDao.findStationById(stationId);
    	if(StringUtils.isNotBlank(station.getGatewaySerial()) 
    			&& StringUtils.isNotBlank(param)){
    		Date[] ds=new Date[startTime1.length];
    		for(int i=0;i<startTime1.length;i++) {
    			ds[i]=startTime1[i];
    		}
    		return dataCompareService.getDataCompareData(param, ds, station.getGatewaySerial());
    	}
    	return new HashMap<>();
    }
}
