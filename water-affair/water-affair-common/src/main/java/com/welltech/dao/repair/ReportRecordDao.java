/**
 * 
 */
package com.welltech.dao.repair;

import java.util.List;
import java.util.Map;

import com.welltech.dto.WtStationBreakdownDto;
import com.welltech.dto.WtUserLoginDto;
import com.welltech.entity.WtStationBreakdown;
import com.welltech.entity.WtStationRepair;

/**
 * Created by Zhujia at 2017年8月23日 下午6:34:07
 */
public interface ReportRecordDao {

	/**
	 * @param map
	 * @return
	 */
	List<WtStationBreakdownDto> findPageBreakdownList(Map<String, Object> map);

	/**
	 * @param id
	 * @return
	 */
	WtStationBreakdownDto getBreakdownDto(String id);

	/**
	 * @param id
	 */
	void deleteBreakdown(String id);

	/**
	 * @param breakdown
	 */
	void saveBreakdown(WtStationBreakdown breakdown);

	/**
	 * @return
	 */
	int getMaxBreakdownId();

	/**
	 * @return
	 */
	int getMaxRepairId();

	/**
	 * @param repair
	 */
	void saveRepair(WtStationRepair repair);

	/**
	 * @param id
	 */
	void deleteBreakdownRepairList(String id);
}
