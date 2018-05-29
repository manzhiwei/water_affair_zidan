package com.welltech.service.index;

import java.util.List;
import java.util.Map;

import com.welltech.dto.PointDto;
import com.welltech.entity.WtParam;
import com.welltech.entity.WtStation;

/**
 * 页面元素服务接口
 * @author wangxin
 *
 */
public interface UiElementService {
	
	/**
	 * 查询所有测点与id对应关系
	 * @return
	 */
	List<PointDto> findAllPointDtos();
	
	/**
	 * 参数与实体对应map
	 * @return
	 */
	Map<String, WtParam> getParams();
	
	/**
	 * 要显示的参数
	 * @return
	 */
	List<WtParam> getDisplayParams();

	/**
	 * 站点别名参数
	 * @param integer
	 * @return
	 */
	Map<String, WtParam> getParamsByStationId(Integer integer);

	/**
	 * 显示参与统计的参数
	 * @param station
	 * @return
	 */
	Map<String, WtParam> getDisplayParamsByStationId(WtStation station);
}
