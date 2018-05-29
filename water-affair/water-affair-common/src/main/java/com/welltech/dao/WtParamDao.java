package com.welltech.dao;

import java.util.List;

import com.welltech.entity.WtParam;
import org.apache.ibatis.annotations.Param;

/**
 * 参数dao
 * @author wangxin
 *
 */
public interface WtParamDao {

	/**
	 * 查询所有参数
	 * @return
	 */
	List<WtParam> findAllWtParams();
	
	/**
	 * 查询所有显示的参数
	 * @return
	 */
	List<WtParam> findDisplayWtParams();
	
	/**
	 * 根据参数查询参数名称
	 * @param param
	 * @return
	 */
	WtParam findByParam(String param);

	/**
	 * 获取显示并参与统计的参数
	 * @return
	 */
    List<WtParam> findStatisticsDisplayWtParams(@Param("stationId") String stationId);
}
