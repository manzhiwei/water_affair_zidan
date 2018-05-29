package com.welltech.service.statistics;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.dao.WtParamDao;
import com.welltech.dao.WtStationMonitorDao;
import com.welltech.dao.indexData.IndexDataDao;
import com.welltech.dto.WaterLevelResult;
import com.welltech.dto.WaterLevelResultDetail;
import com.welltech.entity.WtDataRaw;
import com.welltech.entity.WtParam;
import com.welltech.entity.WtStation;
import com.welltech.entity.WtStationMonitor;
import com.welltech.entity.WtWaterLevel;

/**
 * 水质分级
 * @author wangxin
 *
 */
@Service
public class WaterLevelService {

	private static final Logger logger = LoggerFactory.getLogger(WaterLevelService.class);
	
	@Autowired
	private IndexDataDao indexDataDao;
	
	@Autowired
	private WtStationMonitorDao wtStationMonitorDao;
	
	@Autowired
	private WtParamDao wtParamDao;
	
	/**
	 * 查询站点设置
	 * @param stationId
	 * @return
	 */
	public List<WtStationMonitor> listMonitorByStationId(Integer stationId){
		return wtStationMonitorDao.findWtStationMonitors(stationId);
	}
	
	/**
	 * 通用参数
	 * @return
	 */
	public List<WtParam> listWtParam(){
		return wtParamDao.findAllWtParams();
	}
	
	/**
	 * 通用标准
	 * @return
	 */
	public List<WtWaterLevel> listWaterLevel(){
		return indexDataDao.findEffectiveWaterLevel(null);
	}
	
