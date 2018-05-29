package com.welltech.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.welltech.dto.WtDataRawDto;
import com.welltech.entity.WtDataRaw;

/**
 * 原始数据dao
 * @author wangxin
 *
 */
public interface WtDataRawDao {

	/**
	 * 查询最新的数据
	 * 暂时做全部查询
	 * @return
	 */
	List<WtDataRawDto> findPageLatestWtDataRaws(Map<String,Object> map);
	
	/**
	 * 查询数据
	 */
	List<WtDataRawDto> findPageWtDataRaws(Map<String,Object> map);
	
	/**
	 * 变化趋势图表数据
	 * @param params
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<Map<String, Object>> findChartWtData(@Param("params") String[] params, @Param("startTime") Date startTime, @Param("endTime") Date endTime,
			@Param("mcu") String mcu);
	
	/**
	 * 查询最新数据
	 * @param id
	 * @return
	 */
	WtDataRawDto findWtDataRawDtoByStationId(Integer id);

	/**
	 * 查询最新数据
	 * @param id
	 * @return
	 */
	WtDataRawDto findNowWtDataRawDtoByStationId(@Param("params") List<String> params, @Param("stationId") Integer id);

	/**
	 * 查询一个月的平均数据
	 * @param stationId
	 * @return
	 */
	WtDataRaw findMonthAvgWtDataRawDtoByStationId(@Param("params") List<String> params, @Param("stationId") Integer stationId);
	
	/**
	 * 查询一个月的数据
	 * @param stationId
	 * @return
	 */
	List<WtDataRaw> findMonthWtDataRawDtoByStationId(@Param("stationId") Integer stationId);

	/**
	 * @param map
	 * @return
	 */
	List<WtDataRawDto> findPageWtDataRaw(Map<String, Object> map);

	/**
	 * 手机端趋势分析数据
	 * @param map
	 * @return
	 */
	List<WtDataRaw> findAnalysisWtData(Map<String, Object> map);
	
	/**
	 * 根据id查找单条数据
	 * @param id
	 * @return
	 */
	WtDataRaw findById(Integer id);
	
	/**
	 * 查询最新的数据
	 * 暂时做全部查询
	 * @return
	 */
	WtDataRaw findLatestWtDataRaw(String mcu);
	
	/**
	 * 查询24小时内数据
	 * 而且只有station中有的
	 * @return
	 */
	List<WtDataRaw> find24HourWtDataRaw(String mcu);

	/**
	 * 微信站点分析图表
	 * @param map
	 * @return
	 */
	List<WtDataRaw> findWtDataRowsOfMobileChart(Map<String, Object> map);
}
