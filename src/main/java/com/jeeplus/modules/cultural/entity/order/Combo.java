/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.order;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 套餐Entity
 * @author orange
 * @version 2018-10-24
 */
public class Combo extends DataEntity<Combo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 套餐名
	private String intro;		// 套餐介绍
	
	public Combo() {
		super();
	}

	public Combo(String id){
		super(id);
	}

	@ExcelField(title="套餐名", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="套餐介绍", align=2, sort=2)
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	
}