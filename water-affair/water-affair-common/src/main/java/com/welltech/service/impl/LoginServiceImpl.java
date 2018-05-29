/**
 * 
 */
package com.welltech.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.welltech.dao.history.LoginDao;
import com.welltech.dto.WtUserLoginDto;
import com.welltech.entity.WtMenu;
import com.welltech.entity.WtUserLogin;
import com.welltech.framework.aop.pagination.bean.MyPage;
import com.welltech.framework.aop.pagination.bean.Page;
import com.welltech.framework.aop.pagination.bean.Pagination;

/**
 * Created by Zhujia at 2017年8月14日 下午4:31:36
 */
@Service
public class LoginServiceImpl {

	@Autowired
	private LoginDao loginDao;
	
	/**
	 * 保存登录信息
	 * @param id
	 * @param ip
	 */
	public void saveLoginInfo(Integer id, String ip) {
		WtUserLogin login = new WtUserLogin();
		int maxId = loginDao.getMaxLoginId()+1;
		login.setId(maxId);
		login.setLoginStatus("1");
		login.setUserId(id);
		login.setLoginIp(ip);
		loginDao.saveLogin(login);
	}

	/**
	 * @param myPage
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<WtUserLoginDto> listLoginHistory(MyPage myPage, Date beginTime, Date endTime) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Page page = new Pagination();
		page.setDefalutPageRows(0);
		page.setCurrentPage(myPage.getCurrentPage());
		map.put("page", page);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		List<WtUserLoginDto> result = loginDao.findPageLoginHistoryList(map);
		myPage.setTotalPages(page.getTotalPages());
		return result;
	}

	/**
	 * @param id
	 * @return
	 */
	public List<WtMenu> getMenuByUserId(int id) {
		return loginDao.getMenuByUserId(id);
	}

}
