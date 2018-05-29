/**
 * 
 */
package com.welltech.dto;

/**
 * Created by Zhujia at 2017年8月8日 下午5:02:54
 */
public class WtMenuDto {
	
	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 菜单名称
	 */
	private String name;
	
	/**
	 * 父级菜单
	 */
	private Integer pId;
	
	/**
	 * 是否已选中
	 */
	private boolean checked;
	
	/**
	 * 是否打开
	 */
	private boolean open;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	
}
