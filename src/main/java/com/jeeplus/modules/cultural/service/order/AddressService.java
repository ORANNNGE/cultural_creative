/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.order.Address;
import com.jeeplus.modules.cultural.mapper.order.AddressMapper;

/**
 * 收货地址Service
 * @author orange
 * @version 2018-10-12
 */
@Service
@Transactional(readOnly = true)
public class AddressService extends CrudService<AddressMapper, Address> {

	public Address get(String id) {
		return super.get(id);
	}
	
	public List<Address> findList(Address address) {
		return super.findList(address);
	}
	
	public Page<Address> findPage(Page<Address> page, Address address) {
		return super.findPage(page, address);
	}
	
	@Transactional(readOnly = false)
	public void save(Address address) {
		super.save(address);
	}
	
	@Transactional(readOnly = false)
	public void delete(Address address) {
		super.delete(address);
	}
	
}