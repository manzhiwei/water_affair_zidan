/**
 * 
 */
package com.welltech.dao.sysSetting;

import java.util.List;

import com.welltech.dto.WtParamDto;
import com.welltech.entity.WtParam;
import com.welltech.entity.WtWaterLevel;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Zhujia at 2017年8月6日 下午11:22:02
 */
public interface WaterManageDao {

	/**
	 * @return
	 */
	List<WtParam> findAllParam();
	
	/**
	 * 
	 * @return
	 */
	List<WtParamDto> findWaterLevelRangeByParam(String param);

	/**
	 * 根据参数获取指标数据
	 * @param param
	 * @return
	 */
    List<WtWaterLevel> getWtWaterLevelByParam(@Param("param") String param);

	/**
	 * 更新指标数据
	 * @param level
	 */
	void updateWaterLevel(WtWaterLevel level);
}
