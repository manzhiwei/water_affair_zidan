package com.welltech.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.welltech.entity.WtAlarmRecord;
import com.welltech.entity.WtCompany;
import com.welltech.entity.WtDataRaw;
import com.welltech.entity.WtStation;

public interface AlarmDao {

	/**
	 * 
	 * @param receiveTime
	 * @return
	 */
	List<WtDataRaw> findWtDataRawByReceiveTime(@Param("receiveTime") Date receiveTime);
	
	/**
	 * 
	 * @param mcuId
	 * @return
	 */
	List<WtStation> findWtStationByMcuId(@Param("mcuId") String mcuId);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	WtCompany findWtCompanyById(Integer id);
	
	/**
	 * 
	 * @param wtAlarmRecord
	 * @return
	 */
	int insertAlarmRecord(WtAlarmRecord wtAlarmRecord);

	/**
	 *
	 * @param id
	 * @return
	 */
	WtStation findWtStationById(@Param("id") Integer id);
	
}
