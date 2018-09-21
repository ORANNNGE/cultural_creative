/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.role;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 作者Entity
 * @author orange
 * @version 2018-09-13
 */
public class Author extends DataEntity<Author> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String type;		// 类型
	private String intro;		// 简介
	private String details;		// 详情
	private String picture;		// 图片
	private Double pay;		// 薪酬
	
	public Author() {
		super();
	}

	public Author(String id){
		super(id);
	}

	@ExcelField(title="姓名", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="类型", dictType="cultural_author_type", align=2, sort=2)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="简介", align=2, sort=3)
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	@ExcelField(title="详情", align=2, sort=4)
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	@ExcelField(title="图片", align=2, sort=5)
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@ExcelField(title="薪酬", align=2, sort=6)
	public Double getPay() {
		return pay;
	}

	public void setPay(Double pay) {
		this.pay = pay;
	}
	
}