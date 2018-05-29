package com.welltech.service.index.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.dao.WtDataRawDao;
import com.welltech.dao.WtParamDao;
import com.welltech.dao.WtStationDao;
import com.welltech.dao.indexData.IndexDataDao;
import com.welltech.dto.RealtimeNetworkDto;
import com.welltech.dto.WaterLevelResult;
import com.welltech.entity.WtDataRaw;
import com.welltech.entity.WtParam;
import com.welltech.entity.WtStation;
import com.welltech.entity.WtStationMonitor;
import com.welltech.entity.WtWaterLevel;
import com.welltech.entity.WtWaterType;
import com.welltech.framework.aop.pagination.bean.Page;
import com.welltech.service.index.IndexDataService;
import com.welltech.service.statistics.WaterLevelService;

@Service
public class IndexDataServiceImpl implements IndexDataService {

	@Autowired
	private IndexDataDao indexDataDao;
	@Autowired
	private WtStationDao wtStationDao;
	@Autowired
	private WtDataRawDao wtDataRawDao;
	
	@Autowired
	private WtParamDao wtParamDao;

	@Autowired
	private WaterLevelService waterLevelService;
	
	@Override
	public List<RealtimeNetworkDto> findRealtimeNetworkDto() {
		return indexDataDao.findRealtimeNetworkDto();
	}

