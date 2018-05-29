/**
 * 
 */
package com.welltech.dao.sysSetting;

import java.util.List;

import com.welltech.dto.WtStationBaseDto;
import com.welltech.dto.WtStationDto;
import com.welltech.dto.WtStationMonitorDto;
import com.welltech.entity.WtCompany;
import com.welltech.entity.WtStation;
import com.welltech.entity.WtStationBase;
import com.welltech.entity.WtStationMonitor;

/**
 * Created by Zhujia at 2017年7月29日 下午4:45:02
 */
public interface PortManageDao {

	/**
	 * 查找所有的测点
	 * @return
	 */
	List<WtStationDto> findAllStation();

	/**
	 * 根据id查找测点
	 * @param id
	 * @return
	 */
	WtStation findStationById(String id);
	
	/**
	 * 根据测点id查找测点基本信息
	 * @param stationId
	 * @return
	 */
	WtStationBase findStationBaseByStationId(String stationId);

	/**
	 * 根据id删除测点
	 * @param id
	 */
	void deleteStationById(String id);
	
	/**
	 * 根据id删除测点基本信息
	 * @param id
	 */
	void deleteStationBaseById(String id);


	/**
	 * 根据测点id查找监测项
	 * @param id
	 * @return
	 */
	List<WtStationMonitorDto> findWtStationMonitorByStationId(String id);

	/**
	 * 根据监测项数据查找条目信息
	 * @param monitor
	 * @return
	 */
	WtStationMonitor findMonitorByMonitor(WtStationMonitor monitor);

	/**
	 * 获取最大的监测项id
	 * @return
	 */
	int getMaxMonitorId();

	/**
	 * 保存监测项条目
	 * @param monitor 
	 * 
	 */
	void saveMonitor(WtStationMonitor monitor);

	/**
	 * 更新监测项条目
	 * @param monitor 
	 * 
	 */
	void updateMonitor(WtStationMonitor monitor);

	/**
	 * 根据序列号查找站点
	 * @param gatewaySerial
	 * @return
	 */
	WtStation findStationBySerial(String gatewaySerial);

	/**
	 * 获取最大站点id
	 * @return
	 */
	int getMaxStationId();
	
	/**
	 * 获取最大站点基本信息id
	 * @return
	 */
	int getMaxStationBaseId();

	/**
	 * 保存站点基本信息
	 * @param base
	 */
	void saveStationBase(WtStationBase base);
	
	/**
	 * 更新站点基本信息
	 * @param base
	 */
	void updateStationBase(WtStationBase base);

	/**
	 * 保存站点信息
	 * @param station
	 */
	void saveStation(WtStation station);

	/**
	 * 更新站点信息
	 * @param station
	 */
	void updateStation(WtStation station);

	/**
	 * 获取测点和设备信息
	 * @param id
	 * @return
	 */
	WtStationBaseDto findStationBaseDtoByStationId(String id);

	/**
	 * 更新标准
	 * @param judgeType
	 */
	void updateJudgeType(String judgeType);
}
