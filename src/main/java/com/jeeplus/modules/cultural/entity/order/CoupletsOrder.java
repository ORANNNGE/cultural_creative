/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.order;

import com.jeeplus.modules.cultural.entity.role.Customer;
import com.jeeplus.modules.cultural.entity.couplets.Couplets;
import com.jeeplus.modules.cultural.entity.order.CoupletsPrice;
import com.jeeplus.modules.cultural.entity.order.Address;
import com.jeeplus.modules.cultural.entity.role.Installer;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 成品楹联订单Entity
 * @author orange
 * @version 2018-10-17
 */
public class CoupletsOrder extends DataEntity<CoupletsOrder> {
	
	private static final long serialVersionUID = 1L;
	private Customer customer;		// 用户
	private Couplets couplets;		// 成品楹联
	private CoupletsPrice coupletsPrice;		// 价格
	private Address address;		// 收货地址
	private Installer installer;		// 安装人员
	private Integer num;		// 数量
	private Double totalPrice;		// 总价
	private String status;		// 订单状态
	
	public CoupletsOrder() {
		super();
	}

	public CoupletsOrder(String id){
		super(id);
	}

	@ExcelField(title="用户", fieldType=Customer.class, value="customer.nickname", align=2, sort=1)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@ExcelField(title="成品楹联", fieldType=Couplets.class, value="couplets.name", align=2, sort=2)
	public Couplets getCouplets() {
		return couplets;
	}

	public void setCouplets(Couplets couplets) {
		this.couplets = couplets;
	}
	
	@ExcelField(title="价格", fieldType=CoupletsPrice.class, value="coupletsPrice.price", align=2, sort=3)
	public CoupletsPrice getCoupletsPrice() {
		return coupletsPrice;
	}

	public void setCoupletsPrice(CoupletsPrice coupletsPrice) {
		this.coupletsPrice = coupletsPrice;
	}
	
	@ExcelField(title="收货地址", fieldType=Address.class, value="address.district", align=2, sort=4)
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	@ExcelField(title="安装人员", fieldType=Installer.class, value="installer.name", align=2, sort=5)
	public Installer getInstaller() {
		return installer;
	}

	public void setInstaller(Installer installer) {
		this.installer = installer;
	}
	
	@ExcelField(title="数量", align=2, sort=6)
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@ExcelField(title="总价", align=2, sort=7)
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@ExcelField(title="订单状态", dictType="order_status", align=2, sort=8)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}