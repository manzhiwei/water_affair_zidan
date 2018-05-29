/**
 * 
 */
package com.welltech.service.sysSetting;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.*;

import com.welltech.dao.WtParamDao;
import com.welltech.dao.WtStationDao;
import com.welltech.entity.WtStation;
import com.welltech.entity.WtWaterLevel;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.dao.sysSetting.PageParamManageDao;
import com.welltech.dto.WtParamDataDto;
import com.welltech.dto.WtParamQueryDto;
import com.welltech.entity.WtParam;

/**
 * Created by Zhujia at 2017年8月5日 下午2:46:41
 */
@Service
public class PageParamManageService {

	@Autowired
	private PageParamManageDao pageParamManageDao;

	@Autowired
	private WtStationDao stationDao;

	@Autowired
	private WtParamDao wtParamDao;
	
	/**
	 * @return
	 */
	public List<WtParam> findAllParams() {
		return pageParamManageDao.findAllParams();
	}

	/**
	 * @param wtParam
	 * @return
	 */
	public WtParam changeStatus(WtParam wtParam) {
		WtParam param = pageParamManageDao.findParamById(wtParam.getId());
		param.setDisplay(wtParam.getDisplay());
		pageParamManageDao.update(param);
		return param;
	}

	/**
	 * @param stationId 
	 * @param paramMap
	 * @return
	 */
	public List<WtParam> findAllDisplayParams(Map paramMap, String stationId) {
		WtStation station = stationDao.findStationById(Integer.parseInt(stationId));
		List<WtParam> params = new ArrayList<WtParam>();
		if("1".equals(station.getStationStandard())){
			params = pageParamManageDao.getDisplayParamList();
		}else{
			params = pageParamManageDao.findAllDisplayParamsList(stationId);
//			params = wtParamDao.findStatisticsDisplayWtParams(stationId);
		}
		for(WtParam param : params){
			if(null!=paramMap.get(param.getParam()) && "on".equals(((String[])paramMap.get(param.getParam()))[0])){
				param.setDisplay("2");
			}
		}
		return params;
	}

	/**
	 * @param queryDto
	 * @param paramMap 
	 * @return
	 */
	public Map<String, Object> findAllDisplayData(WtStation station, WtParamQueryDto queryDto, Map paramMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(null==queryDto.getReportType())	//如果报表类型传空 则默认选择日报表
			return this.getTimeReport(station,queryDto,paramMap);
		switch (queryDto.getReportType()) {
		case "0":	//时段报表
			result = this.getTimeReport(station,queryDto,paramMap);
			break;
		case "1":	//日报表
			result = this.getDailyReport(station,queryDto,paramMap);
			break;
		case "2":	//周报表
			result = this.getWeekReport(station,queryDto,paramMap);
			break;
		case "3":	//月报表
			result = this.getMonthReport(station,queryDto,paramMap);
			break;
		case "4":	//季报表 	--已废弃
			result = this.getSeasonReport(queryDto,paramMap);
			break;
		case "5":	//年报表
			result = this.getYearReport(station,queryDto,paramMap);
			break;
		default:
			result = this.getTimeReport(station,queryDto,paramMap);
			break;
		}
		return result;
	}

