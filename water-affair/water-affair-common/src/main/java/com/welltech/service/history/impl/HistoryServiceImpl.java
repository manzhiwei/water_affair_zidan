package com.welltech.service.history.impl;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.dao.WtDataRawDao;
import com.welltech.dao.statistics.StationStatisticDao;
import com.welltech.dto.WtDataRawDto;
import com.welltech.entity.WtDataRaw;
import com.welltech.entity.WtStation;
import com.welltech.framework.aop.pagination.bean.MyPage;
import com.welltech.framework.aop.pagination.bean.Page;
import com.welltech.framework.aop.pagination.bean.Pagination;
import com.welltech.service.history.HistoryService;

@Service
public class HistoryServiceImpl implements HistoryService{
	
	@Autowired
	private WtDataRawDao wtDataRawDao;
	
	@Autowired
	private StationStatisticDao stationStatisticDao;

	@Override
	public List<WtDataRawDto> listHistoryWtDataRawDto(MyPage myPage, Integer[] pointIds, Date startTime,
			Date endTime) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Page page = new Pagination();
		page.setCurrentPage(myPage.getCurrentPage());
		map.put("page", page);
		map.put("pointIds", pointIds);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<WtDataRawDto> result = wtDataRawDao.findPageWtDataRaws(map);
		if(result != null){
			Field[] fields = WtDataRaw.class.getDeclaredFields();
			for (WtDataRawDto dto : result) {
				Map<String, Object> paramValues = new LinkedHashMap<>();
				
				for(Field f: fields){
					f.setAccessible(true);
					if(f.getName().matches("^[p][1-9]?\\d$")){
						//匹配p1~p32
						try {
							paramValues.put(f.getName(), f.get(dto));
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

	/* (non-Javadoc)
	 * @see com.welltech.service.history.HistoryService#listHistoryWtData(com.welltech.framework.aop.pagination.bean.MyPage, java.lang.Integer, java.util.Date, java.util.Date)
	 */
	@Override
	public Object listHistoryWtData(MyPage myPage, Integer pointId, Date startTime, Date endTime, String dataType) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Page page = new Pagination();
		page.setCurrentPage(myPage.getCurrentPage());
		map.put("page", page);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("dataType", dataType);

		List<WtDataRawDto> result = null;
		if("5".equals(dataType)){
			map.put("pointIds", new Integer[]{pointId});
			result = wtDataRawDao.findPageWtDataRaws(map);
		} else{
			map.put("pointId", pointId);
			result = wtDataRawDao.findPageWtDataRaw(map);
		}

		if(result != null){
			Field[] fields = WtDataRaw.class.getDeclaredFields();
			for (WtDataRawDto dto : result) {
				Map<String, Object> paramValues = new LinkedHashMap<>();
				
				for(Field f: fields){
					f.setAccessible(true);
					if(f.getName().matches("^[p][1-9]?\\d$")){
						//匹配p1~p32
						try {
							paramValues.put(f.getName(), f.get(dto));
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
	public WtStation getStation(Integer stationId) {
		return stationStatisticDao.findWtStationById(stationId);
	}

	
	
}
