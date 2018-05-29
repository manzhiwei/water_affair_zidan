/**
 * 
 */
package com.welltech.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.dao.UserDao;
import com.welltech.entity.WtUser;

/**
 * Created by Zhujia at 2017年7月24日 下午5:15:32
 */
@Service
public class UserService {
	
	@Autowired
	UserDao userDao;

	/**
	 * @param string
	 * @return
	 */
	public WtUser getUserInfoByUserId(String userId) {
		return userDao.getUserInfoByUserId(userId);
	}

	/**
	 * @param userId
	 * @param newPwd
	 */
	public void updatePwd(String userId,String newPwd) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("newPwd", newPwd);
		userDao.updatePwd(map);
	}

}
