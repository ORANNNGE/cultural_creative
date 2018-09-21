/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.mapper.finished;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.cultural.entity.finished.Painting;

import java.util.List;

/**
 * 美术作品MAPPER接口
 * @author orange
 * @version 2018-09-13
 */
@MyBatisMapper
public interface PaintingMapper extends BaseMapper<Painting> {
	List<Painting> getNewest();
}