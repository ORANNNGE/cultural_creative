/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.finished;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.finished.Decoration;
import com.jeeplus.modules.cultural.mapper.finished.DecorationMapper;

/**
 * 装饰品Service
 * @author orange
 * @version 2018-09-13
 */
@Service
@Transactional(readOnly = true)
public class DecorationService extends CrudService<DecorationMapper, Decoration> {
	@Autowired
	DecorationMapper mapper;
	public List<Decoration> getNewest(){
		List<Decoration> datas = mapper.getNewest();
		for (Decoration data : datas) {
			data.setPicture(data.getPicture().replace("|",""));
		}
		return datas;
	}
	public Decoration get(String id) {
		return super.get(id);
	}
	
	public List<Decoration> findList(Decoration decoration) {
		List<Decoration> datas = super.findList(decoration);
		for (Decoration data : datas) {
			data.setPicture(data.getPicture().replace("|",""));
		}
		return datas;
	}
	
	public Page<Decoration> findPage(Page<Decoration> page, Decoration decoration) {
		return super.findPage(page, decoration);
	}
	
	@Transactional(readOnly = false)
	public void save(Decoration decoration) {
		super.save(decoration);
	}
	
	@Transactional(readOnly = false)
	public void delete(Decoration decoration) {
		super.delete(decoration);
	}
	
}