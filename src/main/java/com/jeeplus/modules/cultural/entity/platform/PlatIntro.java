/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.platform;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 平台简介Entity
 * @author orange
 * @version 2018-10-24
 */
public class PlatIntro extends DataEntity<PlatIntro> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 平台简介
	private String intro;		// 简介
	
	public PlatIntro() {
		super();
	}

	public PlatIntro(String id){
		super(id);
	}

	@ExcelField(title="平台简介", align=2, sort=1)
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