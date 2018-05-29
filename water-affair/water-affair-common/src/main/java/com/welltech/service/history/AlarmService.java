package com.welltech.service.history;

import java.util.Date;
import java.util.List;

import com.welltech.entity.WtAlarmRecord;
import com.welltech.framework.aop.pagination.bean.MyPage;

public interface AlarmService {

	/**
	 * 分页查询报警记录
	 * @param myPage
	 * @param pointIds
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<WtAlarmRecord> listWtAlarmRecords(MyPage myPage, Integer[] pointIds, Date startTime, Date endTime,String typeCode);

	/**
	 * 通过id假删除
	 * @param id
	 */
	void deleteById(Integer id);
}
