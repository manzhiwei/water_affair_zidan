package com.welltech.service.statistics;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

import com.welltech.dto.StationStatisticQueryDto;
import com.welltech.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.dao.WtDataRawDao;
import com.welltech.dao.WtStationDao;
import com.welltech.dao.indexData.IndexDataDao;
import com.welltech.dao.statistics.StationStatisticDao;
import com.welltech.dao.sysSetting.PortManageDao;
import com.welltech.dto.WaterLevelResult;
import com.welltech.dto.WaterLevelResultDetail;
import com.welltech.dto.WtDataRawDto;
import com.welltech.service.index.UiElementService;

@Service
public class StationStatisticService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StationStatisticDao stationStatisticDao;
	
	@Autowired
	private UiElementService uiElementService;
	
	@Autowired
	private WtDataRawDao wtDataRawDao;
	
	@Autowired
	private WtStationDao wtStationDao;
	
	@Autowired
	private WaterLevelService waterLevelService;
	
	@Autowired
	private IndexDataDao indexDataDao;
	
	@Autowired
	private PortManageDao portManageDao;

	public Map<String, Object> getStationStatisticData(Integer stationId) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<WtParam> params = waterLevelService.listWtParam();
		List<String> searchParams = new ArrayList<String>();
		for(WtParam param: params){
			searchParams.add(param.getParam());//避免sql写重复内容
		}
		WtStation station = wtStationDao.findStationById(stationId);
		result.put("station", station);
		result.put("company", stationStatisticDao.findWtCompanyByStationId(stationId));
		result.put("stationBase", stationStatisticDao.findWtStationBaseByStationId(stationId));
		result.put("breakdownNum", stationStatisticDao.countStationBreakdownNum(stationId));
		WtDataRawDto nowData = wtDataRawDao.findNowWtDataRawDtoByStationId(searchParams, stationId);
		WtDataRaw monthData = wtDataRawDao.findMonthAvgWtDataRawDtoByStationId(searchParams, stationId);
		List<WtDataRaw> monthDatas = wtDataRawDao.findMonthWtDataRawDtoByStationId(stationId);

		List<WtStationMonitor> monitors = waterLevelService.listMonitorByStationId(stationId);
		List<WtWaterLevel> waterLevels = waterLevelService.listWaterLevel();
		
		result.put("nowData", nowData);//当前数据
		result.put("monthData", monthData);//月均值
		result.put("aimResult", this.getAimResult(station, monitors, params));
		result.put("nowResult", waterLevelService.calculateWaterLevel(station, nowData, monitors, params, waterLevels));
		result.put("monthResult", waterLevelService.calculateWaterLevel(station, monthData, monitors, params, waterLevels));
		
		List<String> calcParam = this.getCalcParam(station, monitors, params);
		result.put("calcParam", calcParam);//参与评价参数
		result.put("displayParam", this.getDisplayParam(station, monitors, params));
		
		List<WaterLevelResult> monthResults = new ArrayList<>();
		Map<String, Integer> monthParamResult = this.getCalcMonthLevelResult(station, monthDatas, monitors, params, waterLevels, monthResults);
		result.put("monthParamResult", monthParamResult);
		// 综合数据分析
		Map<String, LevelResultDetail> levelResultMap = getLevelResultDetail(monthResults);
		result.put("changqichaobiao",this.getChangqichaobiao(levelResultMap));
		result.put("lianxuchaobiao",this.getLianxuchaobiao(levelResultMap));
		result.put("ouerchaobiao",this.getOuerchaobiao(levelResultMap));
		
		//参数异常
		Map<String, Integer> monthParamAbnormal = this.getMonthParamAbnormal(monitors, monthDatas, station, params);
		result.put("monthParamAbnormal", monthParamAbnormal);
		
