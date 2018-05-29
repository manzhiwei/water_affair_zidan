package com.welltech.service.index;

import java.util.List;
import java.util.Map;

import com.welltech.dto.RealtimeNetworkDto;
import com.welltech.framework.aop.pagination.bean.Page;

/**
 * 首页数据服务接口
 * @author WangXin
 *
 */
public interface IndexDataService {

	/**
	 * 实时在线状态
	 * @return
	 */
	@Deprecated
	List<RealtimeNetworkDto> findRealtimeNetworkDto();
	
	/**
	 * 单参数变化趋势
	 * @param page
	 * @param param
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> listSingleParam(Page page, String param, String type);
	
	/**
	 * 24小时水质比重
	 * @param typeCode
	 * @return
	 */
	Map<String, Object> countWaterLevel(String typeCode);

	/**
	 * 实时监控
	 * @return
	 */
	Map<String, Object> getRealtimeMonitoring();

	/**
	 * 24小时预警记录-超标
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> listOverproof(Page page);

	List<Map<String, Object>> listAbnormal(Page page);

	List<Map<String, Object>> listFault(Page page);
	
	/**
	 * 24小时预警记录-故障
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> fault(Page page);
	
}
