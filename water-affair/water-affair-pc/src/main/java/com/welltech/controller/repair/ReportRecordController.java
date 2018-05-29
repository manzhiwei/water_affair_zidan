/**
 * 
 */
package com.welltech.controller.repair;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welltech.common.util.ReturnEntity;
import com.welltech.dto.WtCompanyDto;
import com.welltech.dto.WtStationBreakdownDto;
import com.welltech.dto.WtStationDto;
import com.welltech.entity.WtStationBreakdown;
import com.welltech.entity.WtStationRepair;
import com.welltech.framework.aop.pagination.bean.MyPage;
import com.welltech.security.entity.WtUser;
import com.welltech.service.repair.RepairRecordServiceImpl;
import com.welltech.service.repair.ReportRecordServiceImpl;
import com.welltech.service.sysSetting.AreaManageService;
import com.welltech.service.sysSetting.PortManageService;

/**
 * Created by Zhujia at 2017年8月14日 下午5:45:32
 */
@Controller
@RequestMapping(value = {"reportRecord"})
public class ReportRecordController {

	@Autowired
	ReportRecordServiceImpl reportRecordServiceImpl;
	
	@Autowired
	RepairRecordServiceImpl repairRecordServiceImpl;
	
	@Autowired
	PortManageService portManageService;
	
	@Autowired
	AreaManageService areaManageService;
	
	@RequestMapping(value = { "page" })
	public String loginData(HttpServletRequest request, Model model, MyPage myPage, 
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime, String pointId,String pointName) {
		if (myPage == null) {
			myPage = new MyPage();
		}
		String[] stationIds=null;
		if(StringUtils.isNotBlank(pointId)){
			stationIds = pointId.split(",");
		}
		String username = ((WtUser)request.getSession().getAttribute("user")).getUsername();
		String cellphone = ((WtUser)request.getSession().getAttribute("user")).getCellphone();
		List<WtStationDto> stations = portManageService.findAllStation();
		List<WtCompanyDto> companys = areaManageService.findAllCompany();
		model.addAttribute("datas", reportRecordServiceImpl.listBreakdownPage(myPage , beginTime, endTime, stationIds));
		model.addAttribute("repairDatas", repairRecordServiceImpl.listRepair(beginTime, endTime, stationIds));
		model.addAttribute("pointId",pointId);
		model.addAttribute("pointName",pointName);
		model.addAttribute("stations",stations);
		model.addAttribute("companys",companys);
		model.addAttribute("myPage", myPage);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("username", username);
		model.addAttribute("cellphone", cellphone);
		return "repair/reportRecord";
	}
	
	/**
	 * 删除故障
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"deleteBreakdown"})
	@ResponseBody
	public ReturnEntity<String> deleteBreakdown(HttpServletRequest request){
		String id = request.getParameter("id");
		ReturnEntity<String> re = new ReturnEntity<String>();
		reportRecordServiceImpl.deleteBreakdown(id);
		return re;
	}
	
	/**
	 * 新建故障信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"saveBreakdown"})
	@ResponseBody
	public ReturnEntity<String> saveBreakdown(HttpServletRequest request,WtStationBreakdown breakdown){
		ReturnEntity<String> re = new ReturnEntity<String>();
		reportRecordServiceImpl.saveBreakdown(breakdown);
		return re;
	}
	
	/**
	 * 新建维修信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"saveRepair"})
	@ResponseBody
	public ReturnEntity<String> saveRepair(HttpServletRequest request,WtStationRepair repair){
		ReturnEntity<String> re = new ReturnEntity<String>();
		reportRecordServiceImpl.saveRepair(repair);
		return re;
	}
	
	/**
	 * 根据故障ID获取故障信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"getBreakdown"})
	@ResponseBody
	public ReturnEntity<WtStationBreakdownDto> getBreakdown(HttpServletRequest request){
		String id = request.getParameter("id");
		ReturnEntity<WtStationBreakdownDto> re = new ReturnEntity<WtStationBreakdownDto>();
		WtStationBreakdownDto dto = reportRecordServiceImpl.getBreakdownDto(id);
		dto.setCreateUser(((WtUser)request.getSession().getAttribute("user")).getUsername());
		dto.setCreateUserPhone(((WtUser)request.getSession().getAttribute("user")).getCellphone());
		dto.setCreateTime(new Date());
		re.setReturnData(dto);
		return re;
	}

}
