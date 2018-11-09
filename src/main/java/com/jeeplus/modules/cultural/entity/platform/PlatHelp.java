/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.platform;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 帮助Entity
 * @author orange
 * @version 2018-11-07
 */
public class PlatHelp extends DataEntity<PlatHelp> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String details;		// 详情
	
	public PlatHelp() {
		super();
	}

	public PlatHelp(String id){
		super(id);
	}

	@ExcelField(title="名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="详情", align=2, sort=2)
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
}