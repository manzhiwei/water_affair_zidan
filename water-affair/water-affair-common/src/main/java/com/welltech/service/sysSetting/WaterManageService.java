/**
 * 
 */
package com.welltech.service.sysSetting;

import java.util.ArrayList;
import java.util.List;

import com.welltech.dto.WaterLevelList;
import com.welltech.dto.WtWaterLevelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.dao.sysSetting.WaterManageDao;
import com.welltech.dto.WtParamDto;
import com.welltech.dto.WtWaterDto;
import com.welltech.entity.WtParam;
import com.welltech.entity.WtWaterLevel;

/**
 * Created by Zhujia at 2017年8月6日 下午10:37:17
 */
@Service
public class WaterManageService {
	
	@Autowired
	WaterManageDao waterManageDao;

	/**
	 * @return
	 */
	public List<WtWaterDto> findAllWaterParamDto() {
		List<WtWaterDto> waters = new ArrayList<WtWaterDto>();
		List<WtParam> params = waterManageDao.findAllParam();
		if(null != params && params.size()>0){
			for(WtParam param:params){
				WtWaterDto waterDto = new WtWaterDto();
				waterDto.setParam(param.getParam());
				waterDto.setParamName(param.getParamName());
				List<WtParamDto> paramDto = waterManageDao.findWaterLevelRangeByParam(param.getParam());
				waterDto.setParamDto(paramDto);
				waters.add(waterDto);
			}
		}
		return waters;
	}

	/**
	 * 根据参数获取指标信息
	 * @param param
	 * @return
	 */
    public List<WtWaterLevel> getWtWaterLevelByParam(String param) {
    	return waterManageDao.getWtWaterLevelByParam(param);
    }

	/**
	 * 保存指标等级
	 * @param water
	 */
	public void saveWaterLevel(WaterLevelList water) {
		String param = water.getParam();
		List<WtWaterLevelDto> levelDtos = water.getWater();
		for(WtWaterLevelDto dto : levelDtos){
			if(dto==null||dto.getTypeCode()==null)
				continue;
			WtWaterLevel level = new WtWaterLevel();
			level.setParam(param);
			level.setTypeCode(dto.getTypeCode());
			level.setLevel(dto.getLevel());

			if("1".equals(dto.getOption1()) || "2".equals(dto.getOption2())){
				level.setHasLower("1");
			}
			if("2".equals(dto.getOption1()) || "1".equals(dto.getOption2())){
				level.setHasUpper("1");
			}
			if("3".equals(dto.getOption1()) || "4".equals(dto.getOption2())){
				level.setHasLower("1");
				level.setContainLower("1");
			}
			if("4".equals(dto.getOption1()) || "3".equals(dto.getOption2())){
				level.setHasUpper("1");
				level.setContainUpper("1");
			}

			if(Double.parseDouble(dto.getValue1())<=Double.parseDouble(dto.getValue2()) || "0".equals(dto.getOption2())){
				//PH值有两个条件设置，所以这里不适用
				if("p2".equals(water.getParam())) {
					if("2".equals(dto.getOption1()) && "2".equals(dto.getOption2())) {
						level.setLowerLimit(Double.parseDouble(dto.getValue2()));
						level.setUpperLimit(Double.parseDouble(dto.getValue1()));
					}else {
						level.setLowerLimit(Double.parseDouble(dto.getValue1()));
						level.setUpperLimit(Double.parseDouble(dto.getValue2()));
					}
				}else {
					level.setLowerLimit(Double.parseDouble(dto.getValue1()));
					level.setUpperLimit(Double.parseDouble(dto.getValue2()));
				}
			}else{
				//PH值有两个条件设置，所以这里不适用
				if("p2".equals(water.getParam())) {
					if("2".equals(dto.getOption1()) && "2".equals(dto.getOption2())) {
						level.setLowerLimit(Double.parseDouble(dto.getValue2()));
						level.setUpperLimit(Double.parseDouble(dto.getValue1()));
					}else {
						level.setLowerLimit(Double.parseDouble(dto.getValue1()));
						level.setUpperLimit(Double.parseDouble(dto.getValue2()));
					}
				}else {
					level.setLowerLimit(Double.parseDouble(dto.getValue2()));
					level.setUpperLimit(Double.parseDouble(dto.getValue1()));
				}
			}
			waterManageDao.updateWaterLevel(level);
		}
	}
}
