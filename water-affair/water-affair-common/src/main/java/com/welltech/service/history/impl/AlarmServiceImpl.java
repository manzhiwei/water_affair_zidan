package com.welltech.service.history.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.dao.history.WtAlarmRecordDao;
import com.welltech.entity.WtAlarmRecord;
import com.welltech.framework.aop.pagination.bean.MyPage;
import com.welltech.framework.aop.pagination.bean.Page;
import com.welltech.framework.aop.pagination.bean.Pagination;
import com.welltech.service.history.AlarmService;

@Service
public class AlarmServiceImpl implements AlarmService {

	@Autowired
	private WtAlarmRecordDao wtAlarmRecordDao;

	@Override
	public List<WtAlarmRecord> listWtAlarmRecords(MyPage myPage, Integer[] pointIds, Date startTime, Date endTime,String typeCode) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Page page = new Pagination();
		page.setCurrentPage(myPage.getCurrentPage());
		map.put("page", page);
		map.put("pointIds", pointIds);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("typeCode", typeCode);
		
		List<WtAlarmRecord> result = wtAlarmRecordDao.findPageWtAlarmRecord(map);
		myPage.setTotalPages(page.getTotalPages());
		return result;
	}

	@Override
	public void deleteById(Integer id) {
		wtAlarmRecordDao.updateDeleteFlag(id);
	}
	
}
