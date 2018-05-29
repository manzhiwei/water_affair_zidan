/**
 * 
 */
package com.welltech.dao.history;

import java.util.List;
import java.util.Map;

import com.welltech.dto.WtUserLoginDto;
import com.welltech.entity.WtMenu;
import com.welltech.entity.WtUserLogin;

/**
 * Created by Zhujia at 2017年8月14日 下午4:45:07
 */
public interface LoginDao {

	/**
	 * 获取最大ID
	 * @return
	 */
	int getMaxLoginId();

	/**
	 * 保存登录日志信息
	 * @param login
	 */
	void saveLogin(WtUserLogin login);

	/**
	 * 查找登录列表信息
	 * @param map
	 * @return
	 */
	List<WtUserLoginDto> findPageLoginHistoryList(Map<String, Object> map);

	/**
	 * @param id
	 * @return
	 */
	List<WtMenu> getMenuByUserId(int id);
	
	int deleteLogin(String id);

}
