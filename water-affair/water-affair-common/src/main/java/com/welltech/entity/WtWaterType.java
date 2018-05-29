package com.welltech.entity;

public class WtWaterType {
	
	/**
	 * 主键id
	 */
	private Integer id;
	
	/**
	 * 类型编号
	 */
	private String typeCode;
	
	/**
	 * 类型名称
	 */
	private String typeName;
	
	/**
	 * 等级
	 */
	private String level;
	
	/**
	 * 等级序号
	 */
	private String levelCode;
	
	/**
	 * 描述
	 */
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
