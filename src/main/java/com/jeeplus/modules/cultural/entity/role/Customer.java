/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.entity.role;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 用户Entity
 * @author orange
 * @version 2018-10-07
 */
public class Customer extends DataEntity<Customer> {
	
	private static final long serialVersionUID = 1L;
	private String phonenum;		// 手机号
	private String openid;		// 微信标识
	private String nickname;		// 昵称
	private String headimg;		// 头像
	
	public Customer() {
		super();
	}

	public Customer(String id){
		super(id);
	}

	@ExcelField(title="手机号", align=2, sort=1)
	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	
	@ExcelField(title="微信标识", align=2, sort=2)
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@ExcelField(title="昵称", align=2, sort=3)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@ExcelField(title="头像", align=2, sort=4)
	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	
}