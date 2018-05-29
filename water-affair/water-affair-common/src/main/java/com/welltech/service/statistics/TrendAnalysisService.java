/**
 * 
 */
package com.welltech.service.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.dao.statistics.TrendAnalysisDao;
import com.welltech.dto.WaterLevelResultDetail;

/**
 * Created by Zhujia at 2017年9月17日 下午5:35:01
 */
@Service
public class TrendAnalysisService {
	
	@Autowired
	private TrendAnalysisDao trendAnalysisDao;

	/**
	 * @param stationId
	 * @param dataType
	 * @return
	 */
	public List<WaterLevelResultDetail> findAnalysisDatas(String stationId, String dataType) {
		List<WaterLevelResultDetail> datas = new ArrayList<WaterLevelResultDetail>();
		switch (dataType) {
		case "1":	//日报表
			datas = this.getDailyReport(stationId,dataType);
			break;
		case "2":	//周报表
			datas = this.getWeekReport(stationId,dataType);
			break;
		case "3":	//月报表
			datas = this.getMonthReport(stationId,dataType);
			break;
		case "5":	//年报表
			datas = this.getYearReport(stationId,dataType);
			break;
		default:
			datas = this.getDailyReport(stationId,dataType);
			break;
		}
		return datas;
	}

	/**
	 * 年趋势分析
	 * @param stationId
	 * @param dataType
	 * @return
	 */
	private List<WaterLevelResultDetail> getYearReport(String stationId, String dataType) {
		List<String> params = trendAnalysisDao.getColumnParams();
		StringBuilder paramColumn = new StringBuilder(1024);
		if(null!=params && params.size()>0){
			boolean flag = false;
			for(String param : params){
				if(flag){
					paramColumn.append(",");
				}
				paramColumn.append("IFNULL(AVG(").append(param).append("),0) AS ").append(param);
				flag=true;
			}
		}
		Map<String,Object> param = new HashMap<>();
		param.put("type", stationId);
		param.put("paramColumn", paramColumn);
		param.put("timef","%Y-%m-01");
		param.put("subIndex", 1);
		param.put("subUnit", "year");
		List<WaterLevelResultDetail> datas = trendAnalysisDao.getAnalysisData(param);
		return datas;
	}

	/**
	 * 月趋势分析
	 * @param stationId
	 * @param dataType
	 * @return
	 */
	private List<WaterLevelResultDetail> getMonthReport(String stationId, String dataType) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 周趋势分析
	 * @param stationId
	 * @param dataType
	 * @return
	 */
	private List<WaterLevelResultDetail> getWeekReport(String stationId, String dataType) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 日趋势分析
	 * @param stationId
	 * @param dataType
	 * @return
	 */
	private List<WaterLevelResultDetail> getDailyReport(String stationId, String dataType) {
		// TODO Auto-generated method stub
		return null;
	}

}
