package com.welltech.controller.sysSetting;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.welltech.dto.WtDeviceMessageDto;
import com.welltech.framework.aop.pagination.bean.MyPage;
import com.welltech.framework.aop.pagination.bean.Page;
import com.welltech.framework.aop.pagination.bean.Pagination;
import com.welltech.security.entity.WtUser;
import com.welltech.service.device.DeviceService;
import com.welltech.service.sysSetting.DeviceManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/device")
public class DeviceManageController {
	
	@Autowired
	private DeviceManageService deviceManageService;

	@Autowired
	private DeviceService deviceService;

	/**
	 * 测点管理页
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("paramConfigs", deviceManageService.findCanWriteConfigByType("2"));
		model.addAttribute("formatConfigs", deviceManageService.findCanWriteConfigByType("3"));
		return "sysSetting/deviceManage";
	}

	@RequestMapping("data")
	@ResponseBody
	public Map<String, Object> data(HttpServletRequest request){
		Map<String, Object> map = this.handleDT(request);
		map.put("data", deviceManageService.listWtDeviceDtos(map));
		Page page = (Page) map.get("page");
		map.put("recordsTotal", page.getTotalRecord());
		map.put("recordsFiltered", page.getTotalRecord());
		return map;
	}

	private Map<String,Object> handleDT(HttpServletRequest request) {
		Map<String, Object> map = new LinkedHashMap<>();
		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		Page page = new Pagination();
		page.setCurrentPage(start/length + 1);
		page.setDefalutPageRows(length);
		map.put("page", page);

		map.put("draw", Integer.parseInt(request.getParameter("draw")));
		map.put("start", start);
		map.put("length", length);

		String orderColumn = request.getParameter("order[0][column]");
		String order = "mcuId";
		if("0".equals(orderColumn) || "8".equals(orderColumn)){
			;
		} else if("1".equals(orderColumn)){
			order = "stationName";
		} else if("4".equals(orderColumn)){
			order = "cardNo";
		} else if("5".equals(orderColumn)){
			order = "projectCode";
		} else if("6".equals(orderColumn)){
			order = "connectTime";
		} else if("7".equals(orderColumn)){
			order = "controllerAddress";
		}
		String sort = request.getParameter("order[0][dir]");
		map.put("order", order);
		map.put("sort", sort);
		return map;
	}

	@RequestMapping("/modbus")
	public String modbus(Model model, String mcuId) {
		model.addAttribute("datas", deviceManageService.listWtDeviceModbusDtos(mcuId));
		return "sysSetting/deviceModbusManage";
	}

	@RequestMapping("/param")
	public String param(Model model, String mcuId) {
		model.addAttribute("datas", deviceManageService.listWtDeviceDataDtos(mcuId));
		return "sysSetting/deviceParamManage";
	}

	@RequestMapping(value="/message",method = RequestMethod.GET)
	public String message(Model model, String mcuId){
		model.addAttribute("mcuId", mcuId);
		return "sysSetting/deviceMessageManage";
	}

	@RequestMapping(value="/message", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> message(String mcuId, Integer draw, Integer start, Integer length){
		Page page = new Pagination();
		page.setDefalutPageRows(length);
		page.setCurrentPage(start / length + 1);
		List<WtDeviceMessageDto> data = deviceManageService.listWtDeviceMessageDtos(page, mcuId);

		Map<String, Object> result = new HashMap<>();
		result.put("draw", draw);
		result.put("recordsTotal", page.getTotalRecord());
		result.put("recordsFiltered", page.getTotalRecord());
		result.put("data", data);
		return result;
	}

	@RequestMapping("/paramCode")
	@ResponseBody
	public Map<String, Object> paramCode(String type){
		Map<String, Object> map = new HashMap<>();
		map.put("data", deviceManageService.findCanWriteConfigByType(type));
		return map;
	}

	@RequestMapping("/sendOrder")
    @ResponseBody
    public Map<String, Object> sendOrder(HttpServletRequest request, @RequestParam Map<String, Object> map, String[] mcuIds){
        map.remove("mcuIds");
        WtUser user = (WtUser) request.getSession().getAttribute("user");
        for(String mcuId : mcuIds){
            deviceService.applySetConfig(map, mcuId, user.getUsername());
        }
        return map;
    }

	@RequestMapping("/readParam")
	@ResponseBody
	public Map<String, Object> readParam(HttpServletRequest request, @RequestParam String jobCode, String type, String code, String[] mcuIds){
		Map<String, Object> map = new HashMap<>();
		WtUser user = (WtUser) request.getSession().getAttribute("user");
		JSONObject data = new JSONObject();
		if("2".equals(type)){
            data.put("paramCode", code);
        } else{
            int length = deviceManageService.countConfigByType(type);
            data.put("startCode", 1);
            data.put("length", length);
        }
		for(String mcuId : mcuIds){
			deviceService.applyJob(jobCode, mcuId, user.getUsername(), data);
		}
		return map;
	}

	@RequestMapping("/readParam1")
	@ResponseBody
	public Map<String, Object> readParam1(HttpServletRequest request, @RequestParam String jobCode, String[] mcuIds){
		Map<String, Object> map = new HashMap<>();
		WtUser user = (WtUser) request.getSession().getAttribute("user");
		JSONObject data = new JSONObject();
		for(String mcuId : mcuIds){
			deviceService.applyJob(jobCode, mcuId, user.getUsername(), data);
		}
		return map;
	}

	@RequestMapping("/writeParam")
	@ResponseBody
	public Map<String, Object> writeParam(HttpServletRequest request, String jobCode, String type, String code, BigDecimal content, String[] mcuIds){
		Map<String, Object> map = new HashMap<>();
		WtUser user = (WtUser) request.getSession().getAttribute("user");
		JSONObject data = new JSONObject();
		data.put("startCode", code);
		JSONArray contents = new JSONArray();
		contents.add(content);
		data.put("content", contents);
		for(String mcuId : mcuIds){
			deviceService.applyJob(jobCode, mcuId, user.getUsername(), data);
		}
		return map;
	}



	@RequestMapping("/writeParam1")
	@ResponseBody
	public Map<String, Object> writeParam1(HttpServletRequest request, String jobCode,BigDecimal content, String[] mcuIds){
		Map<String, Object> map = new HashMap<>();
		WtUser user = (WtUser) request.getSession().getAttribute("user");
		JSONObject data = new JSONObject();
		JSONArray contents = new JSONArray();
		contents.add(content);
		data.put("content", contents);
		for(String mcuId : mcuIds){
			deviceService.applyJob(jobCode, mcuId, user.getUsername(), data);
		}
		return map;
	}

	@RequestMapping("/writeParam2")
	@ResponseBody
	public Map<String, Object> writeParam2(HttpServletRequest request, String jobCode,String address,String startCode, String content, String[] mcuIds){
		Map<String, Object> map = new HashMap<>();
		WtUser user = (WtUser) request.getSession().getAttribute("user");
		JSONObject data = new JSONObject();
		data.put("address",address);
		data.put("startCode", startCode);
//		JSONArray contents = new JSONArray();
//		contents.add(content);
		data.put("content", content);
		for(String mcuId : mcuIds){
			deviceService.applyJob(jobCode, mcuId, user.getUsername(), data);
		}
		return map;
	}

	@RequestMapping("/cancelJob")
	@ResponseBody
	public Map<String, Object> cancelJob(Integer jobId){
		Map<String, Object> map = new HashMap<>();
		map.put("result", deviceManageService.cancelJob(jobId));
		return map;
	}

	@RequestMapping("/deleteMessage")
	@ResponseBody
	public Map<String, Object> deleteMessage(Integer messageId){
		Map<String, Object> map = new HashMap<>();
		map.put("result", deviceManageService.deleteMessage(messageId));
		return map;
	}
}
