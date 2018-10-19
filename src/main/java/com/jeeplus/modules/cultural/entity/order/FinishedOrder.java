/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.order;

import com.jeeplus.modules.cultural.entity.role.Customer;
import com.jeeplus.modules.cultural.entity.order.Address;
import com.jeeplus.modules.cultural.entity.role.Installer;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 其他成品订单Entity
 * @author orange
 * @version 2018-10-20
 */
public class FinishedOrder extends DataEntity<FinishedOrder> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 类型
	private String finishedId;		// 其他成品id
	private String finishedName;		// 名称
	private Double price;		// 价格
	private Integer num;		// 数量
	private Double totalPrice;		// 总价
	private Customer customer;		// 用户
	private Address address;		// 收货地址
	private Installer installer;		// 安装人员
	private String status;		// 订单状态
	
	public FinishedOrder() {
		super();
	}

	public FinishedOrder(String id){
		super(id);
	}

	@ExcelField(title="类型", dictType="finished_type", align=2, sort=1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="其他成品id", align=2, sort=2)
	public String getFinishedId() {
		return finishedId;
	}

	public void setFinishedId(String finishedId) {
		this.finishedId = finishedId;
	}
	
	@ExcelField(title="名称", align=2, sort=3)
	public String getFinishedName() {
		return finishedName;
	}

	public void setFinishedName(String finishedName) {
		this.finishedName = finishedName;
	}
	
	@ExcelField(title="价格", align=2, sort=4)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@ExcelField(title="数量", align=2, sort=5)
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@ExcelField(title="总价", align=2, sort=6)
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@ExcelField(title="用户", fieldType=Customer.class, value="customer.nickname", align=2, sort=7)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@ExcelField(title="收货地址", fieldType=Address.class, value="address.district", align=2, sort=8)
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	@ExcelField(title="安装人员", fieldType=Installer.class, value="installer.name", align=2, sort=9)
	public Installer getInstaller() {
		return installer;
	}

	public void setInstaller(Installer installer) {
		this.installer = installer;
	}
	
	@ExcelField(title="订单状态", dictType="order_status", align=2, sort=10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}