package com.welltech.service.history;

import java.util.Date;
import java.util.List;

import com.welltech.dto.WtDataRawDto;
import com.welltech.entity.WtStation;
import com.welltech.framework.aop.pagination.bean.MyPage;

public interface HistoryService {
	
	/**
	 * 历史查询
	 * @param myPage
	 * @param pointIds
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<WtDataRawDto> listHistoryWtDataRawDto(MyPage myPage, Integer[] pointIds, Date startTime, Date endTime);

	/**
	 * @param myPage
	 * @param pointId
	 * @param startTime
	 * @param endTime
	 * @param dataType 数据类型
	 * @return
	 */
	Object listHistoryWtData(MyPage myPage, Integer pointId, Date startTime, Date endTime, String dataType);
	
	/**
	 * 获取站点
	 * @param stationId
	 * @return
	 */
	WtStation getStation(Integer stationId);

}
