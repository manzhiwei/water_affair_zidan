/**
 * 
 */
package com.welltech.dao.sysSetting;

import java.util.List;

import com.welltech.dto.WtCompanyDto;
import com.welltech.entity.WtCompany;

/**
 * Created by Zhujia at 2017年7月28日 下午5:15:11
 */
public interface AreaManageDao {

	/**
	 * 查找所有的区域
	 * @return
	 */
	List<WtCompanyDto> findAllCompany();

	/**
	 * 根据id查找区域
	 * @param id
	 * @return 
	 */
	WtCompany findCompanyById(String id);

	/**
	 * 根据id删除区域
	 * @param id
	 */
	void deleteCompanyById(String id);

	/**
	 * 新增区域
	 * @param company
	 */
	void addArea(WtCompany company);

	/**
	 * 更新区域
	 * @param company
	 */
	void updateArea(WtCompany company);

}
