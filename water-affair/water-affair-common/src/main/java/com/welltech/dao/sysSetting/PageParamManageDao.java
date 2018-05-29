/**
 * 
 */
package com.welltech.dao.sysSetting;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.welltech.dto.WtParamDataDto;
import com.welltech.entity.WtParam;
import com.welltech.entity.WtWaterLevel;

/**
 * Created by Zhujia at 2017年8月5日 下午3:12:23
 */
public interface PageParamManageDao {

	/**
	 * 查找所有的页面参数
	 * @return
	 */
	List<WtParam> findAllParams();

	/**
	 * 根据id查找某个页面参数
	 * @param id
	 * @return
	 */
	WtParam findParamById(Integer id);

	/**
	 * 更新参数
	 * @param param
	 */
	void update(WtParam param);

	/**
	 * 查找所有显示的参数
	 * @return
	 */
	List<WtParam> findAllDisplayParamsList(@Param("stationId")String stationId);

	List<String> findAllDisplayParams(@Param("stationId")String stationId);

	/**
	 * @return
	 */
	List<String> getDisplayParam();

	List<WtParam> getDisplayParamList();


	/**
	 * 查询日报表/仅数据
	 * @param map
	 * @return
	 */
	List<WtParamDataDto> getDailyReportDataList(Map<String, Object> map);

	WtParamDataDto getDailyReportData(Map<String, Object> map);
	
	/**
	 * 查询日报表/仅日期
	 * @param map
	 * @return
	 */
	WtParamDataDto getDailyReportDate(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	List<WtParamDataDto> getReportDataList(Map<String, Object> map);

	/**
	 * @param map
	 * @return
	 */
	WtParamDataDto getReportData(Map<String, Object> map);

	/**
	 * 获取地表水质
	 * @param map
	 * @return
	 */
	String getDiBiao(Map<String, String> map);

	String getDiBiaoGexing(Map<String, String> map);
	
	/**
	 * 获取地表水质为空的字段是否要显示
	 * @param map
	 * @return
	 */
	String getDiBiaoNull(Map<String, String> map);

	String getDiBiaoNullGexing(Map<String, String> map);

	/**
	 * 获取param获取参数
	 * @param params
	 * @return
	 */
	List<WtParam> findParamsByParam(@Param("params") List<String> params , @Param("displayColumn") String displayColumn);

	/**
	 * 获取param获取带别名参数
	 * @param params
	 * @return
	 */
	List<WtParam> findALiasParamsByParam(@Param("params") List<String> params, @Param("stationId") String id, @Param("displayColumn") String displayColumn);


	String getStationParamAdjustInfo(@Param("id") String id, @Param("param") String param);
	
	
	WtWaterLevel getWtWaterLevel(@Param("param") String params, @Param("typeCode") String typeCode, @Param("levelCode") String levelCode);


}
