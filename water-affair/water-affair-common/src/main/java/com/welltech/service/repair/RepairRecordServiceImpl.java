/**
 * 
 */
package com.welltech.service.repair;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.dao.repair.RepairRecordDao;
import com.welltech.dto.WtStationBreakdownDto;
import com.welltech.entity.WtStationRepair;
import com.welltech.framework.aop.pagination.bean.MyPage;
import com.welltech.framework.aop.pagination.bean.Page;
import com.welltech.framework.aop.pagination.bean.Pagination;

/**
 * Created by Zhujia at 2017年8月27日 下午2:07:24
 */
@Service
public class RepairRecordServiceImpl {
	
	@Autowired
	RepairRecordDao repairRecordDao;
	
	/**
	 * @param myPage
	 * @param beginTime
	 * @param endTime
	 * @param stationIds
	 * @return
	 */
	public Object listRepairPage(MyPage myPage, Date beginTime, Date endTime, String[] stationIds) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Page page = new Pagination();
		page.setDefalutPageRows(0);
		page.setCurrentPage(myPage.getCurrentPage());
		map.put("page", page);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("stationIds", stationIds);
		List<WtStationRepair> result = repairRecordDao.findPageRepairList(map);
		myPage.setTotalPages(page.getTotalPages());
		return result;
	}

	public Object listRepair(Date beginTime, Date endTime, String[] stationIds) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("stationIds", stationIds);
		List<WtStationRepair> result = repairRecordDao.findRepairList(map);
		return result;
	}
}
