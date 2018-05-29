package com.welltech.controller.statistics;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.welltech.service.index.UiElementService;
import com.welltech.service.statistics.WaterStatisticService;

/**
 * 水质评价分析
 * @author wangxin
 *
 */
@Controller
public class WaterStatisticController {

	@Autowired
	private UiElementService uiElementService;
	
	@Autowired
	private WaterStatisticService waterStatisticService;
	
	@RequestMapping("/waterStatistic")
	public String waterStatistic(Model model,String pointId,String pointName, String params, Integer chartType,
								 @DateTimeFormat(pattern="yyyy-MM-dd HH") Date startTime, @DateTimeFormat(pattern="yyyy-MM-dd HH") Date endTime,
								 @DateTimeFormat(pattern="yyyy-MM-dd") Date date, @DateTimeFormat(pattern="yyyy-MM") Date month,
								 @DateTimeFormat(pattern="yyyy") Date year){
		model.addAttribute("displayParams", uiElementService.getDisplayParams());
		/*model.addAttribute("points", uiElementService.findAllPointDtos());
		model.addAttribute("paramVal",params);
		model.addAttribute("startTime",startTime);
		model.addAttribute("endTime",endTime);
		model.addAttribute("date",date);
		model.addAttribute("month",month);
		model.addAttribute("year",year);
		model.addAttribute("pointId",pointId);
		model.addAttribute("pointName",pointName);
		model.addAttribute("chartType",chartType);*/
		return "statistics/waterStatistic";
	}
	
	@RequestMapping("/waterStatisticHeichouData")
	@ResponseBody
	public Map<String, Object> waterStatisticHeichouData(String pointId, Integer chartType, String params, String typeCode,
			@DateTimeFormat(pattern="yyyy-MM-dd HH") Date startTime, @DateTimeFormat(pattern="yyyy-MM-dd HH") Date endTime,
			@DateTimeFormat(pattern="yyyy-MM-dd") Date date, @DateTimeFormat(pattern="yyyy-MM") Date month,
			@DateTimeFormat(pattern="yyyy") Date year) {
				
		Calendar c = Calendar.getInstance();
		switch (chartType) {
			case 1:
				c.setTime(endTime);
				c.add(Calendar.HOUR_OF_DAY, 1);
				break;
			case 2:
				startTime = date;
				c.setTime(startTime);
				c.add(Calendar.DAY_OF_MONTH, 1);
				break;
			case 3:
				startTime = month;
				c.setTime(startTime);
				c.add(Calendar.MONTH, 1);
				break;
			case 4:
				startTime = year;
				c.setTime(startTime);
				c.add(Calendar.YEAR, 1);
				break;
			default:
				c.setTime(endTime);
				break;
		}
		endTime = c.getTime();

        String[] ids = pointId.split(",");
		List<Integer> stationIds = new ArrayList<>();
		List<Integer> companyIds = new ArrayList<>();
        for(int i = 0; i<ids.length; i++){
        	if(ids[i].trim().startsWith("s")){
        		stationIds.add(Integer.parseInt(ids[i].trim().substring(1)));
			} else{
        		companyIds.add(Integer.parseInt(ids[i].trim()));
			}
        }

		return waterStatisticService.getWaterStatistic(companyIds, stationIds, params, startTime, endTime, "1");
	}

	@RequestMapping("/waterStatisticDibiaoData")
	@ResponseBody
	public Map<String, Object> waterStatisticDibiaoData(String pointId, Integer chartType, String params, String typeCode,
												  @DateTimeFormat(pattern="yyyy-MM-dd HH") Date startTime, @DateTimeFormat(pattern="yyyy-MM-dd HH") Date endTime,
												  @DateTimeFormat(pattern="yyyy-MM-dd") Date date, @DateTimeFormat(pattern="yyyy-MM") Date month,
												  @DateTimeFormat(pattern="yyyy") Date year) {

		Calendar c = Calendar.getInstance();
		switch (chartType) {
			case 1:
				c.setTime(endTime);
				c.add(Calendar.HOUR_OF_DAY, 1);
				break;
			case 2:
				startTime = date;
				c.setTime(startTime);
				c.add(Calendar.DAY_OF_MONTH, 1);
				break;
			case 3:
				startTime = month;
				c.setTime(startTime);
				c.add(Calendar.MONTH, 1);
				break;
			case 4:
				startTime = year;
				c.setTime(startTime);
				c.add(Calendar.YEAR, 1);
				break;
			default:
				c.setTime(endTime);
				break;
		}
		endTime = c.getTime();

        String[] ids = pointId.split(",");
        List<Integer> stationIds = new ArrayList<>();
        List<Integer> companyIds = new ArrayList<>();
        for(int i = 0; i<ids.length; i++){
            if(ids[i].trim().startsWith("s")){
                stationIds.add(Integer.parseInt(ids[i].trim().substring(1)));
            } else{
                companyIds.add(Integer.parseInt(ids[i].trim()));
            }
        }

		return waterStatisticService.getWaterStatistic(companyIds, stationIds, params, startTime, endTime, "2");
	}
}
