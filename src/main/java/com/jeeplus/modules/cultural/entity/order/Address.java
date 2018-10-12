/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.order;

import com.jeeplus.modules.cultural.entity.role.Customer;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 收货地址Entity
 * @author orange
 * @version 2018-10-12
 */
public class Address extends DataEntity<Address> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String phonenum;		// 手机号
	private String district;		// 地区
	private Customer customer;		// 用户
	private String details;		// 详细地址
	
	public Address() {
		super();
	}

	public Address(String id){
		super(id);
	}

	@ExcelField(title="姓名", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="手机号", align=2, sort=2)
	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	
	@ExcelField(title="地区", align=2, sort=3)
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	@ExcelField(title="用户", fieldType=Customer.class, value="customer.nickname", align=2, sort=4)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@ExcelField(title="详细地址", align=2, sort=5)
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
}