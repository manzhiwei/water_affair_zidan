package com.welltech.entity;

import java.util.Date;

/**
 * 文件实体
 * @author wangxin
 *
 */
public class WtReceivedFile {

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 采集器序列号
	 */
	private String sn;
	
	/**
	 * 文件名
	 */
	private String fileName;
	
	/**
	 * 文件链接
	 */
	private String fileUrl;
	
	/**
	 * 文件目录
	 */
	private String fileDir;
	
	/**
	 * 文件meta类型
	 */
	private String fileType;
	
	/**
	 * 类型
	 * 1：图片 2：视频
	 */
	private String type;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the sn
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * @param sn the sn to set
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileUrl
	 */
	public String getFileUrl() {
		return fileUrl;
	}

	/**
	 * @param fileUrl the fileUrl to set
	 */
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	/**
	 * @return the fileDir
	 */
	public String getFileDir() {
		return fileDir;
	}

	/**
	 * @param fileDir the fileDir to set
	 */
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
