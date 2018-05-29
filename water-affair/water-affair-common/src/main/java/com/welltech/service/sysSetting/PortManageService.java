/**
 * 
 */
package com.welltech.service.sysSetting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.common.util.ReturnEntity;
import com.welltech.dao.sysSetting.PortManageDao;
import com.welltech.dto.WtStationBaseDto;
import com.welltech.dto.WtStationDto;
import com.welltech.dto.WtStationMonitorDto;
import com.welltech.dto.WtStationMonitorList;
import com.welltech.entity.WtCompany;
import com.welltech.entity.WtStation;
import com.welltech.entity.WtStationBase;
import com.welltech.entity.WtStationMonitor;
import com.welltech.entity.WtUserRole;

/**
 * Created by Zhujia at 2017年7月29日 下午4:29:14
 */
@Service
public class PortManageService {
	
	@Autowired
	private PortManageDao portManageDao;

	/**
	 * @return
	 */
	public List<WtStationDto> findAllStation() {
		return portManageDao.findAllStation();
	}

	/**
	 * @param station
	 * @param base 
	 */
	public void addStation(WtStation station, WtStationBase base) {
		int maxStationId = portManageDao.getMaxStationId()+1;
		station.setId(maxStationId);
		portManageDao.saveStation(station);

		int maxStationBaseId = portManageDao.getMaxStationBaseId()+1;
		base.setId(maxStationBaseId);
		base.setStationId(maxStationId);
		portManageDao.saveStationBase(base);
	}

	
	/**
	 * @param station
	 * @param base
	 */
	public void updateStation(WtStation station, WtStationBase base) {
		WtStationBase wtStationBase = portManageDao.findStationBaseByStationId(station.getId()+"");
		if(null==wtStationBase){
			//为空 重新插入
			int maxStationBaseId = portManageDao.getMaxStationBaseId()+1;
			base.setId(maxStationBaseId);
			portManageDao.saveStationBase(base);
		}else{
			//不为空 更新操作
			portManageDao.updateStationBase(base);
		}
		//更新用户
		portManageDao.updateStation(station);
	}

	
	
	/**
	 * @param id
	 * @return
	 */
	public WtStation findStationById(String id) {
		return portManageDao.findStationById(id);
	}

	/**
	 * @param id
	 */
	public void deleteStationById(String id) {
		portManageDao.deleteStationById(id);
		portManageDao.deleteStationBaseById(id);
	}

	/**
	 * @param id
	 * @return
	 */
	public List<WtStationMonitorDto> findWtStationMonitorByStationId(Integer id) {
		return portManageDao.findWtStationMonitorByStationId(id.toString());
	}

	/**
	 * @param monitors
	 * @return
	 */
	public void updateMonitorValue(WtStationMonitorList monitors) {
		List<WtStationMonitor> stationMonitorList = monitors.getMonitors();
		for(WtStationMonitor monitor:stationMonitorList){
			WtStationMonitor stationMonitor = portManageDao.findMonitorByMonitor(monitor);
			if(null==stationMonitor){
				//查找监测项为空,新建监测项
				int maxId = portManageDao.getMaxMonitorId()+1;
				monitor.setId(maxId);
				portManageDao.saveMonitor(monitor);
			}else{
				//不为空,更新监测项
				monitor.setId(stationMonitor.getId());
				portManageDao.updateMonitor(monitor);
			}
		}
	}

	/**
	 * @param gatewaySerial
	 * @return
	 */
	public WtStation findStationBySerial(String gatewaySerial) {
		return portManageDao.findStationBySerial(gatewaySerial);
	}

	/**
	 * @param id
	 * @return
	 */
	public WtStationBaseDto findStationBaseDtoByStationId(String id) {
		return portManageDao.findStationBaseDtoByStationId(id);
	}

	/**
	 *
	 */
	public void saveJudgeType(String judgeType){
		portManageDao.updateJudgeType(judgeType);
	}

}
