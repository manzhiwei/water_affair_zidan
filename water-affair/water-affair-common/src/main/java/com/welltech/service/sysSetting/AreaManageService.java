/**
 * 
 */
package com.welltech.service.sysSetting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.dao.sysSetting.AreaManageDao;
import com.welltech.dto.WtCompanyDto;
import com.welltech.entity.WtCompany;

/**
 * Created by Zhujia at 2017年7月28日 下午4:40:53
 */
@Service
public class AreaManageService {

	@Autowired
	private AreaManageDao areaManageDao;
	
	/**
	 * @return
	 */
	public List<WtCompanyDto> findAllCompany() {
		return areaManageDao.findAllCompany();
	}

	/**
	 * @param id
	 * @return
	 */
	public WtCompany findCompanyById(String id) {
		return areaManageDao.findCompanyById(id);
	}

	/**
	 * @param id
	 */
	public void deleteCompanyById(String id) {
		areaManageDao.deleteCompanyById(id);
	}

	/**
	 * @param company
	 */
	public void addArea(WtCompany company) {
		areaManageDao.addArea(company);
	}
	/**
	 * @param company
	 */
	public void updateArea(WtCompany company) {
		areaManageDao.updateArea(company);
	}

}
