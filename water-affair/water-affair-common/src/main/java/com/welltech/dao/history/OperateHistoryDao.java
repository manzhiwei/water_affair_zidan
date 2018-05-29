/**
 * 
 */
package com.welltech.dao.history;

import java.util.List;
import java.util.Map;

import com.welltech.dto.WtUserLogDto;
import com.welltech.entity.WtUserLog;

/**
 * Created by Zhujia at 2017年8月28日 下午11:06:22
 */
public interface OperateHistoryDao {

	/**
	 * @param map
	 * @return
	 */
	List<WtUserLogDto> findPageOperateHistoryList(Map<String, Object> map);

	/**
	 * @return
	 */
	int getMaxId();

	/**
	 * @param log
	 */
	void saveOperate(WtUserLog log);

}
