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
import com.jeeplus.modules.cultural.entity.finished.Calligraphy;
import com.jeeplus.modules.cultural.mapper.finished.CalligraphyMapper;

/**
 * 书法作品Service
 * @author orange
 * @version 2018-09-13
 */
@Service
@Transactional(readOnly = true)
public class CalligraphyService extends CrudService<CalligraphyMapper, Calligraphy> {
	@Autowired
	CalligraphyMapper mapper;
	public List<Calligraphy> getNewest(){
		List<Calligraphy> datas = mapper.getNewest();
		for (Calligraphy data : datas) {
			data.setPicture(data.getPicture().replace("|",""));
		}
		return datas;
	}
	public Calligraphy get(String id) {
		Calligraphy data = super.get(id);
		data.setPicture(data.getPicture().replace("|",""));
		return data;
	}
	
	public List<Calligraphy> findList(Calligraphy calligraphy) {
		List<Calligraphy> datas =super.findList(calligraphy);
		for (Calligraphy data : datas) {
			data.setPicture(data.getPicture().replace("|",""));
		}
		return datas;
	}
	
	public Page<Calligraphy> findPage(Page<Calligraphy> page, Calligraphy calligraphy) {
		return super.findPage(page, calligraphy);
	}
	
	@Transactional(readOnly = false)
	public void save(Calligraphy calligraphy) {
		super.save(calligraphy);
	}
	
	@Transactional(readOnly = false)
	public void delete(Calligraphy calligraphy) {
		super.delete(calligraphy);
	}
	
}