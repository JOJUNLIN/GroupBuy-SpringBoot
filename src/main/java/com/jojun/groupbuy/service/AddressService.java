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

    /**
     * 新增站点
     * @param addressItem 包含站点信息的对象
     * @return 返回创建的站点对象 (可能包含生成的ID)
     */
    AddressItem addSite(AddressItem addressItem);
}
