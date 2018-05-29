/**
 * 
 */
package com.welltech.service.history;

import java.text.MessageFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.common.util.OperateCodeEnum;
import com.welltech.dao.history.OperateHistoryDao;
import com.welltech.dto.WtUserLogDto;
import com.welltech.dto.WtUserLoginDto;
import com.welltech.entity.WtUserLog;
import com.welltech.framework.aop.pagination.bean.MyPage;
import com.welltech.framework.aop.pagination.bean.Page;
import com.welltech.framework.aop.pagination.bean.Pagination;

/**
 * Created by Zhujia at 2017年8月28日 下午10:59:00
 */
@Service
public class OperateHistoryServiceImpl {

	@Autowired
	private OperateHistoryDao operateHistoryDao; 
	
	/**
	 * @param myPage
	 * @param beginTime
	 * @param endTime
	 * @param operateName
	 * @return
	 */
	public List<WtUserLogDto> listOperateHistory(MyPage myPage, Date beginTime, Date endTime, String operateName) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Page page = new Pagination();
		page.setDefalutPageRows(0);
		page.setCurrentPage(myPage.getCurrentPage());
		map.put("page", page);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("operateName", operateName);
		List<WtUserLogDto> result = operateHistoryDao.findPageOperateHistoryList(map);
		myPage.setTotalPages(page.getTotalPages());
		return result;
	}
	
	public void addOperate(OperateCodeEnum ocenum,Integer userId,String...param){
		WtUserLog log = new WtUserLog();
		int maxId = operateHistoryDao.getMaxId()+1;
		log.setId(maxId);
		log.setUserId(userId);
		log.setOperateTime(new Date());
		log.setOperateName(ocenum.getTitle());
		log.setOperateDesc(this.parseContent(ocenum.getContent(),param));
		operateHistoryDao.saveOperate(log);
	}

	/**
	 * @param content
	 * @param param
	 * @return
	 */
	private String parseContent(String content, String...param) {
		String result = MessageFormat.format(content, param)+"，执行结果：完成";
		return result;
	}

}
