/**
 * 
 */
package com.welltech.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welltech.framework.aop.pagination.bean.MyPage;
import com.welltech.service.history.HistoryService;
import com.welltech.service.index.UiElementService;
import com.welltech.service.realtime.RealtimeService;

/**
 * 历史查询
 * Created by Zhujia at 2017年9月5日 下午3:37:40
 */
@Controller
public class HistoryDataController {
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private RealtimeService realtimeService;

	@Autowired
	private UiElementService uiElementService;
	
	@RequestMapping(value = {"historyData"})
    public String realtimeData(Model model, MyPage myPage, Integer pointId, String dataType,
    		@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime,
    		@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endTime){
    	if(myPage == null){
    		myPage = new MyPage();
    	}
    	boolean alias = false;
		//单站点查询，有可能设置过别名
		List<Integer> unpass = realtimeService.checkStation(pointId);
		if(!unpass.isEmpty()){
			//设置过别名
			alias = true;
		}
    	if(alias){
    		model.addAttribute("params", uiElementService.getParamsByStationId(pointId));
    	} else{
    		model.addAttribute("params", uiElementService.getParams());
    	}
    	if(StringUtils.isBlank(dataType)){
    		dataType="1";
    	}
    	model.addAttribute("datas", historyService.listHistoryWtData(myPage, pointId, startTime, endTime, dataType));
    	model.addAttribute("myPage", myPage);
    	model.addAttribute("startTime", startTime);
    	model.addAttribute("endTime", endTime);
    	model.addAttribute("pointId", pointId);
        return "historyData";
    }
	
	@RequestMapping(value = {"history"})
	public String history(Model model, Integer stationId){
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
		return "mobile/history";
	}
	
	@RequestMapping(value = {"historyDatas"})
	@ResponseBody
    public Map<String, Object> historyDatas(Integer currentPage, Integer stationId, String dataType,
    		@DateTimeFormat(pattern="yyyy-MM-dd H:mm") Date startTime,
    		@DateTimeFormat(pattern="yyyy-MM-dd H:mm") Date endTime){
		if(currentPage == null){
			currentPage = 1;
		}
		MyPage myPage = new MyPage();
		myPage.setCurrentPage(currentPage);
		Map<String, Object> map = new HashMap<>();
    	map.put("datas", historyService.listHistoryWtData(myPage, stationId, startTime, endTime, dataType));
        return map;
    }
	
	
}
