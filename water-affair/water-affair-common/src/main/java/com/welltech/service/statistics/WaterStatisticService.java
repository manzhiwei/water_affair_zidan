package com.welltech.service.statistics;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.dao.indexData.IndexDataDao;
import com.welltech.dao.statistics.WaterStatisticDao;

@Service
public class WaterStatisticService {

	@Autowired
	private WaterStatisticDao waterStatisticDao;
	
	@Autowired
	private IndexDataDao indexDataDao;
	
	public Map<String, Object> getWaterStatistic(List<Integer> companyIds, List<Integer> stationIds, String param,
			Date startTime, Date endTime, String typeCode){
		Map<String, Object> result = new LinkedHashMap<>();
		if(param==null||!param.matches("^[p][1-9]?\\d$")){
			param = "p1";
		}
		List<String> levels = indexDataDao.findLevelNameAsc(typeCode);
		for( String level: levels ){
			result.put(level, 0);
		}

		List<Map<String, Object>> queryResult = waterStatisticDao.findWaterStatistic(companyIds, stationIds, param, startTime, endTime, typeCode);
		if( queryResult != null){
			for(Map<String, Object> map: queryResult){
				int cnt = ((Long)map.get("cnt")).intValue();
				result.put((String)map.get("level"), cnt);
			}
		}
		
		return result;
	}
	
}
