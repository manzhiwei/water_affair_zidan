/**
 * 
 */
package com.welltech.service.sysSetting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.common.util.MD5Util;
import com.welltech.dao.history.LoginDao;
import com.welltech.dao.sysSetting.OperatorManageDao;
import com.welltech.dto.WtUserDto;
import com.welltech.entity.WtRole;
import com.welltech.entity.WtUser;
import com.welltech.entity.WtUserRole;

/**
 * Created by Zhujia at 2017年7月27日 上午9:59:13
 */
@Service
public class OperatorManageService {
	
	@Autowired
	OperatorManageDao operatorManageDao;
	@Autowired
	LoginDao loginDao;
	
	/**
	 * 查询所有的用户及其角色
	 * @return
	 */
	public List<WtUserDto> findAllUserRole() {
		return operatorManageDao.findAllUserRole();
	}

	/**
	 * 查询所有的角色组
	 * @return
	 */
	public List<WtRole> findAllRole() {
		return operatorManageDao.findAllRole();
	}

	/**
	 * @param id
	 * @return
	 */
	public WtUser findUserById(String id) {
		return operatorManageDao.findUserById(id);
	}
	
	/**
	 * @param id
	 * @return
	 */
	public WtUserDto findUserDtoById(String id) {
		return operatorManageDao.findUserDtoById(id);
	}

	/**
	 * @param username
	 * @return
	 */
	public WtUser findUserByUsername(String username) {
		return operatorManageDao.findUserByUsername(username);
	}

	
	/**
	 * @param id
	 */
	public void deleteUser(String id) {
		operatorManageDao.deleteUserById(id);
		operatorManageDao.deleteUserRoleByUserId(id);
		//删除用户的登录记录
		
		loginDao.deleteLogin(id);
	}

	/**
	 * @param wtUser
	 */
	public void addUser(WtUser wtUser,String[] roleId) {
		int maxId = operatorManageDao.getMaxUserId()+1;
		wtUser.setId(maxId);
		wtUser.setUsername(wtUser.getUsername().trim());
		wtUser.setRealName(wtUser.getRealName().trim());
		wtUser.setCellphone(wtUser.getCellphone().trim());
		wtUser.setPassword(MD5Util.encode(wtUser.getPassword()));
		wtUser.setPasswordSalt("affair");
		operatorManageDao.addUser(wtUser);
		for(int i=0;i<roleId.length;i++){
			WtUserRole userrole = new WtUserRole();
			userrole.setUserId(maxId);
			userrole.setRoleId(Integer.parseInt(roleId[i]));
			operatorManageDao.addUserRole(userrole);
		}
	}

	/**
	 * @param user
	 * @param roleId
	 */
	public void updateUser(WtUser user, String[] roleId) {
		//重新关联权限
		operatorManageDao.deleteUserRoleByUserId(user.getId()+"");
		for(int i=0;i<roleId.length;i++){
			WtUserRole userrole = new WtUserRole();
			userrole.setUserId(user.getId());
			userrole.setRoleId(Integer.parseInt(roleId[i]));
			operatorManageDao.addUserRole(userrole);
		}
		//更新用户
		operatorManageDao.updateUser(user);
	}

}
