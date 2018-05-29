/**
 * 
 */
package com.welltech.controller.sysSetting;

import java.util.List;
import java.util.Map;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welltech.common.util.ErrorCodeEnum;
import com.welltech.common.util.ReturnEntity;
import com.welltech.dto.WtCompanyDto;
import com.welltech.entity.WtCompany;
import com.welltech.service.sysSetting.AreaManageService;

/**
 * Created by Zhujia at 2017年7月28日 下午4:38:45
 */
@Controller
@RequestMapping("/syssetting/am")
public class AreaManageController {
	
	@Autowired
	private AreaManageService areaManageService;
	
	/**
	 * 区域管理
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "page" })
    public String areaManage(Map<String,Object> map) {
		List<WtCompanyDto> company = areaManageService.findAllCompany();
		map.put("companys",company); 
        return "sysSetting/areaManage";
    }
	
	/**
	 * 新增区域
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "addArea" })
	@ResponseBody
    public ReturnEntity<String> addArea(Map<String,Object> map, HttpServletRequest request, WtCompany company) {
		ReturnEntity<String> re = new ReturnEntity<>();
		String coordinate = request.getParameter("coordinate");
		String[] coordinates = coordinate.replace("，", ",").split(",");
		if(coordinates.length!=2){
			re.setReturnCode(ErrorCodeEnum.ERROR_05.getCode());
		}else if(null!=company.getId()){
			company.setLatitude(coordinates[1].trim());
			company.setLongitude(coordinates[0].trim());
			areaManageService.updateArea(company);
		}else{
			company.setLatitude(coordinates[1].trim());
			company.setLongitude(coordinates[0].trim());
			areaManageService.addArea(company);
		}
		return re;
    }
	
	/**
	 * 编辑区域
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "editArea" })
	@ResponseBody
    public ReturnEntity<String> editUser(Map<String,Object> map) {
		ReturnEntity<String> re = new ReturnEntity<>();
		return re;
    }
	
	/**
	 * 查找区域
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "getAreaById" })
	@ResponseBody
    public ReturnEntity<WtCompany> getAreaById(Map<String,Object> map,String id) {
		ReturnEntity<WtCompany> re = new ReturnEntity<>();
		WtCompany company = areaManageService.findCompanyById(id);
		if(null==company){
			re.setReturnCode(ErrorCodeEnum.ERROR_02.getCode());
			return re;
		}
		re.setReturnData(company);
		return re;
    }
	
	/**
	 * 删除区域
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "deleteArea" })
	@ResponseBody
    public ReturnEntity<String> deleteArea(Map<String,Object> map,String id) {
		ReturnEntity<String> re = new ReturnEntity<>();
		WtCompany company = areaManageService.findCompanyById(id);
		if(null==company){
			re.setReturnCode(ErrorCodeEnum.ERROR_02.getCode());
			return re;
		}
		//TODO 无法删除当前登录状态用户
		areaManageService.deleteCompanyById(id);
		return re;
    }

}
