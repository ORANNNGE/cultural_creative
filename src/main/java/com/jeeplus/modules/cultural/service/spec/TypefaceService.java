/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.spec;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.spec.Typeface;
import com.jeeplus.modules.cultural.mapper.spec.TypefaceMapper;

/**
 * 字体Service
 * @author orange
 * @version 2018-09-05
 */
@Service
@Transactional(readOnly = true)
public class TypefaceService extends CrudService<TypefaceMapper, Typeface> {

	public Typeface get(String id) {
		return super.get(id);
	}
	
	public List<Typeface> findList(Typeface typeface) {
		return super.findList(typeface);
	}
	
	public Page<Typeface> findPage(Page<Typeface> page, Typeface typeface) {
		return super.findPage(page, typeface);
	}
	
	@Transactional(readOnly = false)
	public void save(Typeface typeface) {
		super.save(typeface);
	}
	
	@Transactional(readOnly = false)
	public void delete(Typeface typeface) {
		super.delete(typeface);
	}
	
}