/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.platform;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 客服Entity
 * @author orange
 * @version 2018-11-07
 */
public class CustomerServ extends DataEntity<CustomerServ> {
	
	private static final long serialVersionUID = 1L;
	private String qq;		// qq
	private String wechat;		// 微信
	private String tel;		// 联系电话
	
	public CustomerServ() {
		super();
	}

	public CustomerServ(String id){
		super(id);
	}

	@ExcelField(title="qq", align=2, sort=1)
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	@ExcelField(title="微信", align=2, sort=2)
	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	
	@ExcelField(title="联系电话", align=2, sort=3)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
}