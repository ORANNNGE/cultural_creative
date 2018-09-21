/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.spec;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.spec.Frame;
import com.jeeplus.modules.cultural.mapper.spec.FrameMapper;

/**
 * 楹联框Service
 * @author orange
 * @version 2018-09-05
 */
@Service
@Transactional(readOnly = true)
public class FrameService extends CrudService<FrameMapper, Frame> {

	public Frame get(String id) {
		return super.get(id);
	}
	
	public List<Frame> findList(Frame frame) {
		return super.findList(frame);
	}
	
	public Page<Frame> findPage(Page<Frame> page, Frame frame) {
		return super.findPage(page, frame);
	}
	
	@Transactional(readOnly = false)
	public void save(Frame frame) {
		super.save(frame);
	}
	
	@Transactional(readOnly = false)
	public void delete(Frame frame) {
		super.delete(frame);
	}
	
}