/**
 * 
 */
package com.welltech.dao;

import java.util.Map;

import com.welltech.entity.WtUser;

/**
 * Created by Zhujia at 2017年7月21日 下午3:45:22
 */
public interface UserDao {

	/**
	 * 根据用户ID获取用户信息
	 * @param userId
	 * @return
	 */
	WtUser getUserInfoByUserId(String userId);

	/**
	 * 修改密码
	 * @param map
	 */
	void updatePwd(Map<String, String> map);

}