	/**
	 * 计算水质结果，为避免重复查库此方法不查库
	 * @param station 站点情况
	 * @param data 数据
	 * @param monitors 站点设置
	 * @param params 通用参数
	 * @param waterLevels 通用标准
	 * @return
	 */
	public WaterLevelResult calculateWaterLevel(@NotNull WtStation station, @NotNull WtDataRaw data, List<WtStationMonitor> monitors, 
			@NotNull List<WtParam> params, @NotNull List<WtWaterLevel> waterLevels){
		WaterLevelResult result = new WaterLevelResult();
		
		//通用参数设置为map供方便使用
		Map<String, WtParam> paramMap = new HashMap<>();
		if(params != null){
			for(WtParam param: params){
				paramMap.put(param.getParam(), param);
			}
		}
		//通用水质等级
		Map<String, WtWaterLevel> levelMap = new HashMap<>();
		if(waterLevels != null){
			for(WtWaterLevel level : waterLevels){
				String key = level.getParam() + "|" + level.getTypeCode() + "|" + level.getLevelCode();
				levelMap.put(key, level);
			}
		}
		
		// 参数类型|水质等级类型 - 水质等级
		Map<String, List<WtWaterLevel>> levelMaps = new HashMap<>();
		if(waterLevels != null){
			for(WtWaterLevel level : waterLevels){
				String key = level.getParam() + "|" + level.getTypeCode();
				List<WtWaterLevel> levels = levelMaps.getOrDefault(key, new ArrayList<>());
				levels.add(level);
				levelMaps.put(key, levels);
			}
		}
		
		// 站点参数设置
		Map<String, WtStationMonitor> monitorMaps = new HashMap<>();
		if(monitors != null){
			for (WtStationMonitor monitor : monitors) {
				monitorMaps.put(monitor.getParam(), monitor);
			}
			
		}
		
		//取站点的标准
		String levelType = station.getStationJudgeType();
		result.setLevelType(levelType);
		String finalResultCode = "1";
		if(StringUtils.isNotBlank(levelType) && data != null){
			List<WaterLevelResultDetail> details = result.getDetails();
			for (WtParam param: params) {
				//循环32路基本参数
				WaterLevelResultDetail detail = new WaterLevelResultDetail();
				//基本参数赋值
				String paramStr = param.getParam();
				detail.setTime(data.getTime());
				detail.setParam(paramStr);
				BigDecimal value = null;
				try {
					Field f = WtDataRaw.class.getDeclaredField(paramStr);
					f.setAccessible(true);
					value = (BigDecimal) f.get(data);
				} catch (Exception e) {
					e.printStackTrace();
				}
				detail.setValue(value);
				//取得该站点参数的设置
				WtStationMonitor monitor = monitorMaps.get(paramStr);
				if(monitor == null){
					monitor = new WtStationMonitor();
				}
				if("2".equals(station.getStationStandard())){
					//个性站点
					detail.setParamName(monitor.getAliasParamName());
					detail.setUnit(monitor.getAliasUnit());
				} else{
					detail.setParamName(paramMap.get(paramStr).getParamName());
					detail.setUnit(paramMap.get(paramStr).getUnit());
				}
				
				//判断标准，全部以设置的为准
				if("1".equals(station.getStationStandard())){
					detail.setDisplay("1".equals(param.getDisplay()));
				} else{
					detail.setDisplay("1".equals(monitor.getDisplay()));
				}
				boolean involved = false;//是否参与评价
				String standardLevel = null;
				if("1".equals(station.getStationStandard())){
					if("1".equals(levelType) && "1".equals(param.getInvolved())){
						involved = true;
						standardLevel = param.getHeichou();
					}
					if("2".equals(levelType) && "1".equals(param.getInvolved())){
						involved = true;
						standardLevel = param.getDibiao();
					}
				} else{
					if("1".equals(levelType) && "1".equals(monitor.getHeichouDisplay())){
						involved = true;
						standardLevel = monitor.getHeichouLevel();
					}
					if("2".equals(levelType) && "1".equals(monitor.getDibiaoDisplay())){
						involved = true;
						standardLevel = monitor.getDibiaoLevel();
					}
				}

				String standardParam = monitor.getParamAdjust();
				detail.setInvolved(involved);
				detail.setStandardLevel(standardLevel);
				detail.setStandardParam(standardParam);
				
				if(involved //参与评价 
						&& StringUtils.isNotBlank(standardParam) //设置了标准参数 
						&& StringUtils.isNotBlank(standardLevel)//设置了标准等级
						){
					String resultCode = null;
					String resultName = null;
					List<WtWaterLevel> levels = levelMaps.get(standardParam + "|" + levelType);
					if(levels != null){
						for(WtWaterLevel level : levels){
							if(checkLimit(level, value, detail)){
								resultCode = level.getLevelCode();
								resultName = level.getLevel();
								break;
							}
						}
						if(StringUtils.isNotBlank(resultCode) 
								&& resultCode.compareTo(standardLevel) > 0){
							// 比标准低，变为超标
							result.setFinalResult(false);
							detail.setFinalResult(false);
						}
						
						if(StringUtils.isNotBlank(resultCode)
								&& finalResultCode.compareTo(resultCode) < 0){
							finalResultCode = resultCode;
							result.setResultName(resultName);
						}
						
					}
					detail.setResultCode(resultCode);
					detail.setResultName(resultName);
				}
				details.add(detail);
			}
		}
		result.setResultCode(finalResultCode);
		if(StringUtils.isEmpty(result.getResultName())){
			result.setResultName("1".equals(levelType)?"合格":"Ⅰ类");
		}
		return result;
	}
	
	/**
	 * 比较是否在范围内
	 * @param level
	 * @param value
	 * @return
	 */
	private boolean checkLimit(WtWaterLevel level, BigDecimal value, WaterLevelResultDetail detail){
		if(value == null){
			return false;
		}
		if(!"1".equals(level.getHasLower())
				&& !"1".equals(level.getHasUpper())){
			return false;
		}
		
		boolean isLower = false;
		boolean isUpper = false;
		
		if("1".equals(level.getHasLower())){
			//比较下限
			BigDecimal limit = new BigDecimal(level.getLowerLimit());
			if(value.compareTo(limit) > 0
					|| (value.compareTo(limit) == 0 && "1".equals(level.getContainLower()))){
				isLower = true;
			}
		} else{
			isLower = true;
		}
		
		if("1".equals(level.getHasUpper())){
			//比较上限
			BigDecimal limit = new BigDecimal(level.getUpperLimit());
			if(value.compareTo(limit) < 0
					|| (value.compareTo(limit) == 0 && "1".equals(level.getContainUpper()))){
				isUpper = true;
			}
		} else {
			isUpper = true;
		}
		
		boolean result = isLower && isUpper;
		
		if(result){
			//在范围内
			detail.setUpperLimit("1".equals(level.getHasUpper())? new BigDecimal(level.getUpperLimit()): null);
			detail.setLowerLimit("1".equals(level.getHasLower())? new BigDecimal(level.getLowerLimit()): null);
		}
		
		return result;
	}
}
