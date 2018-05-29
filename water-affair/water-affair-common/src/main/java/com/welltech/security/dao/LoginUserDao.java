package com.welltech.security.dao;

import com.welltech.security.entity.WtUser;

public interface LoginUserDao {

	public WtUser findByUserName(String username);
	
}
