/**
 * 
 */
package com.welltech.service.statistics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.dao.statistics.CommonReportDao;
import com.welltech.dao.sysSetting.PageParamManageDao;
import com.welltech.dto.WtAreaStationDto;
import com.welltech.entity.WtDataRaw;
import com.welltech.entity.WtParam;
import com.welltech.entity.WtStationMonitor;

/**
 * Created by Zhujia at 2017年8月15日 下午11:09:17
 */
@Service
public class CommonReportService {

	@Autowired
	private CommonReportDao commonReportDao;
	
	@Autowired
	private PageParamManageDao pageParamManageDao;
	
	/**
	 * @return
	 */
	public List<WtAreaStationDto> getAreaStationNode() {
		return commonReportDao.getAreaStationNode();
	}
	
	/**
	 * 获取数据的黑臭污染物及等级
	 * @param dataRaw
	 * @return
	 */
	public List<WtStationMonitor> getHeichouDataLevel(WtDataRaw dataRaw){
		if(null != dataRaw){
			List<WtStationMonitor> resultList = new ArrayList<WtStationMonitor>();
			//1.获取参与黑臭评价的指标,判断该指标是否有别名,并获取原标准指标及名称(该测点那些参数显示并参与评价)
			List<WtStationMonitor> monitors = commonReportDao.getHeichouDisplay(dataRaw.getMcu());
			//2.获取评价值的当条数据中参与评价的数据(data_raw)
			try {
				if(null != monitors && monitors.size()>0){
					for(WtStationMonitor monitor:monitors){
						Map<String,String> paraMap = new HashMap<String,String>();
						String param = monitor.getParam();
						PropertyUtilsBean propertyUtilsBeanSource = new PropertyUtilsBean(); 
						double value = ((BigDecimal)propertyUtilsBeanSource.getNestedProperty(dataRaw, param)).doubleValue();
						paraMap.put("typeCode", "1");
						paraMap.put("param", param);
						paraMap.put("paramValue", value+"");
						paraMap.put("paramName", "heichou_level");
						String resultLevel = pageParamManageDao.getDiBiao(paraMap);
						paraMap.put("result", resultLevel);
						if("-".equals(resultLevel)){
							continue;
						}
						paraMap.put("stationId", monitor.getStationId()+"");
						//判断标准结果是否为污染状态 如果为污染,则存到返回结果列表
						boolean result = commonReportDao.isPollution(paraMap);
						if(result){
							resultList.add(monitor);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return resultList;
		}else
			return null;
	}

    public List<WtAreaStationDto> getAreaNode() {
		return commonReportDao.getAreaNode();
    }
}
