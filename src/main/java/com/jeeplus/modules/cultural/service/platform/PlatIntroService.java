/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.platform;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.platform.PlatIntro;
import com.jeeplus.modules.cultural.mapper.platform.PlatIntroMapper;

/**
 * 平台简介Service
 * @author orange
 * @version 2018-10-24
 */
@Service
@Transactional(readOnly = true)
public class PlatIntroService extends CrudService<PlatIntroMapper, PlatIntro> {

	public PlatIntro get(String id) {
		return super.get(id);
	}
	
	public List<PlatIntro> findList(PlatIntro platIntro) {
		return super.findList(platIntro);
	}
	
	public Page<PlatIntro> findPage(Page<PlatIntro> page, PlatIntro platIntro) {
		return super.findPage(page, platIntro);
	}
	
	@Transactional(readOnly = false)
	public void save(PlatIntro platIntro) {
		super.save(platIntro);
	}
	
	@Transactional(readOnly = false)
	public void delete(PlatIntro platIntro) {
		super.delete(platIntro);
	}
	
}