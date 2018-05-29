/**
 * 
 */
package com.welltech.dao.repair;

import java.util.List;
import java.util.Map;

import com.welltech.dto.WtStationBreakdownDto;
import com.welltech.entity.WtStationRepair;

/**
 * Created by Zhujia at 2017年8月27日 下午2:27:53
 */
public interface RepairRecordDao {

	/**
	 * @param map
	 * @return
	 */
	List<WtStationRepair> findPageRepairList(Map<String, Object> map);

    List<WtStationRepair> findRepairList(Map<String, Object> map);
    
    int deleteWtStationRepair(String id);
}
