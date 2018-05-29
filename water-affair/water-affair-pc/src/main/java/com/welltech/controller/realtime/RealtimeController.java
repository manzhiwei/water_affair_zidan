package com.welltech.controller.realtime;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welltech.framework.aop.pagination.bean.MyPage;
import com.welltech.service.index.UiElementService;
import com.welltech.service.realtime.RealtimeService;

@Controller
public class RealtimeController {

	@Autowired
	private RealtimeService realtimeService;
	
	@Autowired
	private UiElementService uiElementService; 

	@Value("${device.server.url}")
	private String url;
	
    @RequestMapping(value = {"realtimeData"})
    public String realtimeData(Model model, MyPage myPage, Integer[] pointIds, String pointName, String pointId){
    	if(myPage == null){
    		myPage = new MyPage();
    	}
    	model.addAttribute("points", uiElementService.findAllPointDtos());//所有站点
    	model.addAttribute("pointIds", pointIds);
    	model.addAttribute("pointId", pointId);
    	model.addAttribute("pointName", pointName);
    	if(pointIds == null || pointIds.length == 0){
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
    	
    	model.addAttribute("datas", realtimeService.listDataRaws(myPage, pointIds));
    	model.addAttribute("myPage", myPage);
        return "realtime/realtimeData";
    }
    
    @RequestMapping(value = {"checkStaions"})
    @ResponseBody
    public Map<String, Object> realtimeChartData(Integer[] pointIds){
    	Map<String, Object> result = new HashMap<>();
    	List<Integer> unpass = realtimeService.checkStations(pointIds);
    	result.put("unpass", unpass);
    	return result;
    }
    
    @RequestMapping(value = {"realtimeChart"})
    public String realtimeChart(Model model, String gatewaySerial, Integer[] pointIds){
    	if(StringUtils.isBlank(gatewaySerial) || pointIds == null || pointIds.length != 1){
    		return "404";
    	}
    	List<Integer> unpass = realtimeService.checkStations(pointIds);
		if(!unpass.isEmpty()){
			model.addAttribute("params", uiElementService.getParamsByStationId(pointIds[0]));
    	} else{
    		model.addAttribute("params", uiElementService.getParams());
		}
    	model.addAttribute("gatewaySerial", gatewaySerial);
    	return "realtime/realtimeChart";
    }
    
    /**
     * 变化趋势图表数据
     * @param mcu 检测器类型，和gatewaySerial同义，以后可能会变更名称使其一致
     * @param params 参数，p1~p32
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @RequestMapping(value = {"realtimeChartData"})
    @ResponseBody
    public Map<String, Object> realtimeChartData(String mcu, String[] params,
    		@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime,
    		@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endTime){
    	Map<String, Object> result = new HashMap<>();
    	if(StringUtils.isNotBlank(mcu)
    			&& params!= null && params.length > 0){
    		result.put("data", realtimeService.findChartWtData(params, startTime, endTime, mcu));
    	}
    	return result;
    }

    /**
     * 图片和视频
     * @param type
     * @param params
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = {"contentData"})
    @ResponseBody
    public Map<String, Object> contentData(String type, String sn){
    	Map<String, Object> result = new HashMap<>();
    	if(StringUtils.isNotBlank(sn)){
    		result.put("data", realtimeService.findReceivedFiles(sn, type));
    	}
    	if("1".equals(type)){
    		result.put("serverUrl", this.url);
    	}
    	return result;
    }
    
}
