/**
 * 
 */
package com.welltech.dao.statistics;

import java.util.List;
import java.util.Map;

import com.welltech.dto.WaterLevelResultDetail;

/**
 * Created by Zhujia at 2017年9月18日 上午12:46:59
 */
public interface TrendAnalysisDao {

	/**
	 * 获取需要进行分析的列明
	 * @return
	 */
	List<String> getColumnParams();

	/**
	 * @param param
	 * @return
	 */
	List<WaterLevelResultDetail> getAnalysisData(Map<String, Object> param);

}
