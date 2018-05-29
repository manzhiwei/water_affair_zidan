/**
 * 
 */
package com.welltech.entity;

/**
 * Created by Zhujia at 2017年8月8日 下午4:52:12
 */
public class WtMenu {

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
	 * 菜单链接
	 */
	private String url;
	
	/**
	 * 菜单图标
	 */
	private String icon;
	
	/**
	 * 打开方式，_blank
	 */
	private String target;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
}
