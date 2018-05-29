/**
 * 
 */
package com.welltech.dao.statistics;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.welltech.dto.WtAreaStationDto;
import com.welltech.entity.WtStationMonitor;

/**
 * Created by Zhujia at 2017年8月15日 下午11:19:38
 */
public interface CommonReportDao {

	/**
	 * @return
	 */
	List<WtAreaStationDto> getAreaStationNode();

	/**
	 * @param mcu
	 * @return
	 */
	List<WtStationMonitor> getHeichouDisplay(@Param("mcu") String mcu);

	/**
	 * @param result
	 * @return
	 */
	String isPollution(String result);

	/**
	 * @param paraMap
	 * @return
	 */
	boolean isPollution(Map<String, String> paraMap);

	/**
	 *
	 * @return
	 */
    List<WtAreaStationDto> getAreaNode();
}
