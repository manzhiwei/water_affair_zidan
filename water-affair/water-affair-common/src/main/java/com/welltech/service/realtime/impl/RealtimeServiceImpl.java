package com.welltech.service.realtime.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.welltech.dao.WtDataRawDao;
import com.welltech.dao.WtReceivedFileDao;
import com.welltech.dao.WtStationDao;
import com.welltech.dao.WtStationMonitorDao;
import com.welltech.dto.PointDto;
import com.welltech.dto.WtDataRawDto;
import com.welltech.entity.WtDataRaw;
import com.welltech.entity.WtReceivedFile;
import com.welltech.entity.WtStation;
import com.welltech.entity.WtStationMonitor;
import com.welltech.framework.aop.pagination.bean.MyPage;
import com.welltech.framework.aop.pagination.bean.Page;
import com.welltech.framework.aop.pagination.bean.Pagination;
import com.welltech.service.index.UiElementService;
import com.welltech.service.realtime.RealtimeService;

@Service
public class RealtimeServiceImpl implements RealtimeService {

	@Autowired
	private WtDataRawDao wtDataRawDao;
	
	@Autowired
	private WtStationMonitorDao wtStationMonitorDao;
	
	@Autowired
	private WtReceivedFileDao wtReceivedFileDao;
	
	@Autowired
	private UiElementService uiElementService;
	
	@Autowired
	private WtStationDao wtStationDao;
	
	@Override
	public List<WtDataRawDto> listDataRaws(MyPage myPage, Integer[] pointIds) {
		return this.listDataRaws(myPage, pointIds, 0);
	}

	@Override
	public List<WtDataRawDto> listDataRaws(MyPage myPage, Integer[] pointIds, int rows) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		Page page = new Pagination();
		if(rows > 0){
			page.setDefalutPageRows(rows);
		}
		page.setCurrentPage(myPage.getCurrentPage());
		map.put("page", page);
		map.put("pointIds", pointIds);
		List<WtDataRawDto> result = wtDataRawDao.findPageLatestWtDataRaws(map);
		if(result != null){
			Field[] fields = WtDataRaw.class.getDeclaredFields();
			for (WtDataRawDto dto : result) {
				Map<String, Object> paramValues = new LinkedHashMap<>();
				
				for(Field f: fields){
					f.setAccessible(true);
					if(f.getName().matches("^[p][1-9]?\\d$")){
						//匹配p1~p32
						try {
							paramValues.put(f.getName(), f.get(dto));//p* - dto
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				dto.setParamValues(paramValues);
			}
		}
		
		myPage.setTotalPages(page.getTotalPages());
		return result;
	}

	@Override
	public List<Map<String, Object>> findChartWtData(String[] params, Date startTime, Date endTime, String mcu) {
		if(params != null){
			for (String param : params) {
				if(!param.matches("^[p][1-9]?\\d$")){//防止sql注入
					return null;
				}
			}
		}
		return wtDataRawDao.findChartWtData(params, startTime, endTime, mcu);
	}

	@Override
	public List<Integer> checkStations(Integer[] stationIds) {
		List<Integer> result = new ArrayList<>();
		if(stationIds != null && stationIds.length > 0){
			for(Integer stationId : stationIds){
				WtStation station = wtStationDao.findStationById(stationId);
				if(station != null
						&& "2".equals(station.getStationStandard())){
					// 个性站点
					result.add(stationId);
				}
			}
		}
		return result;
	}

	@Override
	public Integer[] getDefaultStations() {
		List<PointDto> dtos = uiElementService.findAllPointDtos();
		List<Integer> results = new ArrayList<>();
		if(dtos != null && dtos.size() > 0){
			for(PointDto dto : dtos){
				/*List<WtStationMonitor> monitors = wtStationMonitorDao.findSetAliasByStationId(dto.getId());
				if(monitors == null || monitors.isEmpty()){
					results.add(dto.getId());
				}*/
				WtStation station = wtStationDao.findStationById(dto.getId());
				if(StringUtils.isBlank(station.getStationStandard())
						|| !"2".equals(station.getStationStandard())){
					results.add(dto.getId());
				}
			}
		}
		Integer[] result = new Integer[results.size()];
		return results.toArray(result);
	}

	/* (non-Javadoc)
	 * @see com.welltech.service.realtime.RealtimeService#checkStation(java.lang.Integer)
	 */
	@Override
	public List<Integer> checkStation(Integer pointId) {
		List<Integer> result = new ArrayList<>();
		List<WtStationMonitor> monitors = wtStationMonitorDao.findSetAliasByStationId(pointId);
		if(monitors != null && !monitors.isEmpty()){
			result.add(pointId);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.welltech.service.realtime.RealtimeService#findChartWtDataByDatatype(java.lang.String[], java.util.Date, java.util.Date, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> findChartWtDataByDatatype(String[] params, Date startTime, Date endTime,
			String dataType, String gatewaySerial) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WtReceivedFile> findReceivedFiles(String type, String mcu) {
		return wtReceivedFileDao.listByMcu(type, mcu);
	}

}
