/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.order.CoupletsPrice;
import com.jeeplus.modules.cultural.mapper.order.CoupletsPriceMapper;

/**
 * 成品楹联价格Service
 * @author orange
 * @version 2018-10-16
 */
@Service
@Transactional(readOnly = true)
public class CoupletsPriceService extends CrudService<CoupletsPriceMapper, CoupletsPrice> {

	public CoupletsPrice get(String id) {
		return super.get(id);
	}
	
	public List<CoupletsPrice> findList(CoupletsPrice coupletsPrice) {
		return super.findList(coupletsPrice);
	}
	
	public Page<CoupletsPrice> findPage(Page<CoupletsPrice> page, CoupletsPrice coupletsPrice) {
		return super.findPage(page, coupletsPrice);
	}
	
	@Transactional(readOnly = false)
	public void save(CoupletsPrice coupletsPrice) {
		super.save(coupletsPrice);
	}
	
	@Transactional(readOnly = false)
	public void delete(CoupletsPrice coupletsPrice) {
		super.delete(coupletsPrice);
	}
	
}