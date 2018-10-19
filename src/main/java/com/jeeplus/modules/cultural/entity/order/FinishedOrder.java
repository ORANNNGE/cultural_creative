/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.order;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 其他成品订单Entity
 * @author orange
 * @version 2018-10-19
 */
public class FinishedOrder extends DataEntity<FinishedOrder> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 类型
	private String finishedId;		// 其他成品id
	private String finishedName;		// 名称
	private Double price;		// 价格
	private Integer num;		// 数量
	private Double totalPrice;		// 总价
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
	
	@ExcelField(title="订单状态", dictType="order_status", align=2, sort=7)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}