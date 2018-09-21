/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.spec;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.spec.Craft;
import com.jeeplus.modules.cultural.mapper.spec.CraftMapper;

/**
 * 制作工艺Service
 * @author orange
 * @version 2018-09-05
 */
@Service
@Transactional(readOnly = true)
public class CraftService extends CrudService<CraftMapper, Craft> {

	public Craft get(String id) {
		return super.get(id);
	}
	
	public List<Craft> findList(Craft craft) {
		return super.findList(craft);
	}
	
	public Page<Craft> findPage(Page<Craft> page, Craft craft) {
		return super.findPage(page, craft);
	}
	
	@Transactional(readOnly = false)
	public void save(Craft craft) {
		super.save(craft);
	}
	
	@Transactional(readOnly = false)
	public void delete(Craft craft) {
		super.delete(craft);
	}
	
}