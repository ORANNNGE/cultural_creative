/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.spec;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 制作工艺Entity
 * @author orange
 * @version 2018-09-05
 */
public class Craft extends DataEntity<Craft> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String intro;		// 简介
	
	public Craft() {
		super();
	}

	public Craft(String id){
		super(id);
	}

	@ExcelField(title="名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="简介", align=2, sort=2)
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	
}