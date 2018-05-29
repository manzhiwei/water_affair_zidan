package com.welltech.dao.sysSetting;

import com.welltech.dto.WtDeviceDataDto;
import com.welltech.dto.WtDeviceDto;
import com.welltech.dto.WtDeviceMessageDto;
import com.welltech.dto.WtDeviceModbusDto;
import com.welltech.entity.WtDeviceConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxin on 2017/12/18.
 */
public interface WtDeviceDao {

    /**
     * 分页查询
     * @param map
     * @return
     */
    List<WtDeviceDto> findPageWtDeviceDtos(Map<String, Object> map);

    /**
     * 参数查询
     * @param mcuId
     * @return
     */
    List<WtDeviceDataDto> findWtDeviceDataDtos(String mcuId);

    /**
     * 传感器参数
     */
    List<WtDeviceModbusDto> findWtDeviceModbusDtos(String mcuId);

    /**
     * 根据类型查询可写的参数
     * @param type
     * @return
     */
    List<WtDeviceConfig> findCanWriteConfigByType(String type);

    /**
     * 查询可读参数长度
     * @param type
     * @return
     */
    int countConfigByType(String type);

    /**
     * 分页查询消息
     * @param map
     * @return
     */
    List<WtDeviceMessageDto> findPageWtDeviceMessageDto(Map<String, Object> map);

    /**
     * 取消任务
     * @param jobId
     * @return
     */
    int updateJobCancel(Integer jobId);

    /**
     * 删除消息
     * @param messageId
     * @return
     */
    int deleteMessage(Integer messageId);
}
