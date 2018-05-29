package com.welltech.dao;

import java.util.List;

import com.welltech.dto.PointDto;

/**
 * 页面元素持久层接口
 * @author wangxin
 *
 */
public interface UiElementDao {
	/**
	 * 所有测点
	 * @return
	 */
	List<PointDto> findAllPointDtos();
}
