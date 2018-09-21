/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.finished;

import com.jeeplus.modules.cultural.entity.role.Author;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 书法作品Entity
 * @author orange
 * @version 2018-09-13
 */
public class Calligraphy extends DataEntity<Calligraphy> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private Author author;		// 作者
	private String picture;		// 图片
	private String intro;		// 简介
	private String details;		// 详情
	private Double price;		// 价格
	
	public Calligraphy() {
		super();
	}

	public Calligraphy(String id){
		super(id);
	}

	@ExcelField(title="标题", align=2, sort=1)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="作者", fieldType=Author.class, value="author.name", align=2, sort=2)
	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
	
	@ExcelField(title="图片", align=2, sort=3)
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@ExcelField(title="简介", align=2, sort=4)
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	@ExcelField(title="详情", align=2, sort=5)
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	@ExcelField(title="价格", align=2, sort=6)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}