	@Override
	public List<Map<String, Object>> listSingleParam(Page page, String param, String type) {
		WtParam wtParam = wtParamDao.findByParam(param);
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("page", page);
		if(param.matches("^[p][1-9]?\\d$")){//防止sql注入
			map.put("param", param);
		} else{
			map.put("param", "p1");
		}
		if(!"0".equals(type)) {//0是全部
			map.put("rtype", type);
		}
		List<Map<String, Object>> result = new ArrayList<>();;
		List<Map<String, Object>> datas = indexDataDao.findPageSingleParam(map);
		if(datas != null && !datas.isEmpty()){
			for( Map<String, Object> data: datas) {
				Map<String, Object> r = new LinkedHashMap<>();
				r.put("point", data.get("point"));
				r.put("paramName", wtParam.getParamName());
				r.put("type", data.get("rtype"));
				r.put("value", data.get("rvalue"));
				result.add(r);
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> countWaterLevel(String typeCode) {
		Map<String,Object> data = new HashMap<>();
		Map<String,Object> result1 = new TreeMap<>();
		Map<String,Object> result = new HashMap<>();
		//1.获得站点wt_station数据然后根据stationJudgeType=typeCode类型1黑臭，2地表、获得水质类型wt_water_level；stationStandard获得是标准配置还是个性
		List<WtStation> stations = wtStationDao.findStationBy24Hour();
		//获取水质等级
		List<WtWaterLevel> wtLevels = waterLevelService.listWaterLevel();
		if(stations!=null&&stations.size()>0){
			typeCode=stations.get(0).getStationJudgeType();
			if(StringUtils.isBlank(typeCode)){//如果为空默认1
				typeCode = "1";
			}
		}
		//2.获得标准配置wt_param
		List<WtParam> wtParams = waterLevelService.listWtParam();
		//3.循环站点{
		for(WtStation s:stations){
			//4.以站点id为条件，检索当前站点的个性站点wt_station_monitor和24小时历史数据wt_data_raw
			List<WtStationMonitor> monitors = waterLevelService.listMonitorByStationId(s.getId());
			List<WtDataRaw> wtDatas = wtDataRawDao.find24HourWtDataRaw(s.getGatewaySerial());//24小时历史数据
			//5.调用方法waterLevelService.calculateWaterLevel(station, dataRaw, monitors, wtParams, wtLevels);
			String resultName="";
			String resultCode="";
			for(WtDataRaw dataRaw:wtDatas){
				WaterLevelResult levelResult = waterLevelService.calculateWaterLevel(s, dataRaw, monitors, wtParams, wtLevels);
				if(resultName.equals("")){
					resultName=levelResult.getResultName();
				}
				if(resultCode.equals("")){
					resultCode=levelResult.getResultCode();
				}else{
					if(Integer.valueOf(resultCode).compareTo(Integer.valueOf(levelResult.getResultCode()))<0){
						resultName=levelResult.getResultName();
						resultCode=levelResult.getResultCode();
					}
				}
			}
			//收集站点24小时最差结果返回数据
			Object obj=result.getOrDefault(resultName,0);
			obj=Integer.parseInt(obj.toString())+1;
			result.put(resultName, obj);
			result1.put(resultCode, resultName);
		}
		//}
		//检索wt_water_type补全为0的type放入result
		List<WtWaterType> typeCodes=indexDataDao.findWtWaterTypesByTypeCode(typeCode);
		for(WtWaterType type:typeCodes){
			Object obj = result.get(type.getLevel());
			if(obj==null){
				result.put(type.getLevel(), 0);
				result1.put(type.getLevelCode(), type.getLevel());
			}
		}
		data.put("data", result);
		data.put("data1", result1);
		data.put("typeCode", typeCode);
		return data;
	}

	@Override
	public Map<String, Object> getRealtimeMonitoring() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> stationNum = indexDataDao.countStationNum2Map();//总数
		result.put("stationNum", stationNum.size());
		
		List<Map<String, Object>> offlineNum=indexDataDao.countOfflineStationNum2Map();//离线的设备
		
		List<WtParam> wtParams = wtParamDao.findAllWtParams();
		List<String> params = new ArrayList<>();
		if(wtParams != null){
			for (WtParam param : wtParams) {
				params.add(param.getParam());
			}
		}
		
		List<Integer> data = new ArrayList<>();
		
		List<Map<String, Object>> abnormalNum = indexDataDao.countAbnormalStationNum2Map(params);//异常站点，排除离线设备
		int abnormalNumTmp=0;
		for(Map<String,Object> t:offlineNum) {//离线设备是否包含在内
			for(Map<String,Object> t1:abnormalNum) {
				if(t.containsValue(t1.get("station_id"))) {
					abnormalNumTmp++;
					break;
				}
			}
		}
		//超标站点数 所有站点数 - 黑臭合格和地表水质一类的最小数（合格数）
		int qualifiedNum = 0;
		List<Map<String, Object>> overproofNum = indexDataDao.countOverproofStationNum2Map(params);//超标站点，排除离线设备，异常站点
		int overproofNumTmp=0;
		for(Map<String,Object> t:offlineNum) {//离线设备是否包含在内
			for(Map<String,Object> t1:overproofNum) {
				if(t.containsValue(t1.get("station_id"))) {
					overproofNumTmp++;
					break;
				}
			}
		}
//		List<String> levelNames = indexDataDao.findLevelName("1");
//		params = indexDataDao.findEffectiveWaterLevelParams("1");
//		List<Map<String, Object>> buttonDatas = null;
//		if(buttonDatas != null && !buttonDatas.isEmpty()){
//			for(Map<String, Object> map : buttonDatas){
//				String level = (String) map.get("levelName");
//				if("合格".equals(level)){
//					qualifiedNum = ((Long) map.get("levelNum")).intValue();
//				}
//			}
//		}
//		
//		levelNames = indexDataDao.findLevelName("2");
//		params = indexDataDao.findEffectiveWaterLevelParams("2");
//		buttonDatas = null;
//		if(buttonDatas != null && !buttonDatas.isEmpty()){
//			for(Map<String, Object> map : buttonDatas){
//				int num = 0;
//				boolean flg = false;//是否存在基准值
//				String level = (String) map.get("levelName");
//				if("Ⅰ类".equals(level)
//						||"Ⅱ类".equals(level)
//						||"Ⅲ类".equals(level)){
//					flg = true;
//					num += ((Long) map.get("levelNum")).intValue();
//				}
//				
//				if( flg && num < qualifiedNum){
//					qualifiedNum = num;
//				}
//			}
//		}

		//正常站点数：总数-异常-超标-离线=正常站点
		int normal=stationNum.size() - (abnormalNum.size()-abnormalNumTmp)-(overproofNum.size()-overproofNumTmp)-offlineNum.size();
		data.add(normal<0?0:normal);
		
		data.add((overproofNum.size()-overproofNumTmp)<0?0:(overproofNum.size()-overproofNumTmp));

		//异常站点数
		data.add((abnormalNum.size()-abnormalNumTmp)<0?0:(abnormalNum.size()-abnormalNumTmp));
		
		//离线站点数
		data.add(offlineNum.size());
		
		result.put("data", data);
		return result;
	}

	@Override
	public List<Map<String, Object>> listOverproof(Page page) {
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("page", page);
		List<WtParam> wtParams = wtParamDao.findAllWtParams();
		List<String> params = new ArrayList<>();
		if(wtParams != null){
			for (WtParam param : wtParams) {
				params.add(param.getParam());
			}
		}
		map.put("params", params);
		
		List<Map<String, Object>> result = new ArrayList<>();;
		List<Map<String, Object>> datas = indexDataDao.findPageOverproof(map);
		if(datas != null && !datas.isEmpty()){
			for( Map<String, Object> data: datas) {
				Map<String, Object> r = new LinkedHashMap<>();
				r.put("point", data.get("point"));
				r.put("paramName", data.get("paramName"));
				r.put("value", data.get("val"));
				r.put("time", data.get("time"));
				r.put("typeCode", data.get("typeCode"));
				r.put("typeValue", data.get("typeValue"));
				result.add(r);
			}
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> fault(Page page) {
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("page", page);
		List<WtParam> wtParams = wtParamDao.findAllWtParams();
		List<String> params = new ArrayList<>();
		if(wtParams != null){
			for (WtParam param : wtParams) {
				params.add(param.getParam());
			}
		}
		map.put("params", params);
		
		List<Map<String, Object>> result = new ArrayList<>();;
		List<Map<String, Object>> datas = indexDataDao.findPageFault(map);
		if(datas != null && !datas.isEmpty()){
			for( Map<String, Object> data: datas) {
				Map<String, Object> r = new LinkedHashMap<>();
				r.put("point", data.get("point"));
				//* 1:设备修改 
				//* 2:电极维护
				Object obj=data.get("breakdown_type");
				if(obj!=null){
					int tmp=Integer.parseInt(obj.toString());
					switch (tmp)
					{
					case 1:
						r.put("breakdownType","设备修改");
						break;
					case 2:
						r.put("breakdownType","电极维护");
						break;
					default:
						r.put("breakdownType","");
						break;
					}
				}
				r.put("desc", data.get("desc"));
				result.add(r);
			}
		}
		return result;
	}
	public List<Map<String, Object>> listAbnormal(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> listFault(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
