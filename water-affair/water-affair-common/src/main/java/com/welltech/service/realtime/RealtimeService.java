package com.welltech.service.realtime;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.welltech.dto.WtDataRawDto;
import com.welltech.entity.WtReceivedFile;
import com.welltech.framework.aop.pagination.bean.MyPage;

public interface RealtimeService {

	/**
	 * 查询实时数据
	 * @param pointIds 
	 * @return
	 */
	List<WtDataRawDto> listDataRaws(MyPage myPage, Integer[] pointIds);
	
	/**
	 * 查询实时数据带默认分页行数
	 * @param pointIds 
	 * @return
	 */
	List<WtDataRawDto> listDataRaws(MyPage myPage, Integer[] pointIds, int rows);
	
	/**
	 * 查询变化趋势图表参数
	 * @param params
	 * @param startTime
	 * @param endTime
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> findChartWtData(String[] params, Date startTime, Date endTime, String mcu);

	/**
	 * 检测站点是否设置过别名或者单位
	 * @param stationIds
	 * @return
	 */
	List<Integer> checkStations(Integer[] stationIds);

	/**
	 * 使用默认不设置过别名或单位的参数
	 * @return
	 */
	Integer[] getDefaultStations();

	/**
	 * @param pointId
	 * @return
	 */
	List<Integer> checkStation(Integer pointId);

	/**
	 * @param params
	 * @param startTime
	 * @param endTime
	 * @param dataType
	 * @param gatewaySerial
	 * @return
	 */
	List<Map<String, Object>> findChartWtDataByDatatype(String[] params, Date startTime, Date endTime, String dataType,
			String gatewaySerial);

	/**
	 * 获取视频或图片
	 * @param sn
	 * @param type
	 * @return
	 */
	List<WtReceivedFile> findReceivedFiles(String sn, String type);
	
}
