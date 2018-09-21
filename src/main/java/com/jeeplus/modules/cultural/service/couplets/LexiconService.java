/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.couplets;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.couplets.Lexicon;
import com.jeeplus.modules.cultural.mapper.couplets.LexiconMapper;

/**
 * 词库Service
 * @author orange
 * @version 2018-09-17
 */
@Service
@Transactional(readOnly = true)
public class LexiconService extends CrudService<LexiconMapper, Lexicon> {

	public Lexicon get(String id) {
		return super.get(id);
	}
	
	public List<Lexicon> findList(Lexicon lexicon) {
		List<Lexicon> datas = super.findList(lexicon);
		for (Lexicon data : datas) {
			data.setPicture(data.getPicture().replace("|",""));
		}
		return datas;
	}
	
	public Page<Lexicon> findPage(Page<Lexicon> page, Lexicon lexicon) {
		return super.findPage(page, lexicon);
	}
	
	@Transactional(readOnly = false)
	public void save(Lexicon lexicon) {
		super.save(lexicon);
	}
	
	@Transactional(readOnly = false)
	public void delete(Lexicon lexicon) {
		super.delete(lexicon);
	}
	
}