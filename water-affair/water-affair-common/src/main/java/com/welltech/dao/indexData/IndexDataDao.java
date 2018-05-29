package com.welltech.dao.indexData;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.welltech.dto.RealtimeNetworkDto;
import com.welltech.entity.WtStationMonitor;
import com.welltech.entity.WtWaterLevel;
import com.welltech.entity.WtWaterType;

/**
 * 首页数据查询
 * @author WangXin
 *
 */
public interface IndexDataDao {

	/**
	 * 查询实时在线状态
	 * @return
	 */
	@Deprecated
	List<RealtimeNetworkDto> findRealtimeNetworkDto();
	
	/**
	 * 单参数变化趋势
	 * @param
	 * @return map 依次为站点名，两条数据差值，参数值
	 */
	List<Map<String, Object>> findPageSingleParam(Map<String, Object> map);
	
	/**
	 * 通过站点id查询参数量程
	 * @param stationId
	 * @return
	 */
	List<WtStationMonitor> findMonitorsByStationId(Integer stationId);
	
	/**
	 * 查询生效的水质分类等级
	 * @return
	 */
	List<WtWaterLevel> findEffectiveWaterLevel(@Param("typeCode") String typeCode);
	
	/**
	 * 查询生效的水质分类参数
	 * @param typeCode
	 * @return
	 */
	List<String> findEffectiveWaterLevelParams(@Param("typeCode") String typeCode);
	
	/**
	 * 按水质分类查询分类结果的名称，按严重程度排序
	 * <br/>ps.黑臭三分：重度黑臭，轻度黑臭，合格
	 * @param typeCode
	 * @return
	 */
	List<String> findLevelName(@Param("typeCode") String typeCode);
	
	/**
	 * 按水质分类查询分类结果的名称，按优质程度排序
	 * <br/>ps.黑臭三分：合格，轻度黑臭，重度黑臭
	 * @param typeCode
	 * @return
	 */
	List<String> findLevelNameAsc(@Param("typeCode") String typeCode);

	/**
	 * 查询站点数
	 * @return
	 */
	int countStationNum();
	
	/**
	 * 查询站点数
	 * @return
	 */
	List<Map<String, Object>> countStationNum2Map();

	/**
	 * 查询异常站点数
	 * @param params
	 * @return
	 */
	int countAbnormalStationNum(@Param("params") List<String> params);
	/**
	 * 查询异常站点数
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> countAbnormalStationNum2Map(@Param("params") List<String> params);
	/**
	 * 查询离线站点数
	 */
	int countOfflineStationNum();
	
	/**
	 * 查询离线站点数
	 */
	List<Map<String, Object>> countOfflineStationNum2Map();

	/**
	 * 查询超标站点数
	 */
	int countOverproofStationNum(@Param("params") List<String> params);
	/**
	 * 查询超标站点数
	 */
	List<Map<String, Object>> countOverproofStationNum2Map(@Param("params") List<String> params);
	
	/**
	 * 24小时预警记录
	 * @param
	 * @return map 依次为站点名，两条数据差值，参数值
	 */
	List<Map<String, Object>> findPageOverproof(Map<String, Object> map);
	
	/**
	 * 水质类别
	 * @param typeCode
	 * @return
	 */
	List<WtWaterType> findWtWaterTypesByTypeCode(@Param("typeCode") String typeCode);
	
	/**
	 * 24小时故障记录
	 * @param
	 * @return map 依次为站点名，两条数据差值，参数值
	 */
	List<Map<String, Object>> findPageFault(Map<String, Object> map);
}
