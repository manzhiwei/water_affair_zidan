package com.welltech.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.welltech.entity.WtStationMonitor;

public interface WtStationMonitorDao {

	/**
	 * 查询别名设置过的站点
	 * @param stationId
	 * @return
	 */
	List<WtStationMonitor> findSetAliasByStationId(Integer stationId);

	/**
	 * 查询别名设置过的站点
	 * @param stationId
	 * @return
	 */
	List<WtStationMonitor> findDisplaySetAliasByStationId(Integer stationId);
	
	/**
	 * 站点设置过别名的参数数量
	 * @param stationId
	 * @return
	 */
	int countAliasDisplay(Integer stationId);
	
	/**
	 * 站点参数配置
	 * @param stationId
	 * @return
	 */
	List<WtStationMonitor> findWtStationMonitors(@Param("stationId") Integer stationId);
}
