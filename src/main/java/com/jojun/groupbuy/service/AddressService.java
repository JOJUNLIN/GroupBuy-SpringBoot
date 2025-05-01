package com.jojun.groupbuy.service;

import com.jojun.groupbuy.pojo.AddressItem;

import java.util.List;

/**
 * @interfaceName: AddressService
 * @author: JOJUN-CJL
 * @date: 2025/4/24
 * @Version: 1.0
 * @description: 地址服务接口
 */
public interface AddressService {

    /**
     * 获取用户地址列表
     * @return 地址列表
     */
    List<AddressItem> getAddressList();

}
