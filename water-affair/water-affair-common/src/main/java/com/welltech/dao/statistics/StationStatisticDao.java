package com.welltech.dao.statistics;

import com.welltech.dto.StationStatisticQueryDto;
import com.welltech.dto.StationStatisticsResultDto;
import com.welltech.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;

public interface StationStatisticDao {

	/**
	 * 
	 * @param id
	 * @return
	 */
	WtStation findWtStationById(Integer id);
	
	/**
	 * 
	 * @param stationId
	 * @return
	 */
	WtStationBase findWtStationBaseByStationId(Integer stationId);
	
	/**
	 * 
	 */
	int countStationBreakdownNum(Integer stationId);
	
	/**
	 * 
	 * @param stationId
	 * @return
	 */
	WtCompany findWtCompanyByStationId(Integer stationId);

	/**
	 * 获取长期超标的参数名称
	 * @param stationId
	 * @param mcu
	 * @param querys
	 * @return
	 */
	List<WtParam> getChangqichaobiao(@Param("stationId") Integer stationId, @Param("mcu") String mcu,
									 @Param("querys") List<StationStatisticQueryDto> querys);

	/**
	 * 获取连续超标的参数名称
	 * @param stationId
	 * @param mcu
	 * @param querys
	 * @return
	 */
	List<WtParam> getLianxuchaobiao(@Param("stationId") Integer stationId, @Param("mcu") String mcu,
							  @Param("querys") List<StationStatisticQueryDto> querys);

	/**
	 * 获取偶尔超标的参数名称
	 * @param stationId
	 * @param mcu
	 * @param querys
	 * @return
	 */
	List<WtParam> getOuerchaobiao(@Param("stationId") Integer stationId, @Param("mcu") String mcu,
							@Param("querys") List<StationStatisticQueryDto> querys);

	/**
	 * 获取每个参数的超标次数
	 * @param mcu
	 * @param querys
	 * @return
	 */
	List<Integer> getChaobiaoTimes(@Param("mcu") String mcu, @Param("querys") List<StationStatisticQueryDto> querys);

	List<WtWaterLevel> getWaterLevel(@Param("stationId") Integer stationId, @Param("typeCode") String typeCode, @Param("param") String param);

	/**
	 * 获取今日参数指标超标次数
	 * @param stationId
	 * @param querys
	 * @return
	 */
	List<StationStatisticsResultDto> getTodayChaobiaoTimes(@Param("stationId") Integer stationId, @Param("mcu") String mcu,
														   @Param("querys") List<StationStatisticQueryDto> querys);

	/**
	 * 获取目标等级
	 * @param stationId
	 * @param typeCode
	 * @param displayCol
	 * @return
	 */
	String getTargetLevel(@Param("stationId") Integer stationId, @Param("typeCode") String typeCode, @Param("displayCol") String displayCol);

	/**
	 * 获取最新污染等级
	 * @param type
	 * @param typeCode
	 * @param querys
	 * @return
	 */
	String getTargetResult(@Param("mcu") String mcu, @Param("typeCode") String typeCode,
						   @Param("querys") List<StationStatisticQueryDto> querys);

	/**
	 * 获取月污染等级
	 * @param type
	 * @param typeCode
	 * @param querys
	 * @return
	 */
	String getTargetMonthResult(@Param("mcu") String mcu, @Param("typeCode") String typeCode,
						   @Param("querys") List<StationStatisticQueryDto> querys);


	/**
	 * 获取评价等级
	 * @param typeCode
	 * @param levelCode
	 * @return
	 */
	WtWaterType findOneByTypeAndLevelCode(@Param("typeCode") String typeCode, @Param("levelCode") String levelCode);
}
