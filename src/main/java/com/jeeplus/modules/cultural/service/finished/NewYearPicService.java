/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.finished;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.finished.NewYearPic;
import com.jeeplus.modules.cultural.mapper.finished.NewYearPicMapper;

/**
 * 年画Service
 * @author orange
 * @version 2018-09-12
 */
@Service
@Transactional(readOnly = true)
public class NewYearPicService extends CrudService<NewYearPicMapper, NewYearPic> {
	@Autowired
	NewYearPicMapper mapper;
	public List<NewYearPic> getNewest(){
		List<NewYearPic> datas = mapper.getNewest();
		for (NewYearPic data : datas) {
			data.setPicture(data.getPicture().replace("|",""));
		}
		return datas;
	}
	public NewYearPic get(String id) {
		return super.get(id);
	}
	
	public List<NewYearPic> findList(NewYearPic newYearPic) {
		List<NewYearPic> datas = super.findList(newYearPic);
		for (NewYearPic data : datas) {
			data.setPicture(data.getPicture().replace("|",""));
		}
		return datas;
	}
	
	public Page<NewYearPic> findPage(Page<NewYearPic> page, NewYearPic newYearPic) {
		return super.findPage(page, newYearPic);
	}
	
	@Transactional(readOnly = false)
	public void save(NewYearPic newYearPic) {
		super.save(newYearPic);
	}
	
	@Transactional(readOnly = false)
	public void delete(NewYearPic newYearPic) {
		super.delete(newYearPic);
	}
	
}