//		result.put("params", uiElementService.getParamsByStationId(stationId));
		/**
		Map<String, WtParam> map = uiElementService.getDisplayParamsByStationId(station);
		result.put("params", map);
		Set set = map.keySet();
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String param = it.next();
			params.add(param);
			srcParams.add(map.get(param).getParam());
		}
		Collections.sort(params);
		List<StationStatisticQueryDto> querys = this.getQueryCase(stationId,params,srcParams,typeCode);
		result.put("nowData", wtDataRawDao.findNowWtDataRawDtoByStationId(params, stationId));
		result.put("monthData", wtDataRawDao.findMonthAvgWtDataRawDtoByStationId(params, stationId));
		result.put("changqichaobiao",this.getChangqichaobiao(stationId,type,params,typeCode));
		result.put("lianxuchaobiao",this.getLianxuchaobiao(stationId,type,params,typeCode));
		result.put("ouerchaobiao",this.getOuerchaobiao(stationId,type,params,typeCode));
		result.put("chaobiaoTimes",this.getChaobiaoTimes(stationId,type,params,typeCode));
		result.put("todayChaobiaoTimes",this.getTodayChaobiaoTimes(stationId,type,params,srcParams,typeCode));
		result.put("heichouTarget",stationStatisticDao.getTargetLevel(stationId,"1","heichou_display"));
		result.put("heichouResult",stationStatisticDao.getTargetResult(type,"1",querys));
		result.put("heichouMonthResult",stationStatisticDao.getTargetMonthResult(type,"1",querys));
		result.put("dibiaoTarget",stationStatisticDao.getTargetLevel(stationId,"2","dibiao_display"));
		result.put("dibiaoResult",stationStatisticDao.getTargetResult(type,"2",querys));
		result.put("dibiaoMonthResult",stationStatisticDao.getTargetMonthResult(type,"2",querys));
		*/
		return result;
	}

	/**
	 * 目标水质
	 * @param station
	 * @param monitors
	 * @param params
	 * @return
	 */
	private String getAimResult(WtStation station, List<WtStationMonitor> monitors, List<WtParam> params) {
		String typeCode = StringUtils.isNotBlank(station.getStationJudgeType())? station.getStationJudgeType() : "1";
		String levelCode = "1";
		if("1".equals(station.getStationStandard())){
			//标准站点
			if(params != null){
				for(WtParam param : params){
					if("1".equals(param.getInvolved())){
						String currentCode = "1".equals(typeCode) ? param.getHeichou() : param.getDibiao();
						if(StringUtils.isNotBlank(currentCode)
								&& currentCode.compareTo(levelCode) > 0){
							levelCode = currentCode;
						}
					}
				}
			}

		} else{
			if(monitors != null){
				for(WtStationMonitor monitor : monitors){
					String currentCode = null;
					if("1".equals(typeCode) && "1".equals(monitor.getHeichouDisplay())){
						currentCode = monitor.getHeichouLevel();

					}
					if("2".equals(typeCode) && "1".equals(monitor.getDibiaoDisplay())){
						currentCode = monitor.getDibiaoDisplay();
					}
					if(StringUtils.isNotBlank(currentCode)
							&& currentCode.compareTo(levelCode) > 0){
						levelCode = currentCode;
					}
				}
			}
		}

		WtWaterType type = stationStatisticDao.findOneByTypeAndLevelCode(typeCode, levelCode);
		return type == null ? null : type.getLevel();
	}

	private class LevelResultDetail {
		private int sumCount;//总次数
		private int overCount;//超标次数
	}
	
	private Map<String, LevelResultDetail> getLevelResultDetail(List<WaterLevelResult> monthResults){
		Map<String, LevelResultDetail> result = new LinkedHashMap<>();
		if(monthResults != null){
			for(WaterLevelResult levelResult: monthResults){
				List<WaterLevelResultDetail> details = levelResult.getDetails();
				for(WaterLevelResultDetail detail : details){
					LevelResultDetail resultDetail = result.getOrDefault(detail.getParamName(), new LevelResultDetail());
					resultDetail.sumCount ++;
					if(!detail.isFinalResult() && detail.isInvolved()){
						resultDetail.overCount ++;
					}
					result.put(detail.getParamName(), resultDetail);
				}
			}
		}
		return result;
	}
	
	private List<String> getChangqichaobiao(Map<String, LevelResultDetail> waterLevelResult) {
		List<String> result = new ArrayList<>();
		Set<Entry<String, LevelResultDetail>> set = waterLevelResult.entrySet();
		for(Entry<String, LevelResultDetail> entry : set){
			LevelResultDetail detail = entry.getValue();
			if(detail.sumCount > 0){
				if((double) detail.overCount / (double) detail.sumCount > 0.8){
					result.add(entry.getKey());
				}
			}
		}
		return result;
	}

	private List<String> getLianxuchaobiao(Map<String, LevelResultDetail> waterLevelResult) {
		List<String> result = new ArrayList<>();
		Set<Entry<String, LevelResultDetail>> set = waterLevelResult.entrySet();
		for(Entry<String, LevelResultDetail> entry : set){
			LevelResultDetail detail = entry.getValue();
			if(detail.overCount >= 5){
				result.add(entry.getKey());
			}
		}
		return result;
	}
	
	private List<String> getOuerchaobiao(Map<String, LevelResultDetail> waterLevelResult) {
		List<String> result = new ArrayList<>();
		Set<Entry<String, LevelResultDetail>> set = waterLevelResult.entrySet();
		for(Entry<String, LevelResultDetail> entry : set){
			LevelResultDetail detail = entry.getValue();
			if(detail.overCount < 5 && detail.overCount > 0){
				result.add(entry.getKey());
			}
		}
		return result;
	}

	private List<String> getDisplayParam(WtStation station, List<WtStationMonitor> monitors, List<WtParam> params) {
		List<String> result = new ArrayList<>();
		//通用参数设置为map供方便使用
		Map<String, WtStationMonitor> monitorMap = new HashMap<>();
		if(params != null){
			for(WtStationMonitor monitor : monitors){
				monitorMap.put(monitor.getParam(), monitor);
			}
		}
		for(WtParam param: params){
			if("1".equals(station.getStationStandard()) && "1".equals(param.getDisplay())){
				//标准站点
				result.add(param.getParamName());
			} else if(!"1".equals(station.getStationStandard())){
				WtStationMonitor monitor = monitorMap.get(param.getParam());
				if(monitor != null && "1".equals(monitor.getDisplay())){
					result.add(monitor.getAliasParamName());
				}
			}
		}
		return result;
	}

	/**
	 * 一个月异常参数
	 * @param monitors
	 * @param monthDatas
	 * @return
	 */
	private Map<String, Integer> getMonthParamAbnormal(List<WtStationMonitor> monitors, List<WtDataRaw> monthDatas, WtStation station, List<WtParam> params) {
		//通用参数设置为map供方便使用
		Map<String, WtParam> paramMap = new HashMap<>();
		if(params != null){
			for(WtParam param: params){
				paramMap.put(param.getParam(), param);
			}
		}
		Map<String, Integer> result = new LinkedHashMap<>();
		if(monthDatas != null && monitors != null){
			for(WtDataRaw data: monthDatas){
				for(WtStationMonitor monitor: monitors){
					WtParam param = paramMap.get(monitor.getParam());
					if(param != null){
						if(("1".equals(station.getStationStandard()) && "1".equals(param.getDisplay()))
								|| (!"1".equals(station.getStationStandard())&& "1".equals(monitor.getDisplay()))
								){//参数显示
							
							double rangeMax = monitor.getRangeMax();
							double rangeMin = monitor.getRangeMin();
							
							Field f;
							Object d = null;
							try {
								f = WtDataRaw.class.getDeclaredField(monitor.getParam());
								f.setAccessible(true);
								d = f.get(data);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								e.printStackTrace();
							} catch (NoSuchFieldException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SecurityException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							BigDecimal val = d !=null? (BigDecimal) d :null;

							if(val != null){
								if(val.doubleValue() < rangeMin 
										|| val.doubleValue() > rangeMax){
									//参数异常
									if("1".equals(station.getStationStandard())){
										//标准站点
										Integer count = result.getOrDefault(param.getParamName(), 0);
										count += 1;
										result.put(param.getParamName(), count);
									} else{
										String paramName = StringUtils.isBlank(monitor.getAliasParamName())? monitor.getParam():monitor.getAliasParamName();
										
										Integer count = result.getOrDefault(paramName, 0);
										count += 1;
										result.put(paramName, count);
									}
									
								}
								
							}
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 当月参数超标情况
	 * @param station
	 * @param monthDatas
	 * @param monitors
	 * @param params
	 * @param waterLevels
	 * @return
	 */
	private Map<String, Integer> getCalcMonthLevelResult(WtStation station, List<WtDataRaw> monthDatas,
			List<WtStationMonitor> monitors, List<WtParam> params, List<WtWaterLevel> waterLevels, List<WaterLevelResult> monthResults) {
		Map<String, Integer> result = new LinkedHashMap<>();
		if(monthDatas != null){
			for(WtDataRaw data: monthDatas){
				WaterLevelResult levelResult = waterLevelService.calculateWaterLevel(station, data, monitors, params, waterLevels);
				monthResults.add(levelResult);
				List<WaterLevelResultDetail> details = levelResult.getDetails();
				for(WaterLevelResultDetail detail: details){
					if(!detail.isFinalResult()){
						//超标
						Integer count = result.getOrDefault(detail.getParamName(), 0);
						count += 1;
						result.put(detail.getParamName(), count);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 得到参与评价的参数
	 * @param station
	 * @param monitors
	 * @param params
	 * @return
	 */
	private List<String> getCalcParam(WtStation station, List<WtStationMonitor> monitors, List<WtParam> params) {
		List<String> result = new ArrayList<>();
		//通用参数设置为map供方便使用
		Map<String, WtParam> paramMap = new HashMap<>();
		if(params != null){
			for(WtParam param: params){
				paramMap.put(param.getParam(), param);
			}
		}
		if("1".equals(station.getStationJudgeType())){
			//黑臭
			for(WtStationMonitor monitor: monitors){
				if("1".equals(monitor.getHeichouDisplay())){
					if("1".equals(station.getStationStandard())){
						//标准站点
						WtParam param = paramMap.get(monitor.getParam());
						result.add(param != null? param.getParamName(): monitor.getParam());
					} else{
						result.add(monitor.getAliasParamName());
					}
				}
			}
			
		} else if("2".equals(station.getStationJudgeType())){
			//地表
			for(WtStationMonitor monitor: monitors){
				if("1".equals(monitor.getDibiaoDisplay())){
					if("1".equals(station.getStationStandard())){
						//标准站点
						WtParam param = paramMap.get(monitor.getParam());
						result.add(param != null? param.getParamName(): monitor.getParam());
					} else{
						result.add(monitor.getAliasParamName());
					}
				}
			}
		}
		return result;
	}

	private Object getTodayChaobiaoTimes(Integer stationId, String type, List<String> params, List<String> srcParams, String typeCode) {
		List<StationStatisticQueryDto> querys = this.getQueryCase(stationId,params,srcParams,typeCode);
		return stationStatisticDao.getTodayChaobiaoTimes(stationId,type,querys);
	}

	/**
	 * 获取每个参数的超标次数
	 * @param stationId
	 * @param type
	 * @param params
	 * @param typeCode
	 * @return
	 */
	private List<Integer> getChaobiaoTimes(Integer stationId, String type, List<String> params, String typeCode) {
		List<StationStatisticQueryDto> querys = this.getQueryCase(stationId,params,typeCode);
		return stationStatisticDao.getChaobiaoTimes(type,querys);
	}

	/**
	 * 获取偶尔超标的指标
	 * @param stationId
	 * @param type
	 * @param params
	 * @param typeCode
	 * @return
	 */
	private String getOuerchaobiao(Integer stationId, String type, List<String> params, String typeCode) {
		List<StationStatisticQueryDto> querys = this.getQueryCase(stationId,params,typeCode);
		return this.listToString(stationStatisticDao.getOuerchaobiao(stationId,type,querys));
	}

	/**
	 * 获取连续超标的指标
	 * @param stationId
	 * @param type
	 * @param params
	 * @param typeCode
	 * @return
	 */
	private String getLianxuchaobiao(Integer stationId, String type, List<String> params, String typeCode) {
		List<StationStatisticQueryDto> querys = this.getQueryCase(stationId,params,typeCode);
		return this.listToString(stationStatisticDao.getLianxuchaobiao(stationId,type,querys));
	}

	/**
	 * 获取长期超标的指标
	 * @param stationId
	 * @param type
	 * @param params
	 * @param typeCode
	 * @return
	 */
	private String getChangqichaobiao(Integer stationId, String type, List<String> params, String typeCode) {
		List<StationStatisticQueryDto> querys = this.getQueryCase(stationId,params,typeCode);
		return this.listToString(stationStatisticDao.getChangqichaobiao(stationId,type,querys));
	}

	/**
	 * 整合超标的where条件
	 * @param stationId
	 * @param params
	 * @return
	 */
	private List<StationStatisticQueryDto> getQueryCase(Integer stationId, List<String> params, String typeCode){
		List<StationStatisticQueryDto> querys = new ArrayList<StationStatisticQueryDto>();
		for(String param:params){
			StationStatisticQueryDto query = new StationStatisticQueryDto();
			query.setParam(param);
			/*
			List<WtWaterLevel> heichoulevels = stationStatisticDao.getWaterLevel(stationId,"1",param);
			List<WtWaterLevel> dibiaolevels = stationStatisticDao.getWaterLevel(stationId,"2",param);
			//生成黑臭where条件
			query.setHeichouCase(this.getLevelWhereCase(heichoulevels, param));
			//生成地表where条件
			query.setDibiaoCase(this.getLevelWhereCase(dibiaolevels,param));
			*/
			List<WtWaterLevel> wherelevels = stationStatisticDao.getWaterLevel(stationId,typeCode,param);
			query.setWhereCase(this.getLevelWhereCase(wherelevels,param));
			querys.add(query);
		}
		return querys;
	}

	private List<StationStatisticQueryDto> getQueryCase(Integer stationId, List<String> params, List<String> srcParams, String typeCode) {
		List<StationStatisticQueryDto> querys = new ArrayList<StationStatisticQueryDto>();
		for(int i=0;i<params.size();i++){
			StationStatisticQueryDto query = new StationStatisticQueryDto();
			query.setParam(params.get(i));
			query.setSrcParam(srcParams.get(i));
			//生成黑臭where条件
			List<WtWaterLevel> whereLevels = stationStatisticDao.getWaterLevel(stationId,typeCode,params.get(i));
			query.setWhereCase(this.getLevelWhereCase(whereLevels, params.get(i)));
			querys.add(query);
		}
		return querys;
	}

	/**
	 * list转string 用、分割
	 * @param paramList
	 * @return
	 */
	private String listToString(List<WtParam> paramList){
		StringBuilder sb = new StringBuilder(1024);
		boolean flag = false;
		if(null != paramList && paramList.size()>0){
			for(WtParam param : paramList){
				if(flag){
					sb.append("、");
				}
				sb.append(param.getParamName());
				flag=true;
			}
		}
		return sb.toString();
	}

	/**
	 * 按照区间范围生成where语句
	 * @param levels
	 * @param param
	 * @return
	 */
	private String getLevelWhereCase(List<WtWaterLevel> levels, String param) {
		StringBuilder whereCase = new StringBuilder(1024);
		boolean orflag = false;
		for(WtWaterLevel level:levels){
			if(orflag){
				whereCase.append(" or ");
			}
			whereCase.append("(");
			boolean andFlag = false;
			if("1".equals(level.getHasLower()) && "1".equals(level.getContainLower())){
				if(andFlag){
					whereCase.append(" and ");
				}
				whereCase.append(param).append(" >= ").append(level.getLowerLimit());
				andFlag = true;
			}
			if("1".equals(level.getHasLower()) && "0".equals(level.getContainLower())){
				if(andFlag){
					whereCase.append(" and ");
				}
				whereCase.append(param).append(" > ").append(level.getLowerLimit());
				andFlag = true;
			}
			if("1".equals(level.getHasUpper()) && "1".equals(level.getContainUpper())){
				if(andFlag){
					whereCase.append(" and ");
				}
				whereCase.append(param).append(" <= ").append(level.getUpperLimit());
				andFlag = true;
			}
			if("1".equals(level.getHasUpper()) && "0".equals(level.getContainUpper())){
				if(andFlag){
					whereCase.append(" and ");
				}
				whereCase.append(param).append(" < ").append(level.getUpperLimit());
				andFlag = true;
			}
			whereCase.append(")");
			orflag=true;

		}
		return whereCase.toString();
	}

	public Map<String, Object> getMobileStationData(Integer stationId) {
		Map<String, Object> result = new HashMap<String, Object>();
		WtStation station = stationStatisticDao.findWtStationById(stationId);
		result.put("station", station);
		result.put("company", stationStatisticDao.findWtCompanyByStationId(stationId));
		result.put("stationBase", stationStatisticDao.findWtStationBaseByStationId(stationId));
		Map<String, WtParam> params = uiElementService.getParamsByStationId(stationId);
		//WtDataRawDto rawDto = wtDataRawDao.findWtDataRawDtoByStationId(stationId);
		WtDataRaw dataRaw = wtDataRawDao.findLatestWtDataRaw(station.getGatewaySerial());
		result.put("nowData", dataRaw);
		WaterLevelResult levelResult = waterLevelService.calculateWaterLevel(station, dataRaw , waterLevelService.listMonitorByStationId(stationId),waterLevelService.listWtParam(),waterLevelService.listWaterLevel());
		result.put("levelResult", levelResult);
		logger.info("站点[id={}]水质评价:[{}]", stationId, levelResult);
		return result;
	}
	
	public List<WtStation> findWtStationsBySearchingPoint(String point){
		if(StringUtils.isBlank(point)){
			return null;
		}
		return wtStationDao.findWtStationsBySearchingPoint(point);
	}

	/**
	 * 趋势分析
	 * @param stationId
	 * @param dataType 1：日 2：周 3：月 4：年
	 * @return
	 */
	public List<Object> findAnalysis(Integer stationId, String dataType, String dateStr) {
		List<Object> result = new ArrayList<>();
		WtStation station = portManageDao.findStationById(stationId + "");
		if(StringUtils.isNotBlank(station.getGatewaySerial())){
			Map<String, WtParam> params = uiElementService.getParamsByStationId(stationId);
			
			Map<String, Object> map = new HashMap<>();
			map.put("mcu", station.getGatewaySerial());
			map.put("dataType", dataType);
			map.put("params", params.keySet());// P1~P32
			map.put("dateStr", dateStr);
			List<WtDataRaw> wtDatas = wtDataRawDao.findAnalysisWtData(map);
			
			if(wtDatas != null){				
				//获取水质等级
				List<WtStationMonitor> monitors = waterLevelService.listMonitorByStationId(stationId);
				List<WtParam> wtParams = waterLevelService.listWtParam();
				List<WtWaterLevel> wtLevels = waterLevelService.listWaterLevel();
				for(WtDataRaw dataRaw : wtDatas){
					WaterLevelResult levelResult = waterLevelService.calculateWaterLevel(station, dataRaw, monitors, wtParams, wtLevels);
					
					Map<String, Object> item = new HashMap<>();
					item.put("dataRaw", dataRaw);
					item.put("levelResult", levelResult);
					result.add(item);
				}
				
			}
			
		}
		
		return result;
	}

	/**
	 * 查询所有站点
	 * @return
	 */
	public Map<WtCompany, List<WtStation>> getAllStationByCompany(){
		Map<WtCompany, List<WtStation>> result = new LinkedHashMap<>();
		List<WtCompany> companies = wtStationDao.findAllCompanys();
		if(companies != null){
			for(WtCompany company : companies){
				Integer id = company.getId();
				List<WtStation> wtStations = wtStationDao.findStationsByCompanyId(id);
				result.put(company, wtStations);
			}
		}
		return result;
	}

	public List<WtDataRaw> getWtDataRowsOfMobileChart(String param, String mcuId, String type, Date date) {
		Map<String, Object> map = new HashMap<>();
		map.put("param", param);
		map.put("mcuId", mcuId);
		map.put("type", type);
		map.put("startTime", date);
		return wtDataRawDao.findWtDataRowsOfMobileChart(map);
	}

}
