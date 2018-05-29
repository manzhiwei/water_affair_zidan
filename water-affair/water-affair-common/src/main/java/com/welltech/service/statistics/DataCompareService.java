package com.welltech.service.statistics;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.dao.WtDataRawDao;

@Service
public class DataCompareService {

	@Autowired
	private WtDataRawDao dataRawDao;

	public Map<String, Object> getDataCompareData(String param, Date[] dates,
			String gatewaySerial) {
		Map<String, Object> result = new HashMap<>();
		int count=dates.length/2;
		for(int i=0;i<count;i++) {
			List<Map<String, Object>> data1 = dataRawDao.findChartWtData(new String[]{param}, dates[i*2], dates[i*2+1], gatewaySerial);
			result.put("data"+(i+1), data1);
		}
//		List<Map<String, Object>> data2 = dataRawDao.findChartWtData(new String[]{param}, dates[2], dates[3], gatewaySerial);
//		result.put("data2", data2);
//		List<Map<String, Object>> data3 = dataRawDao.findChartWtData(new String[]{param}, dates[4], dates[5], gatewaySerial);
//		result.put("data3", data3);
//		List<Map<String, Object>> data4 = dataRawDao.findChartWtData(new String[]{param}, dates[6], dates[7], gatewaySerial);
//		result.put("data4", data4);
//		List<Map<String, Object>> data5 = dataRawDao.findChartWtData(new String[]{param}, dates[8], dates[9], gatewaySerial);
//		result.put("data5", data5);
//		List<Map<String, Object>> data6 = dataRawDao.findChartWtData(new String[]{param}, dates[10], dates[11], gatewaySerial);
//		result.put("data6", data6);
		return result;
	}
	
}
