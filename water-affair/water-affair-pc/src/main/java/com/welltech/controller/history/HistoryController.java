package com.welltech.controller.history;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.welltech.framework.aop.pagination.bean.MyPage;
import com.welltech.service.history.HistoryService;
import com.welltech.service.index.UiElementService;
import com.welltech.service.realtime.RealtimeService;

@Controller
public class HistoryController {

	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private RealtimeService realtimeService;
	
	@Autowired
	private UiElementService uiElementService; 
	
    @RequestMapping(value = {"historyData"})
    public String realtimeData(Model model, MyPage myPage, Integer[] pointIds,
    		@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime,
    		@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endTime, String pointName, String pointId){
    	if(myPage == null){
    		myPage = new MyPage();
    	}
    	model.addAttribute("points", uiElementService.findAllPointDtos());
    	model.addAttribute("pointIds", pointIds);
        model.addAttribute("pointId", pointId);
        model.addAttribute("pointName", pointName);
		if(pointIds == null || pointIds.length == 0) {
			if(StringUtils.isNotBlank(pointId)){
				String[] stationIds = pointId.split(",");
				pointIds = new Integer[stationIds.length];
				for(int i = 0; i < stationIds.length; i ++) {
					pointIds[i] = Integer.parseInt(stationIds[i]);
				}
			}
		}
    	if(pointIds == null || pointIds.length == 0){
    		pointIds = realtimeService.getDefaultStations();
    	}
    	boolean alias = false;
    	if(pointIds.length == 1){
    		//单站点查询，有可能设置过别名
    		List<Integer> unpass = realtimeService.checkStations(pointIds);
    		if(!unpass.isEmpty()){
    			//设置过别名
    			alias = true;
    		}
    	}
    	model.addAttribute("alias", alias);
    	if(alias){
    		model.addAttribute("params", uiElementService.getParamsByStationId(pointIds[0]));
    	} else{
    		model.addAttribute("params", uiElementService.getParams());
    	}
    	
    	model.addAttribute("datas", historyService.listHistoryWtDataRawDto(myPage, pointIds, startTime, endTime));
    	model.addAttribute("myPage", myPage);
    	model.addAttribute("startTime", startTime);
    	model.addAttribute("endTime", endTime);
        return "history/historyData";
    }

}
