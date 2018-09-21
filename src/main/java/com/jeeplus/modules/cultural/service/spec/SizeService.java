/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.spec;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.spec.Size;
import com.jeeplus.modules.cultural.mapper.spec.SizeMapper;

/**
 * 尺寸Service
 * @author orange
 * @version 2018-09-05
 */
@Service
@Transactional(readOnly = true)
public class SizeService extends CrudService<SizeMapper, Size> {

	public Size get(String id) {
		return super.get(id);
	}
	
	public List<Size> findList(Size size) {
		return super.findList(size);
	}
	
	public Page<Size> findPage(Page<Size> page, Size size) {
		return super.findPage(page, size);
	}
	
	@Transactional(readOnly = false)
	public void save(Size size) {
		super.save(size);
	}
	
	@Transactional(readOnly = false)
	public void delete(Size size) {
		super.delete(size);
	}
	
}