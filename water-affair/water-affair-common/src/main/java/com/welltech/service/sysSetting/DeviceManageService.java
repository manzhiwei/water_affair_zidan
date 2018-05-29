package com.welltech.service.sysSetting;

import com.welltech.dao.sysSetting.WtDeviceDao;
import com.welltech.dto.WtDeviceDataDto;
import com.welltech.dto.WtDeviceDto;
import com.welltech.dto.WtDeviceMessageDto;
import com.welltech.dto.WtDeviceModbusDto;
import com.welltech.entity.WtDeviceConfig;
import com.welltech.framework.aop.pagination.bean.MyPage;
import com.welltech.framework.aop.pagination.bean.Page;
import com.welltech.framework.aop.pagination.bean.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxin on 2017/12/18.
 */
@Service
public class DeviceManageService {

    @Autowired
    private WtDeviceDao wtDeviceDao;

    /**
     * 分页查询测点信息
     * @return
     */
    public List<WtDeviceDto> listWtDeviceDtos(Map<String, Object> map){
        List<WtDeviceDto> result = wtDeviceDao.findPageWtDeviceDtos(map);
        return result;
    }

    public List<WtDeviceDataDto> listWtDeviceDataDtos(String mcuId){
        return wtDeviceDao.findWtDeviceDataDtos(mcuId);
    }

    public List<WtDeviceModbusDto> listWtDeviceModbusDtos(String mcuId){
        return wtDeviceDao.findWtDeviceModbusDtos(mcuId);
    }

    public List<WtDeviceConfig> findCanWriteConfigByType(String type){
        return wtDeviceDao.findCanWriteConfigByType(type);
    }

    public int countConfigByType(String type){
        return wtDeviceDao.countConfigByType(type);
    }

    public List<WtDeviceMessageDto> listWtDeviceMessageDtos(Page page, String mcuId) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("page", page);
        map.put("mcuId", mcuId);
        return wtDeviceDao.findPageWtDeviceMessageDto(map);
    }

    public int cancelJob(Integer jobId){
        return wtDeviceDao.updateJobCancel(jobId);
    }

    public int deleteMessage(Integer messageId){
        return wtDeviceDao.deleteMessage(messageId);
    }
}
