/**
 * 
 */
package com.welltech.dto;

/**
 * Created by Zhujia at 2017年8月15日 下午10:56:33
 */
public class WtAreaStationDto {
	
	/**
	 * 联合主键
	 */
	private String id;
	
	/**
	 * 节点的唯一标识
	 */
	private String tId;
	
	/**
	 * 结点名字
	 */
	private String name;
	
	/**
	 * 父结点
	 */
	private String pId;
	
	/**
	 * 是否为无法勾选结点
	 */
	private boolean nocheck;
	
	/**
	 * 是否默认打开
	 */
	private boolean open;

	/**
	 * 是否禁止勾选
	 */
	private boolean chkDisabled;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String gettId() {
		return tId;
	}

	public void settId(String tId) {
		this.tId = tId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}
}
