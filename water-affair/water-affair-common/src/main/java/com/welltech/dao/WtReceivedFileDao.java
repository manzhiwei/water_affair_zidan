package com.welltech.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.welltech.entity.WtReceivedFile;

/**
 * 接收图片或视频dao
 * @author wangxin
 *
 */
public interface WtReceivedFileDao {
	/**
	 * 获取图片或视频
	 * @return
	 */
	List<WtReceivedFile> listByMcu(@Param("type") String type, @Param("mcu") String mcu);
}
