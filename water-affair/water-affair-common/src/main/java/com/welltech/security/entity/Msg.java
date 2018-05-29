package com.welltech.security.entity;

/**
 * 消息类,用于后端返回结果给前端
 * Created by Zhujia at 2017年7月18日 下午6:53:41
 */
public class Msg {

	//标题
	private String title;
	//内容
	private String content;
	//额外信息
	private String etraInfo;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEtraInfo() {
		return etraInfo;
	}
	public void setEtraInfo(String etraInfo) {
		this.etraInfo = etraInfo;
	}
     
     
}
