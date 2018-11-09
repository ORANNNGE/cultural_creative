/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.platform;

import com.jeeplus.modules.cultural.entity.role.Customer;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 留言Entity
 * @author orange
 * @version 2018-11-07
 */
public class LeaveMsg extends DataEntity<LeaveMsg> {
	
	private static final long serialVersionUID = 1L;
	private Customer customer;		// 用户
	private String content;		// 内容
	
	public LeaveMsg() {
		super();
	}

	public LeaveMsg(String id){
		super(id);
	}

	@ExcelField(title="用户", fieldType=Customer.class, value="customer.nickname", align=2, sort=1)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@ExcelField(title="内容", align=2, sort=2)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}