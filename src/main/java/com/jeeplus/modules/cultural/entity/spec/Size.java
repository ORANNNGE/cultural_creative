/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.spec;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 尺寸Entity
 * @author orange
 * @version 2018-09-05
 */
public class Size extends DataEntity<Size> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String details;		// 简介
	
	public Size() {
		super();
	}

	public Size(String id){
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
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
}