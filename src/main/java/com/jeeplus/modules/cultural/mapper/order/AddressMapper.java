/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.cultural.mapper.order;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.cultural.entity.order.Address;

import java.util.List;

/**
 * 收货地址MAPPER接口
 * @author orange
 * @version 2018-10-17
 */
@MyBatisMapper
public interface AddressMapper extends BaseMapper<Address> {
    List<Address> getListByCustomerId(String customerId);
}