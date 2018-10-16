/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.order;

import com.jeeplus.modules.cultural.entity.couplets.Couplets;
import com.jeeplus.modules.cultural.entity.spec.Size;
import com.jeeplus.modules.cultural.entity.spec.Frame;
import com.jeeplus.modules.cultural.entity.spec.Craft;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 成品楹联价格Entity
 * @author orange
 * @version 2018-10-16
 */
public class CoupletsPrice extends DataEntity<CoupletsPrice> {
	
	private static final long serialVersionUID = 1L;
	private Couplets couplets;		// 成品
	private Size size;		// 尺寸
	private Frame frame;		// 楹联框
	private Craft craft;		// 制作工艺
	private Double price;		// 价格
	
	public CoupletsPrice() {
		super();
	}

	public CoupletsPrice(String id){
		super(id);
	}

	@ExcelField(title="成品", fieldType=Couplets.class, value="couplets.name", align=2, sort=1)
	public Couplets getCouplets() {
		return couplets;
	}

	public void setCouplets(Couplets couplets) {
		this.couplets = couplets;
	}
	
	@ExcelField(title="尺寸", fieldType=Size.class, value="size.name", align=2, sort=2)
	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}
	
	@ExcelField(title="楹联框", fieldType=Frame.class, value="frame.name", align=2, sort=3)
	public Frame getFrame() {
		return frame;
	}

	public void setFrame(Frame frame) {
		this.frame = frame;
	}
	
	@ExcelField(title="制作工艺", fieldType=Craft.class, value="craft.name", align=2, sort=4)
	public Craft getCraft() {
		return craft;
	}

	public void setCraft(Craft craft) {
		this.craft = craft;
	}
	
	@ExcelField(title="价格", align=2, sort=5)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}