	/**
	 * @param queryDto
	 * @param paramMap
	 * @return
	 */
	private Map<String, Object> getTimeReport(WtStation station, WtParamQueryDto queryDto, Map paramMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		//时报4小时统计一次 分别为:0:00 4:00 8:00 12:00 16:00 20:00
		//先获取显示的参数
		List<String> params = new ArrayList<>();
		if("1".equals(station.getStationStandard())){
			params = pageParamManageDao.getDisplayParam();
		}else{
			params = pageParamManageDao.findAllDisplayParams(station.getId()+"");
		}
		StringBuilder sb = new StringBuilder(1024);
		StringBuilder avgSb = new StringBuilder(1024);	//字段字符串拼接
		StringBuilder maxSb = new StringBuilder(1024);	//字段字符串拼接
		StringBuilder minSb = new StringBuilder(1024);	//字段字符串拼接
		Map<String, Object> map = new HashMap<String, Object>();	//sql查询参数
		List<WtParamDataDto> datas = new ArrayList<WtParamDataDto>();	//返回结果集
		if(null != params&& params.size()>0){
			for(String param :params){
				//TODO 循环遍历该参数是否要查询
				if((null!=paramMap.get(param) && "on".equals(((String[])paramMap.get(param))[0])) || queryDto.isFirstLoad()){
					sb.append("ifnull(round(").append(param).append(",4),'-') as ").append(param).append(",");
					avgSb.append("ifnull(round(AVG(").append(param).append("),4),'-') as ").append(param).append(",");
					maxSb.append("ifnull(round(MAX(").append(param).append("),4),'-') as ").append(param).append(",");
					minSb.append("ifnull(round(MIN(").append(param).append("),4),'-') as ").append(param).append(",");
				}
			}
			//将字段值转化为字段列 p1,p2,p3,p4....
			String sbColumn = sb.toString().substring(0,sb.toString().length()-1);
			String avgParamColumn = avgSb.toString().substring(0, avgSb.toString().length()-1);
			String maxParamColumn = maxSb.toString().substring(0, maxSb.toString().length()-1);
			String minParamColumn = minSb.toString().substring(0, minSb.toString().length()-1);
			map.put("column",sbColumn);
			map.put("paramColumn", avgParamColumn);
			if(StringUtils.isBlank(queryDto.getType())){
				map.put("mcu","25769738752");
			}else{
				map.put("mcu",queryDto.getType());
			}
			long timeDiff = queryDto.getEndTime().getTime() - queryDto.getStartTime().getTime();	//开始时间和结束时间相差的毫秒数
			int timeUnit = new BigDecimal(timeDiff).divide(new BigDecimal(1000 * 60 * 60 * 4)).setScale(0, BigDecimal.ROUND_UP).intValue();	//4小时为1个时间点,小数点向上进
			map.put("startTime",queryDto.getStartTime());
			map.put("endTime",queryDto.getEndTime());
			datas = pageParamManageDao.getDailyReportDataList(map);
			//TODO 根据列名字查询每个时段的值
			/*
			for(int i=0;i<timeUnit;i++){
				map.put("index",i*4);
				map.put("last", (i+1)*4);
				WtParamDataDto data = pageParamManageDao.getDailyReportData(map);
				if(data==null)
					continue;
				WtParamDataDto dataDate = pageParamManageDao.getDailyReportDate(map);
				if(null == data){
					data = dataDate;
				}else{
					data.setEndDate(dataDate.getEndDate());
					data.setStartDate(dataDate.getStartDate());
				}
				data.setDataType("0");
				datas.add(data);
			}
			*/

			result.put("datas", datas);
			result = this.getMaxMinAvgData(datas,result,station);
			//获取最小值,最大值,平均值
			if(null!=datas && datas.size()>0){
				List<WtParamDataDto> dtos = (List<WtParamDataDto>)result.get("maxDatas");
				List<WtParamDataDto> dtos1 = (List<WtParamDataDto>)result.get("minDatas");
				WtParamDataDto data = dtos.get(0);
				WtParamDataDto heiChouData = this.getHeiChou(data, station);
				WtParamDataDto diBiaoData = this.getDiBiao(data, station);
				WtParamDataDto data1 = dtos1.get(0);
				WtParamDataDto heiChouData1 = this.getHeiChou(data1, station);
				WtParamDataDto diBiaoData1 = this.getDiBiao(data1, station);

				String pollutant = "";
				Map<String,String> pollutantMap=new HashMap<String,String>();//记录最差级别的评级
				Map<String,String> pollutantMap1=new HashMap<String,String>();//记录最差级别的评级
				if(null != heiChouData){
					heiChouData.setDataType("4");
					datas.add(heiChouData);
					result.put("heichouDatas", datas);
					datas = new ArrayList<WtParamDataDto>();
					String heichou = getHeichouLevel(heiChouData,pollutant);
					result.put("heichou", heichou);
				}
				WtParamDataDto mergeDibiao = null;
				if(null != diBiaoData){
					datas = new ArrayList<WtParamDataDto>();
					String dibiao = getDibiaoLevel(diBiaoData,pollutantMap);
					String dibiao1 = getDibiaoLevel(diBiaoData1,pollutantMap1);
					dibiao=compareLevel(dibiao, dibiao1);
					
					//根据最大值和最小值的比较得出一个最差评价然后赋值
					mergeDibiao=compareLevel(diBiaoData,diBiaoData1);
					mergeDibiao.setDataType("5");
					datas.add(mergeDibiao);
					result.put("dibiaoDatas", datas);
					result.put("dibiao", dibiao);
					//根据合并的mergeDibiao将有数据的pn赋值给pollutant并当做参数传入到getParamNameByParam方法中
					pollutant+=mergeDibiao.getPollutant();
				}
				if(StringUtils.isNoneBlank(pollutant)){
					result.put("pollutant", getParamNameByParam(station,pollutant,mergeDibiao));
				}
			}
			map.put("paramColumn", avgParamColumn);
			WtParamDataDto data = pageParamManageDao.getDailyReportData(map);
			if(null!=data){
				data.setDataType("3");
			}
			datas = new ArrayList<WtParamDataDto>();
			datas.add(data);
			result.put("avgDatas", datas);
		}
		return result;
	}

