package com.welltech.controller.realtime;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welltech.common.util.OperateCodeEnum;
import com.welltech.common.util.SpringWebUtils;
import com.welltech.framework.aop.pagination.bean.MyPage;
import com.welltech.security.entity.WtUser;
import com.welltech.service.history.OperateHistoryServiceImpl;
import com.welltech.service.realtime.MapService;
import com.welltech.service.realtime.RealtimeService;

@Controller
public class MapController {
	
	@Autowired
	private RealtimeService realtimeService;
	
	@Autowired
	private MapService mapService;

	@Autowired
	private OperateHistoryServiceImpl operateHistoryService;
	
	@RequestMapping(value = {"map"})
    public String map(){
        return "realtime/map";
    }
    
    /**
     * 获取表数据
     */
    @RequestMapping(value = {"mapData/tableDatas"})
    @ResponseBody
    public Map<String, Object> tableDatas(MyPage myPage, Integer[] pointIds){
    	Map<String, Object> result = new HashMap<>();
    	if(myPage == null){
    		myPage = new MyPage();
    	}
    	result.put("datas", realtimeService.listDataRaws(myPage, pointIds, 3));
    	result.put("myPage", myPage);
    	return result;
    }
    
    /**
     * 获取分类等级数据
     */
    @RequestMapping(value = {"mapData/levelDatas"})
    @ResponseBody
    public Map<String, Object> levelDatas(String typeCode){
    	return mapService.getDataOfMapButtons();
    }
    
    @RequestMapping(value = {"mapData/stationData"})
    @ResponseBody
    public Map<String, Object> stationData(Integer stationId, String record){
    	if(StringUtils.isNotEmpty(record)){
    		WtUser user = (WtUser) SpringWebUtils.getSession().getAttribute("user");
    		operateHistoryService.addOperate(OperateCodeEnum.OPERATE_01, user.getId(), record);
    	}
    	return mapService.getStationData(stationId);
    }
}
