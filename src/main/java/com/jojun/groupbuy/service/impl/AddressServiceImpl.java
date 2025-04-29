package com.jojun.groupbuy.service.impl;

import com.jojun.groupbuy.mapper.AddressMapper;
import com.jojun.groupbuy.pojo.AddressItem;
import com.jojun.groupbuy.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: AddressServiceImpl
 * @author: JOJUN-CJL
 * @date: 2025/4/24
 * @Version: 1.0
 * @description: 地址服务实现类
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    /**
     * 获取用户地址列表
     * @return 地址列表
     */
    @Override
    public List<AddressItem> getAddressList() {
        return addressMapper.findAddress();
    }
}
