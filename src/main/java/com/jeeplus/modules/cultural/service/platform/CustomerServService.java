/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.platform;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.platform.CustomerServ;
import com.jeeplus.modules.cultural.mapper.platform.CustomerServMapper;

/**
 * 客服Service
 * @author orange
 * @version 2018-11-07
 */
@Service
@Transactional(readOnly = true)
public class CustomerServService extends CrudService<CustomerServMapper, CustomerServ> {

	public CustomerServ get(String id) {
		return super.get(id);
	}
	
	public List<CustomerServ> findList(CustomerServ customerServ) {
		return super.findList(customerServ);
	}
	
	public Page<CustomerServ> findPage(Page<CustomerServ> page, CustomerServ customerServ) {
		return super.findPage(page, customerServ);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerServ customerServ) {
		super.save(customerServ);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerServ customerServ) {
		super.delete(customerServ);
	}
	
}