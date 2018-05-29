/**
 * 
 */
package com.welltech.controller.statistics;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welltech.common.util.ReturnEntity;
import com.welltech.dto.WtAreaStationDto;
import com.welltech.dto.WtParamDataDto;
import com.welltech.dto.WtParamQueryDto;
import com.welltech.entity.WtParam;
import com.welltech.entity.WtStation;
import com.welltech.service.statistics.CommonReportService;
import com.welltech.service.sysSetting.PageParamManageService;
import com.welltech.service.sysSetting.PortManageService;

/**
 * Created by Zhujia at 2017年8月14日 下午8:52:22
 */
@Controller
public class CommonReportController {
	
	@Autowired
	CommonReportService commonReportService;
	
	@Autowired
	PageParamManageService pageParamManageService;
	
	@Autowired
	private PortManageService portManageService;

	@RequestMapping(value = { "/commonReport" })
	public String commonReport(HttpServletRequest request,Map<String,Object> map,WtParamQueryDto queryDto) {
		if(StringUtils.isBlank(queryDto.getPointId())){
			queryDto.setPointId("3");
			queryDto.setReportType("0");
			queryDto.setPointName("紫星路");
			Date now = new Date();
			queryDto.setStartTime(new Date(now.getTime()-24*60*60*1000));	//一天前
			queryDto.setEndTime(now);//当前时间
			queryDto.setDate(now);
			queryDto.setMonth(now);
			queryDto.setYear(now);
		}
		Map paramMap = request.getParameterMap();
		List<WtParam> params = pageParamManageService.findAllDisplayParams(paramMap,queryDto.getPointId());
		WtStation station = portManageService.findStationById(queryDto.getPointId());
		if(null!=station.getGatewaySerial()){
			queryDto.setType(station.getGatewaySerial());
		}
		Map<String, Object> resultMap = pageParamManageService.findAllDisplayData(station,queryDto,paramMap);
		List<WtParamDataDto> datas = (List<WtParamDataDto>) resultMap.get("datas");
		List<WtParamDataDto> minDatas = (List<WtParamDataDto>) resultMap.get("minDatas");
		List<WtParamDataDto> maxDatas = (List<WtParamDataDto>) resultMap.get("maxDatas");
		List<WtParamDataDto> avgDatas = (List<WtParamDataDto>) resultMap.get("avgDatas");
		List<WtParamDataDto> heichouDatas = (List<WtParamDataDto>) resultMap.get("heichouDatas");
		List<WtParamDataDto> dibiaoDatas = (List<WtParamDataDto>) resultMap.get("dibiaoDatas");
		String heichou = (String) resultMap.get("heichou");
		String dibiao = (String) resultMap.get("dibiao");
		String pollutant = (String) resultMap.get("pollutant");
		map.put("columns", params);
		map.put("datas", datas);
		map.put("minDatas", minDatas);
		map.put("maxDatas", maxDatas);
		map.put("avgDatas", avgDatas);
		map.put("heichouDatas", heichouDatas);
		map.put("dibiaoDatas", dibiaoDatas);
		map.put("queryDto", queryDto);
		map.put("pointId",queryDto.getPointId());
		map.put("pointName",queryDto.getPointName());
		map.put("heichou", heichou);
		map.put("dibiao", dibiao);
		map.put("pollutant", pollutant);
		map.put("station", station);
		return "statistics/commonReport";
	}

	@RequestMapping(value = { "/getAreaStation"})
	@ResponseBody
	public ReturnEntity<List<WtAreaStationDto>> getAreaStationNode(){
		ReturnEntity<List<WtAreaStationDto>> re = new ReturnEntity<List<WtAreaStationDto>>();
		List<WtAreaStationDto> dto = commonReportService.getAreaStationNode();
		re.setReturnData(dto);
		return re;
	}

	@RequestMapping(value = { "/getAreaTree"})
	@ResponseBody
	public ReturnEntity<List<WtAreaStationDto>> getAreaTree(){
		ReturnEntity<List<WtAreaStationDto>> re = new ReturnEntity<List<WtAreaStationDto>>();
		List<WtAreaStationDto> dto = commonReportService.getAreaNode();
		re.setReturnData(dto);
		return re;
	}

}