	/**
	 * @return
	 */
	private Map<String, Object> getYearReport(WtStation station, WtParamQueryDto queryDto, Map paramMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		//统计近一周7天 每日的平均值
		//先获取显示的参数
		List<String> params = new ArrayList<>();
		if("1".equals(station.getStationStandard())){
			params = pageParamManageDao.getDisplayParam();
		}else{
			params = pageParamManageDao.findAllDisplayParams(station.getId()+"");
		}
		StringBuilder avgSb = new StringBuilder(1024);	//字段字符串拼接
		StringBuilder maxSb = new StringBuilder(1024);	//字段字符串拼接
		StringBuilder minSb = new StringBuilder(1024);	//字段字符串拼接
		Map<String, Object> map = new HashMap<String, Object>();	//sql查询参数
		List<WtParamDataDto> datas = new ArrayList<WtParamDataDto>();	//返回结果集
		if(null != params&& params.size()>0){
			for(String param :params){
				//TODO 循环遍历该参数是否要查询
				if((null!=paramMap.get(param) && "on".equals(((String[])paramMap.get(param))[0])) || queryDto.isFirstLoad()){
					avgSb.append("ifnull(round(AVG(").append(param).append("),4),'-') as ").append(param).append(",");
					maxSb.append("ifnull(round(MAX(").append(param).append("),4),'-') as ").append(param).append(",");
					minSb.append("ifnull(round(MIN(").append(param).append("),4),'-') as ").append(param).append(",");
				}
			}
			//将字段值转化为字段列 p1,p2,p3,p4....
			String avgParamColumn = avgSb.toString().substring(0, avgSb.toString().length()-1);
			String maxParamColumn = maxSb.toString().substring(0, maxSb.toString().length()-1);
			String minParamColumn = minSb.toString().substring(0, minSb.toString().length()-1);
			map.put("paramColumn", avgParamColumn);
			map.put("subIndex", 1);
			map.put("subUnit", "year");
			map.put("format", "%Y-%m");
			map.put("searchDate", queryDto.getYear());
			if(StringUtils.isBlank(queryDto.getType())){
				map.put("mcu","25769738752");
			}else{
				map.put("mcu",queryDto.getType());
			}
			//TODO 根据列名字查询每个时段的值
			map.put("dataType", 0);
			datas = pageParamManageDao.getReportDataList(map);
			result.put("datas", datas);
			result = this.getMaxMinAvgData(datas,result,station);
			//获取最小值,最大值,平均值
			if(null!=datas && datas.size()>0){
				List<WtParamDataDto> dtos = (List<WtParamDataDto>)result.get("maxDatas");
				WtParamDataDto data = dtos.get(0);
				WtParamDataDto heiChouData = this.getHeiChou(data, station);
				WtParamDataDto diBiaoData = this.getDiBiao(data, station);
				List<WtParamDataDto> dtos1 = (List<WtParamDataDto>)result.get("minDatas");
				WtParamDataDto data1 = dtos1.get(0);
				WtParamDataDto heiChouData1 = this.getHeiChou(data1, station);
				WtParamDataDto diBiaoData1 = this.getDiBiao(data1, station);
				String pollutant = "";
				Map<String,String> pollutantMap=new HashMap<String,String>();//记录最差级别的评级
				Map<String,String> pollutantMap1=new HashMap<String,String>();//记录最差级别的评级
				if(null != heiChouData){
					heiChouData.setDataType("4");
					datas.add(heiChouData);
					result.put("heichouDatas", datas);
					datas = new ArrayList<WtParamDataDto>();
					String heichou = getHeichouLevel(heiChouData,pollutant);
					result.put("heichou", heichou);
				}
				WtParamDataDto mergeDibiao=null;
				if(null != diBiaoData){
					datas = new ArrayList<WtParamDataDto>();
					String dibiao = getDibiaoLevel(diBiaoData,pollutantMap);
					String dibiao1 = getDibiaoLevel(diBiaoData1,pollutantMap1);
					dibiao=compareLevel(dibiao, dibiao1);
					
					//根据最大值和最小值的比较得出一个最差评价然后赋值
					mergeDibiao=compareLevel(diBiaoData,diBiaoData1);
					mergeDibiao.setDataType("5");
					datas.add(mergeDibiao);
					result.put("dibiaoDatas", datas);
					result.put("dibiao", dibiao);
					pollutant+=mergeDibiao.getPollutant();
				}

				if(StringUtils.isNoneBlank(pollutant)){
					result.put("pollutant", getParamNameByParam(station,pollutant,mergeDibiao));
				}
			}
			map.put("paramColumn", avgParamColumn);
			WtParamDataDto data = pageParamManageDao.getReportData(map);
			if(null!=data){
				data.setDataType("3");
			}
			datas = new ArrayList<WtParamDataDto>();
			datas.add(data);
			result.put("avgDatas", datas);
		}
		return result;
	}

