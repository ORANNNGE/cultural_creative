/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.order.Combo;
import com.jeeplus.modules.cultural.mapper.order.ComboMapper;

/**
 * 套餐Service
 * @author orange
 * @version 2018-10-24
 */
@Service
@Transactional(readOnly = true)
public class ComboService extends CrudService<ComboMapper, Combo> {

	public Combo get(String id) {
		return super.get(id);
	}
	
	public List<Combo> findList(Combo combo) {
		return super.findList(combo);
	}
	
	public Page<Combo> findPage(Page<Combo> page, Combo combo) {
		return super.findPage(page, combo);
	}
	
	@Transactional(readOnly = false)
	public void save(Combo combo) {
		super.save(combo);
	}
	
	@Transactional(readOnly = false)
	public void delete(Combo combo) {
		super.delete(combo);
	}
	
}