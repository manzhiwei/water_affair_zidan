/**
 * 
 */
package com.welltech.controller.sysSetting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.welltech.common.util.OperateCodeEnum;
import com.welltech.service.history.OperateHistoryServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.thymeleaf.Template;
import org.thymeleaf.TemplateEngine;

import com.welltech.common.util.ErrorCodeEnum;
import com.welltech.common.util.ReturnEntity;
import com.welltech.common.util.ThymeleafProcessor;
import com.welltech.dto.WtCompanyDto;
import com.welltech.dto.WtStationBaseDto;
import com.welltech.dto.WtStationDto;
import com.welltech.dto.WtStationMonitorDto;
import com.welltech.dto.WtStationMonitorList;
import com.welltech.dto.WtUserDto;
import com.welltech.entity.WtCompany;
import com.welltech.entity.WtStation;
import com.welltech.entity.WtStationBase;
import com.welltech.entity.WtStationMonitor;
import com.welltech.service.sysSetting.AreaManageService;
import com.welltech.service.sysSetting.PortManageService;

/**
 * Created by Zhujia at 2017年7月29日 下午4:21:50
 */
@Controller
@RequestMapping("/syssetting/pm")
public class PortManageController {
	
	@Autowired
	PortManageService portManageService;
	
	@Autowired
	AreaManageService areaManageService;
	
	@Autowired
	ThymeleafProcessor thymeleafProcessor;
	
	@Autowired
	TemplateEngine engine;

	@Autowired
	OperateHistoryServiceImpl operateHistoryServiceImpl;
	
	/**
	 * 测点管理
	 * @param map
	 * @return
	 */
	@RequestMapping(value = { "/page" })
	public String portManage(Map<String,Object> map) {
		List<WtStationDto> station = portManageService.findAllStation();
		List<WtCompanyDto> company = areaManageService.findAllCompany();
		map.put("stations",station);
		map.put("companys",company);
		String judgeType = "1";
		if(station != null && station.size() > 0){
			judgeType = station.get(0).getStationJudgeType();
		}
		map.put("judgeType", judgeType);
		return "sysSetting/portManage";
	}
	

	/**
	 * 监测项管理
	 * @param map
	 * @param station
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = { "/mm" })
	public String monitorManage(Map<String,Object> map,WtStation station) throws Exception {
		map.put("stationId",station.getId()); 
		map.put("stationName",station.getPoint()); 
		List<WtStationMonitorDto> monitors = portManageService.findWtStationMonitorByStationId(station.getId());
		WtStationBaseDto baseDto = portManageService.findStationBaseDtoByStationId(station.getId().toString());
		//拿到黑臭还是地表分类标准
		map.put("stationJudgeType", baseDto.getStationJudgeType());
		//拿到标准还是个性
		map.put("stationStandard", baseDto.getStationStandard());
		map.put("monitors", monitors);
		return "sysSetting/monitorManage";
	}

	/**
	 * 修改监测项的值
	 * @param monitors
	 * @return
	 */
	@RequestMapping(value = { "updateValue" })
	@ResponseBody
	public ReturnEntity<String> updateValue(WtStationMonitorList monitors) {
		ReturnEntity<String> re = new ReturnEntity<>();
		portManageService.updateMonitorValue(monitors);
		return re;
	}
	
	/**
	 * 根据id获取测点
	 * @param id
	 * @return
	 */
	@RequestMapping(value = { "getPortById" })
	@ResponseBody
	public ReturnEntity<WtStationBaseDto> operatorManage(String id) {
		ReturnEntity<WtStationBaseDto> re = new ReturnEntity<>();
		WtStationBaseDto baseDto = portManageService.findStationBaseDtoByStationId(id);
		if(null==baseDto){
			re.setReturnCode(ErrorCodeEnum.ERROR_06.getCode());
			return re;
		}
		re.setReturnData(baseDto);
		return re;
	}
	
	
	/**
	 * 新增测点
	 * @param map
	 * @param request
	 * @param station
	 * @param base
	 * @return
	 */
	@RequestMapping(value = { "addPort" })
	@ResponseBody
    public ReturnEntity<String> addPort(Map<String,Object> map, HttpServletRequest request, WtStation station, WtStationBase base) {
		ReturnEntity<String> re = new ReturnEntity<>();
		String coordinate = request.getParameter("coordinate");
		String installCoordinate = request.getParameter("installCoordinate");
		String[] coordinates = coordinate.replace("，", ",").split(",");
		String[] installCoordinates = installCoordinate.replace("，", ",").split(",");
		WtStation wtStation = portManageService.findStationBySerial(station.getGatewaySerial());
		String stationId = request.getParameter("stationId");
		String stationBaseId = request.getParameter("stationBaseId");
		if(StringUtils.isNoneBlank(stationId)){
			station.setId(Integer.parseInt(stationId));
		}
		if(StringUtils.isNoneBlank(stationBaseId)){
			base.setId(Integer.parseInt(stationBaseId));
		}
		
		if(coordinates.length!=2 || installCoordinates.length!=2){
			re.setReturnCode(ErrorCodeEnum.ERROR_05.getCode());
		}else if(null!=wtStation && null == station.getId()){
			//该序列号已存在的插入操作
			re.setReturnCode(ErrorCodeEnum.ERROR_09.getCode());
		}else if(null != station.getId()){
			//更新操作
			station.setLatitude(coordinates[1]);
			station.setLongitude(coordinates[0]);
			station.setInstallLatitude(installCoordinates[1]);
			station.setInstallLongitude(installCoordinates[0]);
			portManageService.updateStation(station,base);

			com.welltech.security.entity.WtUser wtuser = (com.welltech.security.entity.WtUser) request.getSession().getAttribute("user");
			operateHistoryServiceImpl.addOperate(OperateCodeEnum.OPERATE_10, wtuser.getId(), wtStation.getPoint());
		}else{
			//插入操作
			station.setLatitude(coordinates[1]);
			station.setLongitude(coordinates[0]);
			station.setInstallLatitude(installCoordinates[1]);
			station.setInstallLongitude(installCoordinates[0]);
			portManageService.addStation(station,base);

			com.welltech.security.entity.WtUser wtuser = (com.welltech.security.entity.WtUser) request.getSession().getAttribute("user");
			operateHistoryServiceImpl.addOperate(OperateCodeEnum.OPERATE_09, wtuser.getId(), station.getPoint());
		}
		return re;
    }
	
	/**
	 * 删除测点
	 * @param map
	 * @param id
	 * @return
	 */
	@RequestMapping(value = { "deletePort" })
	@ResponseBody
    public ReturnEntity<String> deletePort(Map<String,Object> map,String id, HttpServletRequest request) {
		ReturnEntity<String> re = new ReturnEntity<>();
		WtStation station = portManageService.findStationById(id);
		if(null==station){
			re.setReturnCode(ErrorCodeEnum.ERROR_06.getCode());
			return re;
		}
		portManageService.deleteStationById(id);

		com.welltech.security.entity.WtUser wtuser = (com.welltech.security.entity.WtUser) request.getSession().getAttribute("user");
		operateHistoryServiceImpl.addOperate(OperateCodeEnum.OPERATE_11, wtuser.getId(), station.getPoint());
		return re;
    }

    @RequestMapping(value = {"setJudgeType"})
	public String setJudgeType(@RequestParam(name = "judgeType", defaultValue = "1") String judgeType) {
		portManageService.saveJudgeType(judgeType);
		return "redirect:page";
	}
}
