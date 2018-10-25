/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.order.LexiconOrder;
import com.jeeplus.modules.cultural.mapper.order.LexiconOrderMapper;

/**
 * 楹联词库订单Service
 * @author orange
 * @version 2018-10-25
 */
@Service
@Transactional(readOnly = true)
public class LexiconOrderService extends CrudService<LexiconOrderMapper, LexiconOrder> {

	public LexiconOrder get(String id) {
		return super.get(id);
	}
	
	public List<LexiconOrder> findList(LexiconOrder lexiconOrder) {
		return super.findList(lexiconOrder);
	}
	
	public Page<LexiconOrder> findPage(Page<LexiconOrder> page, LexiconOrder lexiconOrder) {
		return super.findPage(page, lexiconOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(LexiconOrder lexiconOrder) {
		super.save(lexiconOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(LexiconOrder lexiconOrder) {
		super.delete(lexiconOrder);
	}
	
}