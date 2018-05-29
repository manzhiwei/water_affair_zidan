package com.welltech.service.realtime.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.dao.WtDataRawDao;
import com.welltech.dao.WtStationDao;
import com.welltech.dao.indexData.IndexDataDao;
import com.welltech.dto.WaterLevelResult;
import com.welltech.dto.WtDataRawDto;
import com.welltech.entity.WtCompany;
import com.welltech.entity.WtDataRaw;
import com.welltech.entity.WtParam;
import com.welltech.entity.WtStation;
import com.welltech.entity.WtStationMonitor;
import com.welltech.entity.WtWaterLevel;
import com.welltech.entity.WtWaterType;
import com.welltech.service.index.UiElementService;
import com.welltech.service.realtime.MapService;
import com.welltech.service.statistics.WaterLevelService;

@Service
public class MapServiceImpl implements MapService {

	@Autowired
	private IndexDataDao indexDataDao;
	
	@Autowired
	private WtDataRawDao wtDataRawDao;
	
	@Autowired
	private UiElementService uiElementService;
	
	@Autowired
	private WtStationDao wtStationDao;
	
	@Autowired
	private WaterLevelService waterLevelService;
	
	@Override
	public Map<String, Object> getDataOfMapButtons() {
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> levelNums = new ArrayList<>();
		List<Map<String, Object>> stationDetails = new ArrayList<>();	
		
		List<WtStation> stations = wtStationDao.findAllStations();
		List<WtParam> params = waterLevelService.listWtParam();
		List<WtWaterLevel> waterLevels = waterLevelService.listWaterLevel();
		List<WtWaterType> types = indexDataDao.findWtWaterTypesByTypeCode(null);
		
		Map<String, Integer> levelNum = new LinkedHashMap<>();//分类-数目
		Map<String, String> typeCode = new HashMap<>();//分类-数目
		Map<String, String> levelCode = new HashMap<>();//分类-数目
		for(WtWaterType type: types){
			levelNum.put(type.getLevel(), 0);
			typeCode.put(type.getLevel(), type.getTypeCode());
			levelCode.put(type.getLevel(), type.getLevelCode());
		}
		int sum = 0;
		if(stations != null){
			for(WtStation station : stations){
				Map<String, Object> stationDetail = new HashMap<>();
				
				WtDataRaw data = null;
				if(StringUtils.isNotBlank(station.getGatewaySerial())){
					data = wtDataRawDao.findLatestWtDataRaw(station.getGatewaySerial());
				}
				List<WtStationMonitor> monitors = waterLevelService.listMonitorByStationId(station.getId());
				WaterLevelResult levelResult = waterLevelService.calculateWaterLevel(station, data, monitors, params, waterLevels);
			
				int num = levelNum.get(levelResult.getResultName()) + 1;
				levelNum.put(levelResult.getResultName(), num);
				
				stationDetail.put("stationId", station.getId());
				stationDetail.put("longitude", station.getLongitude());
				stationDetail.put("latitude", station.getLatitude());
				stationDetail.put("point", station.getPoint());
				stationDetail.put("companyId", station.getCompanyId());
				stationDetail.put("result", levelResult.getResultName());
				stationDetail.put("typeCode", levelResult.getLevelType());
				stationDetail.put("levelCode", levelResult.getResultCode());
				stationDetails.add(stationDetail);
				
				sum ++;
			}
		}
		result.put("stations", stationDetails);
		
		// 对数据结果进行运算和补全
		for(String level : levelNum.keySet()){
			Map<String, Object> map = new HashMap<>();
			map.put("level", level);
			map.put("levelCode", levelCode.get(level));
			map.put("typeCode", typeCode.get(level));
			map.put("num", levelNum.get(level));
			DecimalFormat df = new DecimalFormat("#.##");
			map.put("precent", sum <= 0 ? 0 : df.format(new BigDecimal(levelNum.get(level) * 100).divide(new BigDecimal(sum), 1, RoundingMode.HALF_UP)));
			levelNums.add(map);
		}
		result.put("levelNums", levelNums);//button上的数据
		
		return result;
	}

	@Override
	public Map<String, Object> getStationData(Integer stationId) {
		Map<String, Object> result = new HashMap<>();
		WtDataRawDto wtDataRawDto = wtDataRawDao.findWtDataRawDtoByStationId(stationId);
		Map<String, WtParam> param = uiElementService.getParamsByStationId(stationId);
		result.put("data", wtDataRawDto);
		result.put("params", param);
		return result;
	}

	@Override
	public List<WtCompany> getCompany() {
		return wtStationDao.findAllCompanys();
	}
	
}
