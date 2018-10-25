/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.platform;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.platform.Notice;
import com.jeeplus.modules.cultural.mapper.platform.NoticeMapper;

/**
 * 公告Service
 * @author orange
 * @version 2018-10-23
 */
@Service
@Transactional(readOnly = true)
public class NoticeService extends CrudService<NoticeMapper, Notice> {

	public Notice get(String id) {
		return super.get(id);
	}
	
	public List<Notice> findList(Notice notice) {
		return super.findList(notice);
	}
	
	public Page<Notice> findPage(Page<Notice> page, Notice notice) {
		return super.findPage(page, notice);
	}
	
	@Transactional(readOnly = false)
	public void save(Notice notice) {
		super.save(notice);
	}
	
	@Transactional(readOnly = false)
	public void delete(Notice notice) {
		super.delete(notice);
	}
	
}