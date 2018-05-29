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
import com.welltech.dao.repair.ReportRecordDao;
import com.welltech.dto.WtStationBreakdownDto;
import com.welltech.dto.WtUserLoginDto;
import com.welltech.entity.WtStationBreakdown;
import com.welltech.entity.WtStationRepair;
import com.welltech.framework.aop.pagination.bean.MyPage;
import com.welltech.framework.aop.pagination.bean.Page;
import com.welltech.framework.aop.pagination.bean.Pagination;

/**
 * Created by Zhujia at 2017年8月22日 下午5:35:06
 */
@Service
public class ReportRecordServiceImpl {

	@Autowired
	ReportRecordDao reportRecordDao;
	@Autowired
	RepairRecordDao repairRecordDao;
	
	/**
	 * @param myPage
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<WtStationBreakdownDto> listBreakdownPage(MyPage myPage, Date beginTime, Date endTime,String[] stationIds) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Page page = new Pagination();
		page.setDefalutPageRows(0);
		page.setCurrentPage(myPage.getCurrentPage());
		map.put("page", page);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("stationIds", stationIds);
		List<WtStationBreakdownDto> result = reportRecordDao.findPageBreakdownList(map);
		myPage.setTotalPages(page.getTotalPages());
		return result;
	}

	/**
	 * 根据id获取故障信息
	 * @param id
	 * @return
	 */
	public WtStationBreakdownDto getBreakdownDto(String id) {
		return reportRecordDao.getBreakdownDto(id);
	}

	/**
	 * @param id
	 */
	public void deleteBreakdown(String id) {
		reportRecordDao.deleteBreakdown(id);
		reportRecordDao.deleteBreakdownRepairList(id);
	}

	/**
	 * @param breakdown
	 */
	public void saveBreakdown(WtStationBreakdown breakdown) {
		int maxId = reportRecordDao.getMaxBreakdownId()+1;
		breakdown.setId(maxId);
		reportRecordDao.saveBreakdown(breakdown);
	}

	/**
	 * @param repair
	 */
	public void saveRepair(WtStationRepair repair) {
		int maxId = reportRecordDao.getMaxRepairId()+1;
		repair.setId(maxId);
		reportRecordDao.saveRepair(repair);
	}
	
	public void deleteRepairRecord(String id) {
		repairRecordDao.deleteWtStationRepair(id);
	}

}
