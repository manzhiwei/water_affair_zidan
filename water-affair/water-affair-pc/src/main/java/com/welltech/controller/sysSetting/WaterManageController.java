/**
 * 
 */
package com.welltech.controller.sysSetting;

import java.util.List;
import java.util.Map;

import com.welltech.dto.WaterLevelList;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welltech.common.util.ReturnEntity;
import com.welltech.dto.WtWaterDto;
import com.welltech.entity.WtWaterLevel;
import com.welltech.service.sysSetting.WaterManageService;

/**
 * 水质类型设置
 * Created by Zhujia at 2017年7月28日 下午4:38:45
 */
@Controller
@RequestMapping("/syssetting/wtm")
public class WaterManageController {
	
	@Autowired
	private WaterManageService waterParamManageService;
	
	/**
	 * 区域管理
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "page" })
    public String waterManage(Map<String,Object> map) {
		List<WtWaterDto> waterDto = waterParamManageService.findAllWaterParamDto();
		map.put("waters",waterDto); 
        return "sysSetting/waterTypeManage";
    }
	
	/**
	 * 获取参数指标
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "getParamInfo" })
	@ResponseBody
    public ReturnEntity<List<WtWaterLevel>> getParamInfo(Map<String,Object> map, HttpServletRequest request, String param) {
		ReturnEntity<List<WtWaterLevel>> re = new ReturnEntity<>();
		List<WtWaterLevel> wtWaterLevel = waterParamManageService.getWtWaterLevelByParam(param);
		re.setReturnData(wtWaterLevel);
		return re;
    }

    /**
	 * 修改指标
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "saveWaterLevel" })
	@ResponseBody
    public ReturnEntity<String> saveWaterLevel(Map<String,Object> map, HttpServletRequest request, WaterLevelList water) {
		ReturnEntity<String> re = new ReturnEntity<>();
		waterParamManageService.saveWaterLevel(water);
		re.setReturnData("");
		return re;
    }
	
}
