/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.mapper.couplets;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.cultural.entity.couplets.Couplets;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 成品楹联MAPPER接口
 * @author orange
 * @version 2018-09-11
 */
@MyBatisMapper
public interface CoupletsMapper extends BaseMapper<Couplets> {
    List<Couplets> getCoupletsList(String type);
    List<Couplets> getRecommendCoupletsList(String type);
    List<Couplets> getNotRecommendCoupletsList(String type);
    int getCount(String type);
    List<Couplets> getIndexRecommendCoupletsList(String type);
}