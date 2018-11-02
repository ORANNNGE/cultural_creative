/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.order;

import com.jeeplus.modules.cultural.entity.spec.Typeface;
import com.jeeplus.modules.cultural.entity.spec.Size;
import com.jeeplus.modules.cultural.entity.order.Combo;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 楹联词库价格Entity
 * @author orange
 * @version 2018-10-26
 */
public class LexiconPrice extends DataEntity<LexiconPrice> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 类型
	private Typeface typeface;		// 字体
	private Size size;		// 尺寸
	private Combo combo;		// 套餐
	private String typefaceName;		// 字体名
	private String sizeName;		// 尺寸名
	private String comboName;		// 楹联框名
	private Double price;		// 价格
	
	public LexiconPrice() {
		super();
	}

	public LexiconPrice(String id){
		super(id);
	}

	@ExcelField(title="类型", dictType="cultural_lexicon_type", align=2, sort=1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="字体", fieldType=Typeface.class, value="typeface.name", align=2, sort=2)
	public Typeface getTypeface() {
		return typeface;
	}

	public void setTypeface(Typeface typeface) {
		this.typeface = typeface;
	}
	
	@ExcelField(title="尺寸", fieldType=Size.class, value="size.name", align=2, sort=3)
	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}
	
	@ExcelField(title="套餐", fieldType=Combo.class, value="combo.name", align=2, sort=4)
	public Combo getCombo() {
		return combo;
	}

	public void setCombo(Combo combo) {
		this.combo = combo;
	}
	
	@ExcelField(title="字体名", align=2, sort=5)
	public String getTypefaceName() {
		return typefaceName;
	}

	public void setTypefaceName(String typefaceName) {
		this.typefaceName = typefaceName;
	}
	
	@ExcelField(title="尺寸名", align=2, sort=6)
	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}
	
	@ExcelField(title="楹联框名", align=2, sort=7)
	public String getComboName() {
		return comboName;
	}

	public void setComboName(String comboName) {
		this.comboName = comboName;
	}
	
	@ExcelField(title="价格", align=2, sort=8)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}