package com.welltech;

import javax.annotation.Resource;

import com.welltech.dao.WtParamDao;
import com.welltech.dao.WtStationDao;
import com.welltech.dao.WtStationMonitorDao;
import com.welltech.dao.sysSetting.WaterManageDao;
import com.welltech.dto.WaterLevelList;
import com.welltech.dto.WaterLevelResult;
import com.welltech.dto.WtDataRawDto;
import com.welltech.entity.*;
import com.welltech.service.statistics.WaterLevelService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.welltech.dao.WtDataRawDao;
import com.welltech.service.statistics.CommonReportService;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepTest {
	
	@Autowired
	WaterLevelService waterLevelService;

	@Autowired
	WtStationDao wtStationDao;

	@Autowired
	WtDataRawDao wtDataRawDao;

	@Autowired
	WtStationMonitorDao wtStationMonitorDao;

	@Autowired
	WtParamDao wtParamDao;

	@Autowired
	WaterManageDao waterManageDao;
	
	@Test
	@Ignore
	public void test(){
		String typeCode = "1";
		String stationStandard = "1";	//标准测点
//		stationStandard = "2";	//个性化测点
		List<WtStation> stations = wtStationDao.findStationByType(typeCode);
		if(stations!=null && stations.size()>0){
			for(WtStation station : stations){	//先统计标准测点
				List<WtStationMonitor> monitors = waterLevelService.listMonitorByStationId(station.getId());
				List<WtParam> params = waterLevelService.listWtParam();
				List<String> searchParams = new ArrayList<String>();
				for(WtParam param: params){
					searchParams.add(param.getParam());//避免sql写重复内容
				}
				List<WtWaterLevel> waterLevels = waterLevelService.listWaterLevel();
				WtDataRawDto nowData = wtDataRawDao.findNowWtDataRawDtoByStationId(searchParams, station.getId());
				WaterLevelResult result = waterLevelService.calculateWaterLevel(station,nowData,monitors,params,waterLevels);
				System.out.println(result);
			}
		}
	}

	@Test
	@Ignore
	public void testFlow(){

	}
	
}
