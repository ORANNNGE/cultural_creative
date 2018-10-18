/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.order.LexiconPrice;
import com.jeeplus.modules.cultural.mapper.order.LexiconPriceMapper;

/**
 * 楹联词库价格Service
 * @author orange
 * @version 2018-10-18
 */
@Service
@Transactional(readOnly = true)
public class LexiconPriceService extends CrudService<LexiconPriceMapper, LexiconPrice> {

	public LexiconPrice get(String id) {
		return super.get(id);
	}
	
	public List<LexiconPrice> findList(LexiconPrice lexiconPrice) {
		return super.findList(lexiconPrice);
	}
	
	public Page<LexiconPrice> findPage(Page<LexiconPrice> page, LexiconPrice lexiconPrice) {
		return super.findPage(page, lexiconPrice);
	}
	
	@Transactional(readOnly = false)
	public void save(LexiconPrice lexiconPrice) {
		super.save(lexiconPrice);
	}
	
	@Transactional(readOnly = false)
	public void delete(LexiconPrice lexiconPrice) {
		super.delete(lexiconPrice);
	}
	
}