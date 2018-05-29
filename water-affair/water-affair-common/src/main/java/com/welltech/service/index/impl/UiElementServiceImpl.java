package com.welltech.service.index.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.dao.UiElementDao;
import com.welltech.dao.WtParamDao;
import com.welltech.dao.WtStationDao;
import com.welltech.dao.WtStationMonitorDao;
import com.welltech.dto.PointDto;
import com.welltech.entity.WtParam;
import com.welltech.entity.WtStation;
import com.welltech.entity.WtStationMonitor;
import com.welltech.service.index.UiElementService;

@Service("uiElementService")
public class UiElementServiceImpl implements UiElementService {

	@Autowired
	private UiElementDao uiElementDao;
	
	@Autowired
	private WtParamDao wtParamDao;
	
	@Autowired
	private WtStationMonitorDao wtStationMonitorDao;
	
	@Autowired
	private WtStationDao wtStationDao;
	
	@Override
	public List<PointDto> findAllPointDtos() {
		List<PointDto> result = uiElementDao.findAllPointDtos();
		if(result == null){
			result = new ArrayList<PointDto>();
		}
		return result;
	}

	@Override
	public Map<String, WtParam> getParams() {
		Map<String, WtParam> result = new LinkedHashMap<>();
		List<WtParam> params = wtParamDao.findAllWtParams();
		if(params != null){
			for (WtParam param : params) {
				result.put(param.getParam(), param);
			}
		}
		return result;
	}

	@Override
	public List<WtParam> getDisplayParams() {
		List<WtParam> result = wtParamDao.findDisplayWtParams();
		if(result == null){
			result = new ArrayList<>();
		}
		return result;
	}

	@Override
	public Map<String, WtParam> getParamsByStationId(Integer stationId) {
		Map<String, WtParam> params = this.getParams();
		WtStation station = wtStationDao.findStationById(stationId);
		if(station != null
				&& "2".equals(station.getStationStandard())){
			// 个性站点
			List<WtStationMonitor> monitors = wtStationMonitorDao.findSetAliasByStationId(stationId);
			params = this.getPersonalAliasName(monitors, params);
		}
		return params;
	}

	@Override
	public Map<String, WtParam> getDisplayParamsByStationId(WtStation wtStation) {
		Map<String, WtParam> params = new LinkedHashMap<>();
		if("1".equals(wtStation.getStationStandard())){	//标准测点
			List<WtParam> paramsList = wtParamDao.findDisplayWtParams();
			if(paramsList != null){
				for (WtParam param : paramsList) {
					params.put(param.getParam(), param);
				}
			}
		}else{	//个性化测点
			List<WtStationMonitor> monitors = wtStationMonitorDao.findDisplaySetAliasByStationId(wtStation.getId());
			List<WtParam> paramsList = wtParamDao.findStatisticsDisplayWtParams(wtStation.getId()+"");
			if(paramsList != null){
				for (WtParam param : paramsList) {
					params.put(param.getParam(), param);
				}
			}
			params = this.getAliasName(monitors,params);
		}
		return params;


	}

	private Map<String, WtParam> getAliasName(List<WtStationMonitor> monitors, Map<String, WtParam> params){
		if(monitors != null){
			for(WtStationMonitor monitor: monitors) {
				/**if(StringUtils.isNotBlank(monitor.getAliasUnit())){
				 params.get(monitor.getParam()).setUnit(monitor.getAliasUnit());
				 }
				 if(StringUtils.isNotBlank(monitor.getAliasParamName())){
				 params.get(monitor.getParam()).setParamName(monitor.getAliasParamName());
				 params.get(monitor.getParam()).setUnit(monitor.getAliasUnit());
				 }*/

				params.get(monitor.getParamAdjust()).setParamName(monitor.getAliasParamName());
				params.get(monitor.getParamAdjust()).setUnit(monitor.getAliasUnit());
				//此行代码待测试 如有bug请删除
				params.get(monitor.getParamAdjust()).setParam(monitor.getParam());
				params.get(monitor.getParamAdjust()).setDisplay(monitor.getDisplay());
			}
		}
		return params;
	}

	private Map<String, WtParam> getPersonalAliasName(List<WtStationMonitor> monitors, Map<String, WtParam> params){
		//个性站点全部以设置的为准，不再取通用部分
		Map<String, WtParam> result = new LinkedHashMap<>();
		Set<String> keys = params.keySet();
		for(String key: keys){
			WtParam param = new WtParam();
			param.setParam(key);
			param.setParamName(key);;
			param.setDisplay("0");
			result.put(key, param);
		}
		
		if(monitors != null){
			for(WtStationMonitor monitor: monitors) {
				WtParam param = result.get(monitor.getParam());
				//重新对参数个性赋值
				param.setParamName(monitor.getAliasParamName());
				param.setUnit(monitor.getAliasUnit());
				param.setDisplay(monitor.getDisplay());
				param.setRoundType(monitor.getRoundType());
				param.setDibiao(monitor.getDibiaoLevel());
				param.setHeichou(monitor.getHeichouLevel());
			}
		}
		return result;
	}
}
