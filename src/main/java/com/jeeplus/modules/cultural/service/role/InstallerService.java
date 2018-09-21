/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.role;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.role.Installer;
import com.jeeplus.modules.cultural.mapper.role.InstallerMapper;

/**
 * 安装人员Service
 * @author orange
 * @version 2018-09-06
 */
@Service
@Transactional(readOnly = true)
public class InstallerService extends CrudService<InstallerMapper, Installer> {

	public Installer get(String id) {
		return super.get(id);
	}
	
	public List<Installer> findList(Installer installer) {
		return super.findList(installer);
	}
	
	public Page<Installer> findPage(Page<Installer> page, Installer installer) {
		return super.findPage(page, installer);
	}
	
	@Transactional(readOnly = false)
	public void save(Installer installer) {
		super.save(installer);
	}
	
	@Transactional(readOnly = false)
	public void delete(Installer installer) {
		super.delete(installer);
	}
	
}