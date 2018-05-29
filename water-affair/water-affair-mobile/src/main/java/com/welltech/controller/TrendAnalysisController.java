/**
 * 
 */
package com.welltech.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welltech.dao.sysSetting.PortManageDao;
import com.welltech.entity.WtStation;
import com.welltech.framework.aop.pagination.bean.MyPage;
import com.welltech.service.history.HistoryService;
import com.welltech.service.index.UiElementService;
import com.welltech.service.realtime.RealtimeService;
import com.welltech.service.statistics.StationStatisticService;
import com.welltech.service.statistics.TrendAnalysisService;

/**
 * 趋势分析
 * Created by Zhujia at 2017年9月17日 下午5:31:10
 */
@Controller
public class TrendAnalysisController {
	
	@Autowired
	private UiElementService uiElementService;
	
	@Autowired
	private RealtimeService realtimeService;
	
	@Autowired
	private TrendAnalysisService trendAnalysisService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private PortManageDao portManageDao;
	
	@Autowired
	private StationStatisticService stationStatisticService;

	@RequestMapping(value = {"trendAnalysis"})
    public String trendAnalysis(Model model, MyPage myPage, String stationId, String dataType){
		WtStation station = portManageDao.findStationById(stationId);
		model.addAttribute("station", station);
		model.addAttribute("points", uiElementService.findAllPointDtos());
		if(StringUtils.isBlank(dataType)){
			dataType="1";
		}
		model.addAttribute("datas", trendAnalysisService.findAnalysisDatas(station.getGatewaySerial(),dataType));
    	model.addAttribute("stationId", stationId);
    	model.addAttribute("pointId", stationId);
        return "trendAnalysis";
    }
	
	@RequestMapping(value = {"multipleParamStatisticData"})
    @ResponseBody
    public Map<String, Object> realtimeChartData(String stationId, String[] params,
    		@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime,
    		@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endTime, String dataType){
    	Map<String, Object> result = new HashMap<>();
    	WtStation station = portManageDao.findStationById(stationId);
    	if(StringUtils.isNotBlank(station.getGatewaySerial()) 
    			&& params!= null && params.length > 0){
    		result.put("data", realtimeService.findChartWtDataByDatatype(params, startTime, endTime, dataType, station.getGatewaySerial()));
    	}
    	return result;
    }
	
	@RequestMapping(value = {"analysis"})
	public String analysis(Model model, Integer stationId){
		try {
			if(stationId == null){
				stationId = uiElementService.findAllPointDtos().get(0).getId();
			}
		} catch(NullPointerException e){
			return "404";
		}
		model.addAttribute("params", uiElementService.getParamsByStationId(stationId));
		model.addAttribute("station", historyService.getStation(stationId));
		model.addAttribute("stationId", stationId);
		return "mobile/analysis";
	}
	
	@RequestMapping(value = {"analysisData"})
	@ResponseBody
	public Map<String, Object> analysisData(Integer stationId, String dataType, String dateStr){
		Map<String, Object> result = new HashMap<>();
		result.put("data", stationStatisticService.findAnalysis(stationId, dataType, dateStr));
		return result;
	}
	
}
