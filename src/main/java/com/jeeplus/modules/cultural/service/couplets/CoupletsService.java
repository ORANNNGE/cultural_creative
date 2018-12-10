/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.service.couplets;

import java.util.ArrayList;
import java.util.List;

import com.jeeplus.modules.cultural.utils.PageUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.cultural.entity.couplets.Couplets;
import com.jeeplus.modules.cultural.mapper.couplets.CoupletsMapper;

/**
 * 成品楹联Service
 * @author orange
 * @version 2018-09-11
 */
@Service
@Transactional(readOnly = true)
public class CoupletsService extends CrudService<CoupletsMapper, Couplets> {
	@Autowired
	CoupletsMapper mapper;
	private List<Couplets> handleData(List<Couplets> datas){
		for (Couplets data : datas) {
			data.setSize(null);
			data.setFrame(null);
			data.setCraft(null);
			data.setDetails("");
			data.setPicture(data.getPicture().replace("|",""));
		}
		return datas;
	}

	public List<Couplets> getCoupletsList(String type){
		List<Couplets> datas = handleData(mapper.getCoupletsList(type));
		return datas;
	}
	public List<Couplets> getRecommendCoupletsList(String type){
		List<Couplets> datas = handleData(mapper.getRecommendCoupletsList(type));
		return datas;
	}
	public List<Couplets> getNotRecommendCoupletsList(String type){
		List<Couplets> datas = handleData(mapper.getNotRecommendCoupletsList(type));
		return datas;
	}
	public List<Couplets> getIndexRecommendCoupletsList(String type){
		List<Couplets> datas = handleData(mapper.getIndexRecommendCoupletsList(type));
		return datas;
	}

	public int getCount(String type){
		return mapper.getCount(type);
	}

	public Couplets get(String id) {
		Couplets data = super.get(id);
		data.setPicture(data.getPicture().replace("|",""));
		return data;
	}
	
	public List<Couplets> findList(Couplets couplets) {
		return super.findList(couplets);
	}
	
	public Page<Couplets> findPage(Page<Couplets> page, Couplets couplets) {
		return super.findPage(page, couplets);
	}
	
	@Transactional(readOnly = false)
	public void save(Couplets couplets) {
		super.save(couplets);
	}
	
	@Transactional(readOnly = false)
	public void delete(Couplets couplets) {
		super.delete(couplets);
	}
	
}