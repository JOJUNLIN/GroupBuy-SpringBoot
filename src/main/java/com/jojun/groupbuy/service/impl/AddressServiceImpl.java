package com.jojun.groupbuy.service.impl;

import com.jojun.groupbuy.mapper.AddressMapper;
import com.jojun.groupbuy.pojo.AddressItem;
import com.jojun.groupbuy.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 新增站点
     * @param addressItem 包含站点信息的对象
     * @return 返回创建的站点对象 (包含生成的ID)
     */
    @Override
    @Transactional // 如果将来添加站点涉及多个数据库操作，需要保证原子性时使用
    public AddressItem addSite(AddressItem addressItem) {
        // 基本校验
        if (addressItem == null || addressItem.getAddress() == null || addressItem.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("站点地址不能为空");
        }
        // 如果前端没有传递 group_num，设置默认值
        if (addressItem.getGroup_num() == null) {
            addressItem.setGroup_num(0); // 默认为0
        }

        // 检查站点名称是否已存在
        // 需要先在 AddressMapper 中添加一个 findByAddressName(String addressName) 的方法
        // List<AddressItem> existingSites = addressMapper.findByAddressName(addressItem.getAddress());
        // if (existingSites != null && !existingSites.isEmpty()) {
        //     throw new RuntimeException("站点名称 '" + addressItem.getAddress() + "' 已存在");
        // }

        int affectedRows = addressMapper.addAddress(addressItem);
        if (affectedRows > 0) {
            // 由于在Mapper中使用了 @Options(useGeneratedKeys = true, keyProperty = "id")
            // 此时 addressItem.getId() 已经包含了数据库生成的ID
            return addressItem;
        } else {
            // 理论上，如果没有其他数据库错误，这种情况不应该发生
            throw new RuntimeException("新增站点失败，数据库未返回影响行数");
        }
    }
}
