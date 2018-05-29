package com.welltech.dao;

import java.util.List;
import java.util.Map;

import com.welltech.entity.WtCompany;
import com.welltech.entity.WtStation;

public interface WtStationDao {

	/**
	 * 通过站点名称查询
	 * @param point
	 * @return
	 */
	List<WtStation> findWtStationsBySearchingPoint(String point);
	
	/**
	 * 有站点的区域
	 * @return
	 */
	List<WtCompany> findHasStationsCompany();

	/**
	 * 根据id查找站点信息
	 * @param stationId
	 * @return
	 */
    WtStation findStationById(Integer stationId);
    
    /**
     * 所有站点
     * @return
     */
    List<WtStation> findAllStations();

	/**
	 * 根据测点类型查找测点
	 * @param typeCode
	 * @return
	 */
	List<WtStation> findStationByType(String typeCode);
	
	/**
     * 24小时内活动的站点
     * @return
     */
    List<WtStation> findStationBy24Hour();

	/**
	 * 查询时所有区域
	 */
	List<WtCompany> findAllCompanys();

	/**
	 * 通过所属区域id查询
	 * @param companyId
	 * @return
	 */
	List<WtStation> findStationsByCompanyId(Integer companyId);
}
