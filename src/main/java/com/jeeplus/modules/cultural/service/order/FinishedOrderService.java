/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.order.FinishedOrder;
import com.jeeplus.modules.cultural.mapper.order.FinishedOrderMapper;

/**
 * 其他成品订单Service
 * @author orange
 * @version 2018-12-04
 */
@Service
@Transactional(readOnly = true)
public class FinishedOrderService extends CrudService<FinishedOrderMapper, FinishedOrder> {

	public FinishedOrder get(String id) {
		return super.get(id);
	}
	
	public List<FinishedOrder> findList(FinishedOrder finishedOrder) {
		return super.findList(finishedOrder);
	}
	
	public Page<FinishedOrder> findPage(Page<FinishedOrder> page, FinishedOrder finishedOrder) {
		return super.findPage(page, finishedOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(FinishedOrder finishedOrder) {
		super.save(finishedOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(FinishedOrder finishedOrder) {
		super.delete(finishedOrder);
	}
	
}