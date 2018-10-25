/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.order.CoupletsOrder;
import com.jeeplus.modules.cultural.mapper.order.CoupletsOrderMapper;

/**
 * 成品楹联订单Service
 * @author orange
 * @version 2018-10-25
 */
@Service
@Transactional(readOnly = true)
public class CoupletsOrderService extends CrudService<CoupletsOrderMapper, CoupletsOrder> {

	public CoupletsOrder get(String id) {
		return super.get(id);
	}
	
	public List<CoupletsOrder> findList(CoupletsOrder coupletsOrder) {
		return super.findList(coupletsOrder);
	}
	
	public Page<CoupletsOrder> findPage(Page<CoupletsOrder> page, CoupletsOrder coupletsOrder) {
		return super.findPage(page, coupletsOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(CoupletsOrder coupletsOrder) {
		super.save(coupletsOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(CoupletsOrder coupletsOrder) {
		super.delete(coupletsOrder);
	}
	
}