/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.couplets;

import com.jeeplus.modules.cultural.entity.couplets.Lexicon;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.cultural.entity.spec.Size;
import com.jeeplus.modules.cultural.entity.spec.Frame;
import com.jeeplus.modules.cultural.entity.spec.Craft;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 成品楹联Entity
 * @author orange
 * @version 2018-09-11
 */
public class Couplets extends DataEntity<Couplets> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 标题
	private Lexicon lexicon;		// 词库
	private Size size;		// 尺寸
	private Frame frame;		// 楹联框
	private Craft craft;		// 制作工艺
	private String picture;		// 图片
	private String details;		// 详情
	private String recommend;		// 推荐到首页
	private Double price;		// 定价
	
	public Couplets() {
		super();
	}

	public Couplets(String id){
		super(id);
	}

	@ExcelField(title="标题", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="词库不能为空")
	@ExcelField(title="词库", fieldType=Lexicon.class, value="lexicon.title", align=2, sort=2)
	public Lexicon getLexicon() {
		return lexicon;
	}

	public void setLexicon(Lexicon lexicon) {
		this.lexicon = lexicon;
	}
	
	@ExcelField(title="尺寸", fieldType=Size.class, value="size.name", align=2, sort=3)
	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}
	
	@ExcelField(title="楹联框", fieldType=Frame.class, value="frame.name", align=2, sort=4)
	public Frame getFrame() {
		return frame;
	}

	public void setFrame(Frame frame) {
		this.frame = frame;
	}
	
	@ExcelField(title="制作工艺", fieldType=Craft.class, value="craft.name", align=2, sort=5)
	public Craft getCraft() {
		return craft;
	}

	public void setCraft(Craft craft) {
		this.craft = craft;
	}
	
	@ExcelField(title="图片", align=2, sort=6)
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@ExcelField(title="详情", align=2, sort=7)
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	@ExcelField(title="推荐到首页", dictType="cultural_recommend", align=2, sort=8)
	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	
	@ExcelField(title="定价", align=2, sort=9)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}