	/**
	 * 需求不需要 已废弃
	 * @return
	 */
	@Deprecated
	private Map<String, Object> getSeasonReport(WtParamQueryDto queryDto, Map paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 */
	private Map<String, Object> getMonthReport(WtStation station, WtParamQueryDto queryDto, Map paramMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		//统计近一周7天 每日的平均值
		//先获取显示的参数
		List<String> params = new ArrayList<>();
		if("1".equals(station.getStationStandard())){
			params = pageParamManageDao.getDisplayParam();
		}else{
			params = pageParamManageDao.findAllDisplayParams(station.getId()+"");
		}
		StringBuilder avgSb = new StringBuilder(1024);	//字段字符串拼接
		StringBuilder maxSb = new StringBuilder(1024);	//字段字符串拼接
		StringBuilder minSb = new StringBuilder(1024);	//字段字符串拼接
		Map<String, Object> map = new HashMap<String, Object>();	//sql查询参数
		List<WtParamDataDto> datas = new ArrayList<WtParamDataDto>();	//返回结果集
		if(null != params&& params.size()>0){
			for(String param :params){
				//TODO 循环遍历该参数是否要查询
				if((null!=paramMap.get(param) && "on".equals(((String[])paramMap.get(param))[0])) || queryDto.isFirstLoad()){
					avgSb.append("ifnull(round(AVG(").append(param).append("),4),'-') as ").append(param).append(",");
					maxSb.append("ifnull(round(MAX(").append(param).append("),4),'-') as ").append(param).append(",");
					minSb.append("ifnull(round(MIN(").append(param).append("),4),'-') as ").append(param).append(",");
				}
			}
			//将字段值转化为字段列 p1,p2,p3,p4....
			String avgParamColumn = avgSb.toString().substring(0, avgSb.toString().length()-1);
			String maxParamColumn = maxSb.toString().substring(0, maxSb.toString().length()-1);
			String minParamColumn = minSb.toString().substring(0, minSb.toString().length()-1);
			map.put("paramColumn", avgParamColumn);
			map.put("subIndex", 1);
			map.put("subUnit", "month");
			map.put("format", "%Y-%m-%d");
			map.put("searchDate", queryDto.getMonth());
			if(StringUtils.isBlank(queryDto.getType())){
				map.put("mcu","25769738752");
			}else{
				map.put("mcu",queryDto.getType());
			}
			//TODO 根据列名字查询每个时段的值
			map.put("dataType", 0);
			datas = pageParamManageDao.getReportDataList(map);
			result.put("datas", datas);
			result = this.getMaxMinAvgData(datas,result,station);
			//获取最小值,最大值,平均值
			if(null!=datas && datas.size()>0){
				List<WtParamDataDto> dtos = (List<WtParamDataDto>)result.get("maxDatas");
				WtParamDataDto data = dtos.get(0);
				WtParamDataDto heiChouData = this.getHeiChou(data, station);
				WtParamDataDto diBiaoData = this.getDiBiao(data, station);
				List<WtParamDataDto> dtos1 = (List<WtParamDataDto>)result.get("minDatas");
				WtParamDataDto data1 = dtos1.get(0);
				WtParamDataDto heiChouData1 = this.getHeiChou(data1, station);
				WtParamDataDto diBiaoData1 = this.getDiBiao(data1, station);

				String pollutant = "";
				Map<String,String> pollutantMap=new HashMap<String,String>();//记录最差级别的评级
				Map<String,String> pollutantMap1=new HashMap<String,String>();//记录最差级别的评级
				if(null != heiChouData){
					heiChouData.setDataType("4");
					datas.add(heiChouData);
					result.put("heichouDatas", datas);
					datas = new ArrayList<WtParamDataDto>();
					String heichou = getHeichouLevel(heiChouData,pollutant);
					result.put("heichou", heichou);
				}
				WtParamDataDto mergeDibiao=null;
				if(null != diBiaoData){
					datas = new ArrayList<WtParamDataDto>();
					String dibiao = getDibiaoLevel(diBiaoData,pollutantMap);
					String dibiao1 = getDibiaoLevel(diBiaoData1,pollutantMap1);
					dibiao=compareLevel(dibiao, dibiao1);
					
					//根据最大值和最小值的比较得出一个最差评价然后赋值
					mergeDibiao=compareLevel(diBiaoData,diBiaoData1);
					mergeDibiao.setDataType("5");
					datas.add(mergeDibiao);
					result.put("dibiaoDatas", datas);
					result.put("dibiao", dibiao);
					pollutant+=mergeDibiao.getPollutant();
				}
				if(StringUtils.isNoneBlank(pollutant)){
					result.put("pollutant", getParamNameByParam(station,pollutant,mergeDibiao));
				}
			}
			map.put("paramColumn", avgParamColumn);
			WtParamDataDto data = pageParamManageDao.getReportData(map);
			if(null!=data){
				data.setDataType("3");
			}
			datas = new ArrayList<WtParamDataDto>();
			datas.add(data);
			result.put("avgDatas", datas);
		}
		return result;
	}

	/**
	 * @return
	 */
	private Map<String, Object> getWeekReport(WtStation station, WtParamQueryDto queryDto, Map paramMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		//统计近一周7天 每日的平均值
		//先获取显示的参数
		List<String> params = new ArrayList<>();
		if("1".equals(station.getStationStandard())){
			params = pageParamManageDao.getDisplayParam();
		}else{
			params = pageParamManageDao.findAllDisplayParams(station.getId()+"");
		}
		StringBuilder avgSb = new StringBuilder(1024);	//字段字符串拼接
		StringBuilder maxSb = new StringBuilder(1024);	//字段字符串拼接
		StringBuilder minSb = new StringBuilder(1024);	//字段字符串拼接
		Map<String, Object> map = new HashMap<String, Object>();	//sql查询参数
		List<WtParamDataDto> datas = new ArrayList<WtParamDataDto>();	//返回结果集
		if(null != params&& params.size()>0){
			for(String param :params){
				//TODO 循环遍历该参数是否要查询
				if((null!=paramMap.get(param) && "on".equals(((String[])paramMap.get(param))[0])) || queryDto.isFirstLoad()){
					avgSb.append("ifnull(round(AVG(").append(param).append("),4),'-') as ").append(param).append(",");
					maxSb.append("ifnull(round(MAX(").append(param).append("),4),'-') as ").append(param).append(",");
					minSb.append("ifnull(round(MIN(").append(param).append("),4),'-') as ").append(param).append(",");
				}
			}
			//将字段值转化为字段列 p1,p2,p3,p4....
			String avgParamColumn = avgSb.toString().substring(0, avgSb.toString().length()-1);
			String maxParamColumn = maxSb.toString().substring(0, maxSb.toString().length()-1);
			String minParamColumn = minSb.toString().substring(0, minSb.toString().length()-1);
			map.put("paramColumn", avgParamColumn);
			map.put("subIndex", 1);
			map.put("subUnit", "week");
			map.put("format", "%Y-%m-%d");
			map.put("searchDate", queryDto.getDate());
			if(StringUtils.isBlank(queryDto.getType())){
				map.put("mcu","25769738752");
			}else{
				map.put("mcu",queryDto.getType());
			}
			//TODO 根据列名字查询每个时段的值
			map.put("dataType", 0);
			datas = pageParamManageDao.getReportDataList(map);
			result.put("datas", datas);
			datas = new ArrayList<WtParamDataDto>();
			//获取最小值,最大值,平均值
			map.put("paramColumn", minParamColumn);
			WtParamDataDto data = pageParamManageDao.getReportData(map);
			if(null!=data){
				data.setDataType("1");
			}
			datas.add(data);
			result.put("minDatas", datas);
			datas = new ArrayList<WtParamDataDto>();
			map.put("paramColumn", maxParamColumn);
			data = pageParamManageDao.getReportData(map);
			if(null!=data){
				data.setDataType("2");
			}
			datas.add(data);
			result.put("maxDatas", datas);
			datas = new ArrayList<WtParamDataDto>();
			map.put("paramColumn", avgParamColumn);
			data = pageParamManageDao.getReportData(map);
			if(null!=data){
				data.setDataType("3");
			}
			datas.add(data);
			result.put("avgDatas", datas);
			datas = new ArrayList<WtParamDataDto>();
		}
		result.put("datas", datas);
		return result;
	}

	/**
	 * @return
	 */
	private Map<String, Object> getDailyReport(WtStation station, WtParamQueryDto queryDto, Map paramMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		//日报最近24小时每小时统计一次
		//先获取显示的参数
		List<String> params = new ArrayList<>();
		if("1".equals(station.getStationStandard())){
			params = pageParamManageDao.getDisplayParam();
		}else{
			params = pageParamManageDao.findAllDisplayParams(station.getId()+"");
		}
		StringBuilder avgSb = new StringBuilder(1024);	//字段字符串拼接
		StringBuilder maxSb = new StringBuilder(1024);	//字段字符串拼接
		StringBuilder minSb = new StringBuilder(1024);	//字段字符串拼接
		Map<String, Object> map = new HashMap<String, Object>();	//sql查询参数
		List<WtParamDataDto> datas = new ArrayList<WtParamDataDto>();	//返回结果集
		if(null != params&& params.size()>0){
			for(String param :params){
				//TODO 循环遍历该参数是否要查询
				if((null!=paramMap.get(param) && "on".equals(((String[])paramMap.get(param))[0])) || queryDto.isFirstLoad()){
					avgSb.append("ifnull(round(AVG(").append(param).append("),4),'-') as ").append(param).append(",");
					maxSb.append("ifnull(round(MAX(").append(param).append("),4),'-') as ").append(param).append(",");
					minSb.append("ifnull(round(MIN(").append(param).append("),4),'-') as ").append(param).append(",");
				}
			}
			//将字段值转化为字段列 p1,p2,p3,p4....
			String avgParamColumn = avgSb.toString().substring(0, avgSb.toString().length()-1);
			String maxParamColumn = maxSb.toString().substring(0, maxSb.toString().length()-1);
			String minParamColumn = minSb.toString().substring(0, minSb.toString().length()-1);
			map.put("paramColumn", avgParamColumn);
			map.put("subIndex", 1);
			map.put("subUnit", "day");
			map.put("format", "%Y-%m-%d %H");
			map.put("searchDate", queryDto.getDate());
			if(StringUtils.isBlank(queryDto.getType())){
				map.put("mcu","25769738752");
			}else{
				map.put("mcu",queryDto.getType());
			}
			//TODO 根据列名字查询每个时段的值
			map.put("dataType", 0);
			datas = pageParamManageDao.getReportDataList(map);
			result.put("datas", datas);
			result = this.getMaxMinAvgData(datas,result,station);
			//获取最小值,最大值,平均值
			if(null!=datas && datas.size()>0){
				List<WtParamDataDto> dtos = (List<WtParamDataDto>)result.get("maxDatas");
				WtParamDataDto data = dtos.get(0);
				WtParamDataDto heiChouData = this.getHeiChou(data, station);
				WtParamDataDto diBiaoData = this.getDiBiao(data, station);
				List<WtParamDataDto> dtos1 = (List<WtParamDataDto>)result.get("minDatas");
				WtParamDataDto data1 = dtos1.get(0);
				WtParamDataDto heiChouData1 = this.getHeiChou(data1, station);
				WtParamDataDto diBiaoData1 = this.getDiBiao(data1, station);

				String pollutant = "";
				Map<String,String> pollutantMap=new HashMap<String,String>();//记录最差级别的评级
				Map<String,String> pollutantMap1=new HashMap<String,String>();//记录最差级别的评级
				if(null != heiChouData){
					heiChouData.setDataType("4");
					datas.add(heiChouData);
					result.put("heichouDatas", datas);
					datas = new ArrayList<WtParamDataDto>();
					String heichou = getHeichouLevel(heiChouData,pollutant);
					result.put("heichou", heichou);
				}
				WtParamDataDto mergeDibiao=null;
				if(null != diBiaoData){
					datas = new ArrayList<WtParamDataDto>();
					String dibiao = getDibiaoLevel(diBiaoData,pollutantMap);
					String dibiao1 = getDibiaoLevel(diBiaoData1,pollutantMap1);
					dibiao=compareLevel(dibiao, dibiao1);
					
					//根据最大值和最小值的比较得出一个最差评价然后赋值
					mergeDibiao=compareLevel(diBiaoData,diBiaoData1);
					mergeDibiao.setDataType("5");
					datas.add(mergeDibiao);
					result.put("dibiaoDatas", datas);
					result.put("dibiao", dibiao);
					pollutant+=mergeDibiao.getPollutant();
				}
				if(StringUtils.isNoneBlank(pollutant)){
					result.put("pollutant", getParamNameByParam(station,pollutant,mergeDibiao));
				}
			}
			map.put("paramColumn", avgParamColumn);
			WtParamDataDto data = pageParamManageDao.getReportData(map);
			if(null!=data){
				data.setDataType("3");
			}
			datas = new ArrayList<WtParamDataDto>();
			datas.add(data);
			result.put("avgDatas", datas);
		}
		return result;
	}

	private Map<String,Object> getMaxMinAvgData(List<WtParamDataDto> datas, Map<String, Object> result, WtStation station) {
		List<WtParamDataDto> maxDatas = new ArrayList<>();
		List<WtParamDataDto> minDatas = new ArrayList<>();
		try {
			if (null != datas && datas.size() > 0) {
				WtParamDataDto maxData = new WtParamDataDto();
				WtParamDataDto minData = new WtParamDataDto();
				for (int n = 1; n < 33; n++) {
					List<Double> list = new ArrayList<Double>();
					String param = "p"+n;
					String tarParam = "tarP"+n;
					Map<String, Object> paramMap = new HashMap<String,Object>();
					for (WtParamDataDto data : datas) {
						PropertyUtilsBean propertyUtilsBeanSource = new PropertyUtilsBean();
						PropertyDescriptor[] descriptorsSource = propertyUtilsBeanSource.getPropertyDescriptors(data);
						for (int i = 0; i < descriptorsSource.length; i++) {
							String name = descriptorsSource[i].getName();
							if (!"class".equals(name)) {
								paramMap.put(name, propertyUtilsBeanSource.getNestedProperty(data, name));
							}
						}
						String valStr = (String)paramMap.get(param);
						if(StringUtils.isNotBlank(valStr)&&!"-".equals(valStr)){
							Double value = Double.parseDouble(valStr);
							list.add(value);
						}
					}
					if (null != list && list.size() > 0) {
						double max = Collections.max(list);
						double min = Collections.min(list);
						PropertyUtilsBean propertyUtilsBeanTarget = new PropertyUtilsBean();
						propertyUtilsBeanTarget.setNestedProperty(maxData, param,max+"");
						propertyUtilsBeanTarget.setNestedProperty(minData, param,min+"");
						if("2".equals(station.getStationStandard())){
							String str = pageParamManageDao.getStationParamAdjustInfo(station.getId()+"",param);
							propertyUtilsBeanTarget.setNestedProperty(maxData, tarParam,str);
							propertyUtilsBeanTarget.setNestedProperty(minData, tarParam,str);
						}
					}
				}
				maxData.setDataType("2");
				minData.setDataType("1");
				maxDatas.add(maxData);
				minDatas.add(minData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("maxDatas",maxDatas);
		result.put("minDatas",minDatas);

		return result;
	}


	/**
	 * 获取地表水质
	 * @param data
	 * @return
	 */
	private WtParamDataDto getDiBiao(WtParamDataDto data, WtStation station) {
		WtParamDataDto paramData = new WtParamDataDto();
		Map<String, String> map = new HashMap<String,String>();
		Map<String, Object> paramMap = new HashMap<String,Object>();
		String table;
		if("2".equals(station.getStationStandard())){		//个性化配置站点
			table = "(SELECT * from wt_station_monitor where station_id='"+station.getId()+"')";
			map.put("joinParam","param_adjust");
		}else{
			table = "wt_param";
			map.put("joinParam","param");
		}
		map.put("table",table);
		map.put("typeCode", "2");
		map.put("poColumn","dibiao_display");
		map.put("stationId", station.getId()+"");
		try { 
			PropertyUtilsBean propertyUtilsBeanSource = new PropertyUtilsBean(); 
			PropertyDescriptor[] descriptorsSource = propertyUtilsBeanSource.getPropertyDescriptors(data);
			for (int i = 0; i < descriptorsSource.length; i++) { 
				String name = descriptorsSource[i].getName(); 
				if (!"class".equals(name)) { 
					paramMap.put(name, propertyUtilsBeanSource.getNestedProperty(data, name)); 
				} 
			} 
			PropertyUtilsBean propertyUtilsBeanTarget = new PropertyUtilsBean(); 
			PropertyDescriptor[] descriptorsTarget = propertyUtilsBeanTarget.getPropertyDescriptors(paramData);
			for(int i = 1;i<33;i++){
				map.put("param", "p"+i);
				String value = (String)propertyUtilsBeanSource.getNestedProperty(data, "p"+i);
				String tarParam = (String)propertyUtilsBeanSource.getNestedProperty(data, "tarP"+i);
				map.put("paramValue", value);
				map.put("tarParam","2".equals(station.getStationStandard())?tarParam:"p"+i);
				if(null != value && !"-".equals(value)){
					String result;
//					if("1".equals(station.getStationStandard())){
					result = pageParamManageDao.getDiBiao(map);
//					}else{
//						result = pageParamManageDao.getDiBiaoGexing(map);
//					}
					propertyUtilsBeanTarget.setNestedProperty(paramData, "p"+i,result);
				}else{
					String result;
//					if("1".equals(station.getStationStandard())){
					result = pageParamManageDao.getDiBiaoNull(map);
//					}else{
//						result = pageParamManageDao.getDiBiaoNullGexing(map);
//					}
					propertyUtilsBeanTarget.setNestedProperty(paramData, "p"+i,result);
				}
			}
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
		return paramData;
	}

	/**
	 * 获取黑臭三分
	 * @param data
	 * @return
	 */
	private WtParamDataDto getHeiChou(WtParamDataDto data, WtStation station) {
		WtParamDataDto paramData = new WtParamDataDto();
		Map<String, String> map = new HashMap<String,String>();
		Map<String, Object> paramMap = new HashMap<String,Object>();
		String table;
		if("2".equals(station.getStationStandard())){
			table = "(SELECT * from wt_station_monitor where station_id='"+station.getId()+"')";
			map.put("joinParam","param_adjust");
		}else{
			table = "wt_param";
			map.put("joinParam","param");
		}
		map.put("table",table);
		map.put("typeCode", "1");
		map.put("poColumn","heichou_display");
		map.put("stationId", station.getId()+"");
		try { 
			PropertyUtilsBean propertyUtilsBeanSource = new PropertyUtilsBean(); 
			PropertyDescriptor[] descriptorsSource = propertyUtilsBeanSource.getPropertyDescriptors(data);
			for (int i = 0; i < descriptorsSource.length; i++) { 
				String name = descriptorsSource[i].getName(); 
				if (!"class".equals(name)) { 
					paramMap.put(name, propertyUtilsBeanSource.getNestedProperty(data, name)); 
				} 
			} 
			PropertyUtilsBean propertyUtilsBeanTarget = new PropertyUtilsBean(); 
			for(int i = 1;i<33;i++){
				map.put("param", "p"+i);
				String value = (String)propertyUtilsBeanSource.getNestedProperty(data, "p"+i);
				String tarParam = (String)propertyUtilsBeanSource.getNestedProperty(data, "tarP"+i);
				map.put("paramValue", value);
				map.put("tarParam","2".equals(station.getStationStandard())?tarParam:"p"+i);
				if(null != value && !"-".equals(value)){
					String result;
//					if("1".equals(station.getStationStandard())){
						result = pageParamManageDao.getDiBiao(map);
//					}else{
//						result = pageParamManageDao.getDiBiaoGexing(map);
//					}
					propertyUtilsBeanTarget.setNestedProperty(paramData, "p"+i,result);
				}else{
					String result;
//					if("1".equals(station.getStationStandard())){
						result = pageParamManageDao.getDiBiaoNull(map);
//					}else{
//						result = pageParamManageDao.getDiBiaoNullGexing(map);
//					}
					propertyUtilsBeanTarget.setNestedProperty(paramData, "p"+i,result);
				}
			}
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
		return paramData;
	}
	
	//合并两者对象并将最差数据展示出来
	private WtParamDataDto compareLevel(WtParamDataDto level1,WtParamDataDto level2) {
		PropertyUtilsBean propertyUtilsBeanTarget = new PropertyUtilsBean(); 
		PropertyUtilsBean propertyUtilsBeanSource = new PropertyUtilsBean(); 
		WtParamDataDto result=new WtParamDataDto();
		String pollutant="";
		for(int i = 1;i<33;i++){
			try {
				String value1 = (String)propertyUtilsBeanSource.getNestedProperty(level1, "p"+i);
				String value2 = (String)propertyUtilsBeanSource.getNestedProperty(level2, "p"+i);
				String temp=compareLevel(value1,value2);
				propertyUtilsBeanTarget.setNestedProperty(result, "p"+i,temp);
				if(temp!=null&&!"-".equals(temp)&&!"".equals(temp)) {//当temp不为-或者“”时将数据传入pollutant
					pollutant += ("p"+i+",");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		result.setPollutant(pollutant);
		return result;
	}
	
	private String compareLevel(String level1,String level2) {
		if("Ⅰ类".equals(level1)&&"Ⅱ类".equals(level2)) {
			return level2;
		}else if("Ⅰ类".equals(level1)&&"Ⅲ类".equals(level2)) {
			return level2;
		}else if("Ⅰ类".equals(level1)&&"Ⅳ类".equals(level2)) {
			return level2;
		}else if("Ⅰ类".equals(level1)&&"Ⅴ类".equals(level2)) {
			return level2;
		}else if("Ⅰ类".equals(level1)&&"劣Ⅴ类".equals(level2)) {
			return level2;
		}else if("Ⅱ类".equals(level1)&&"Ⅲ类".equals(level2)) {
			return level2;
		}else if("Ⅱ类".equals(level1)&&"Ⅳ类".equals(level2)) {
			return level2;
		}else if("Ⅱ类".equals(level1)&&"Ⅴ类".equals(level2)) {
			return level2;
		}else if("Ⅱ类".equals(level1)&&"劣Ⅴ类".equals(level2)) {
			return level2;
		}else if("Ⅲ类".equals(level1)&&"Ⅳ类".equals(level2)) {
			return level2;
		}else if("Ⅲ类".equals(level1)&&"Ⅴ类".equals(level2)) {
			return level2;
		}else if("Ⅲ类".equals(level1)&&"劣Ⅴ类".equals(level2)) {
			return level2;
		}else if("Ⅳ类".equals(level1)&&"Ⅴ类".equals(level2)) {
			return level2;
		}else if("Ⅳ类".equals(level1)&&"劣Ⅴ类".equals(level2)) {
			return level2;
		}else if("Ⅴ类".equals(level1)&&"劣Ⅴ类".equals(level2)) {
			return level2;
		}else {
			return level1;
		}
	}
	
	/**
	 * @param diBiaoData
	 * @param pollutant	标示最差一类是P几
	 * @return
	 */
	private String getDibiaoLevel(WtParamDataDto diBiaoData,Map<String,String> pollutantMap) {
		String level1 = "Ⅰ类";
		String level2 = "Ⅱ类";
		String level3 = "Ⅲ类";
		String level4 = "Ⅳ类";
		String level5 = "Ⅴ类";
		String level6 = "劣Ⅴ类";
		String result = "";
		String pollutant="";
		try{
			PropertyUtilsBean propertyUtilsBeanSource = new PropertyUtilsBean(); 
			//判断是否有值为劣Ⅴ类 如果有直接返回
			for (int i = 1; i < 33; i++) { 
				String value = (String) propertyUtilsBeanSource.getNestedProperty(diBiaoData, "p"+i); 
				if(null!=value && level6.equals(value)){
					pollutant += ("p"+i+",");
					result = level6;
					if(pollutantMap.containsKey(result)) {
						pollutant +=pollutantMap.get(result);
					}
					pollutantMap.put(result, pollutant);
				}
			}
			if(StringUtils.isNotBlank(result)){
				return result;
			}
			//判断是否有值为Ⅴ类 如果有直接返回
			for (int i = 1; i < 33; i++) { 
				String value = (String) propertyUtilsBeanSource.getNestedProperty(diBiaoData, "p"+i); 
				if(null!=value && level5.equals(value)){
					pollutant += ("p"+i+",");
					result = level5;
					if(pollutantMap.containsKey(result)) {
						pollutant +=pollutantMap.get(result);
					}
					pollutantMap.put(result, pollutant);
				}
			}
			if(StringUtils.isNotBlank(result)){
				return result;
			}
			//判断是否有值为Ⅳ类 如果有直接返回
			for (int i = 1; i < 33; i++) { 
				String value = (String) propertyUtilsBeanSource.getNestedProperty(diBiaoData, "p"+i); 
				if(null!=value && level4.equals(value)){
					pollutant += ("p"+i+",");
					result = level4;
					if(pollutantMap.containsKey(result)) {
						pollutant +=pollutantMap.get(result);
					}
					pollutantMap.put(result, pollutant);
				}
			}
			if(StringUtils.isNotBlank(result)){
				return result;
			}
			//判断是否有值为Ⅲ类 如果有直接返回
			for (int i = 1; i < 33; i++) { 
				String value = (String) propertyUtilsBeanSource.getNestedProperty(diBiaoData, "p"+i); 
				if(null!=value && level3.equals(value)){
					pollutant += ("p"+i+",");
					result = level3;
					if(pollutantMap.containsKey(result)) {
						pollutant +=pollutantMap.get(result);
					}
					pollutantMap.put(result, pollutant);
				}
			}
			if(StringUtils.isNotBlank(result)){
				return result;
			}
			//判断是否有值为Ⅱ类 如果有直接返回
			for (int i = 1; i < 33; i++) { 
				String value = (String) propertyUtilsBeanSource.getNestedProperty(diBiaoData, "p"+i); 
				if(null!=value && level2.equals(value)){
					pollutant += ("p"+i+",");
					result = level2;
					if(pollutantMap.containsKey(result)) {
						pollutant +=pollutantMap.get(result);
					}
					pollutantMap.put(result, pollutant);
				}
			}
			if(StringUtils.isNotBlank(result)){
				return result;
			}
			//判断是否有值为Ⅰ类 如果有直接返回
			for (int i = 1; i < 33; i++) { 
				String value = (String) propertyUtilsBeanSource.getNestedProperty(diBiaoData, "p"+i); 
				if(null!=value && level1.equals(value)){
					pollutant += ("p"+i+",");
					result = level1;
					if(pollutantMap.containsKey(result)) {
						pollutant +=pollutantMap.get(result);
					}
					pollutantMap.put(result, pollutant);
				}
			}
			if(StringUtils.isNotBlank(result)){
				return result;
			}
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
		return StringUtils.EMPTY;
	}

	/**
	 * @param heiChouData
	 * @return
	 */
	private String getHeichouLevel(WtParamDataDto heiChouData,String pollutant) {
		String level1 = "合格";
		String level2 = "轻度黑臭";
		String level3 = "重度黑臭";
		String result = "";
		try{
			PropertyUtilsBean propertyUtilsBeanSource = new PropertyUtilsBean(); 
			//判断是否有值为重度黑臭 如果有直接返回
			for (int i = 1; i < 33; i++) { 
				String value = (String) propertyUtilsBeanSource.getNestedProperty(heiChouData, "p"+i); 
				if(null!=value && level3.equals(value)){
					pollutant += ("p"+i+",");
					result = level3;
				}
			}
			if(StringUtils.isNotBlank(result)){
				return result;
			}
			//判断是否有值为轻度黑臭 如果有直接返回
			for (int i = 1; i < 33; i++) { 
				String value = (String) propertyUtilsBeanSource.getNestedProperty(heiChouData, "p"+i); 
				if(null!=value && level2.equals(value)){
					pollutant += ("p"+i+",");
					result = level2;
				}
			}
			if(StringUtils.isNotBlank(result)){
				return result;
			}
			//判断是否有值为合格 如果有直接返回
			for (int i = 1; i < 33; i++) { 
				String value = (String) propertyUtilsBeanSource.getNestedProperty(heiChouData, "p"+i); 
				if(null!=value && level2.equals(value)){
					pollutant += ("p"+i+",");
					result = level1;
				}
			}
			if(StringUtils.isNotBlank(result)){
				return result;
			}
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
		return StringUtils.EMPTY;
	}

	public String getParamNameByParam(WtStation station,String pollutant,WtParamDataDto mergeDibiao){
		if(StringUtils.isNotBlank(pollutant)){
			String[] params = pollutant.substring(0,pollutant.length()).split(",");
			List<WtParam> paramList = new ArrayList<>();
			String displayColumn = "";
			String typeCode = "";
			String level = "";
			if("1".equals(station.getStationJudgeType())){	//黑臭
				displayColumn = "heichou_display";
				level="heichou";//以黑臭的评判level为标准
				typeCode="1";
			}else{	//地表
				displayColumn = "dibiao_display";
				level="dibiao";//以地表的评判level为标准
				typeCode="2";
			}
			if("1".equals(station.getStationStandard())){	//标准站点
				paramList = pageParamManageDao.findParamsByParam(Arrays.asList(params),displayColumn);
			}else{	//其余情况为个性站点
				paramList = pageParamManageDao.findALiasParamsByParam(Arrays.asList(params),station.getId()+"", displayColumn);
			}
			StringBuilder sb = new StringBuilder(1024);
			if(null != paramList && paramList.size()>0){
				boolean flag = false;
				PropertyUtilsBean propertyUtilsBeanTarget = new PropertyUtilsBean(); 
				for(WtParam param :paramList){
					if(flag){
						sb.append(",");
					}
					WtWaterLevel waterLevel;
					if("1".equals(station.getStationJudgeType())){	//黑臭
						//获得黑臭的评判标准
						waterLevel=pageParamManageDao.getWtWaterLevel(param.getParam(), typeCode, param.getHeichou());
						
					}else {
						//获得地表的评判标准
						waterLevel=pageParamManageDao.getWtWaterLevel(param.getParam(), typeCode, param.getDibiao());
					}
					String levelTemp=waterLevel.getLevel();//获得评判等级
					//将获得的评判等级跟传入的评判等级进行比较，如果不同则添加
					try {
						//当前等级
						String currentLevel = (String)propertyUtilsBeanTarget.getNestedProperty(mergeDibiao, param.getParam());
						if(!levelTemp.equals(currentLevel)) {//不符合评判等级的不添加
							sb.append(param.getParamName());
							flag = true;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				return sb.toString();
			}else{
				return "-";
			}
		}else{
			return "-";
		}
	}

	public WtParam getWtParamById(String id) {
		return pageParamManageDao.findParamById(Integer.parseInt(id));
	}

	public void saveWtParam(WtParam wtParam) {
		pageParamManageDao.update(wtParam);
	}
}
