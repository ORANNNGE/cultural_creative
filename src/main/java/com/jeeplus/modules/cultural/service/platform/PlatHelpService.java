/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.platform;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.platform.PlatHelp;
import com.jeeplus.modules.cultural.mapper.platform.PlatHelpMapper;

/**
 * 帮助Service
 * @author orange
 * @version 2018-11-07
 */
@Service
@Transactional(readOnly = true)
public class PlatHelpService extends CrudService<PlatHelpMapper, PlatHelp> {

	public PlatHelp get(String id) {
		return super.get(id);
	}
	
	public List<PlatHelp> findList(PlatHelp platHelp) {
		return super.findList(platHelp);
	}
	
	public Page<PlatHelp> findPage(Page<PlatHelp> page, PlatHelp platHelp) {
		return super.findPage(page, platHelp);
	}
	
	@Transactional(readOnly = false)
	public void save(PlatHelp platHelp) {
		super.save(platHelp);
	}
	
	@Transactional(readOnly = false)
	public void delete(PlatHelp platHelp) {
		super.delete(platHelp);
	}
	
}