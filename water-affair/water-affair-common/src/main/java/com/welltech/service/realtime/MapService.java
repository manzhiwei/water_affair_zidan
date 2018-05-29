package com.welltech.service.realtime;

import java.util.List;
import java.util.Map;

import com.welltech.entity.WtCompany;

/**
 * 地图功能服务层接口
 * @author wangxin
 *
 */
public interface MapService {

	/**
	 * 得到地图中的按钮和标注点数据
	 * @return
	 */
	Map<String, Object> getDataOfMapButtons();

	/**
	 * 得到站点数据
	 * @param stationId
	 * @return
	 */
	Map<String, Object> getStationData(Integer stationId);

	/**
	 * 得到有站点的区域
	 * @return
	 */
	List<WtCompany> getCompany();
	
}
