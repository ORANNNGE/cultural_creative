/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.platform;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.platform.LeaveMsg;
import com.jeeplus.modules.cultural.mapper.platform.LeaveMsgMapper;

/**
 * 留言Service
 * @author orange
 * @version 2018-11-07
 */
@Service
@Transactional(readOnly = true)
public class LeaveMsgService extends CrudService<LeaveMsgMapper, LeaveMsg> {

	public LeaveMsg get(String id) {
		return super.get(id);
	}
	
	public List<LeaveMsg> findList(LeaveMsg leaveMsg) {
		return super.findList(leaveMsg);
	}
	
	public Page<LeaveMsg> findPage(Page<LeaveMsg> page, LeaveMsg leaveMsg) {
		return super.findPage(page, leaveMsg);
	}
	
	@Transactional(readOnly = false)
	public void save(LeaveMsg leaveMsg) {
		super.save(leaveMsg);
	}
	
	@Transactional(readOnly = false)
	public void delete(LeaveMsg leaveMsg) {
		super.delete(leaveMsg);
	}
	
}