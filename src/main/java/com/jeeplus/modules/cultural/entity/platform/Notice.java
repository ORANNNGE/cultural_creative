/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.platform;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 公告Entity
 * @author orange
 * @version 2018-10-23
 */
public class Notice extends DataEntity<Notice> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String intro;		// 简介
	private String details;		// 详情
	
	public Notice() {
		super();
	}

	public Notice(String id){
		super(id);
	}

	@ExcelField(title="标题", align=2, sort=1)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="简介", align=2, sort=2)
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	@ExcelField(title="详情", align=2, sort=3)
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
}