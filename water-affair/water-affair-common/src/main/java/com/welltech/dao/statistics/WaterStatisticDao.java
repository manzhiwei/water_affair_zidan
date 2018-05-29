package com.welltech.dao.statistics;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.welltech.entity.WtWaterLevel;
import com.welltech.entity.WtWaterType;
import org.apache.ibatis.annotations.Param;

public interface WaterStatisticDao {

	/**
	 * 
	 * @param param
	 * @param startTime
	 * @param endTime
	 * @param typeCode
	 * @return
	 */
	List<Map<String, Object>> findWaterStatistic(@Param("companyIds") List<Integer> companyIds, @Param("stationIds") List<Integer> stationIds,
												 @Param("param") String param, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("typeCode") String typeCode);

}
