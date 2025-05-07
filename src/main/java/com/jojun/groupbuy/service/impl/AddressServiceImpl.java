package com.jojun.groupbuy.service.impl;

import com.jojun.groupbuy.mapper.AddressMapper;
import com.jojun.groupbuy.mapper.OrderMapper;
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

    @Autowired
    private OrderMapper orderMapper;

    // 定义待发货状态码常量，方便维护
    private static final int ORDER_STATE_TO_BE_SHIPPED = 2;

    /**
     * 获取用户地址列表 (站点列表)，并实时更新每个站点的拼团数 (待发货商品总数)
     * @return 更新了拼团数的地址列表
     */
    @Override
    public List<AddressItem> getAddressList() {
        // 1. 获取所有基础的站点信息
        List<AddressItem> addressList = addressMapper.findAddress();

        // 2. 遍历每个站点，查询并更新其 group_num (待发货商品总数)
        if (addressList != null && !addressList.isEmpty()) {
            for (AddressItem site : addressList) {
                if (site.getId() != null) {
                    // 查询该站点下，状态为“待发货”的订单商品总数
                    Integer groupNum = orderMapper.sumProductCountByAddressIdAndOrderState(
                            site.getId(),
                            ORDER_STATE_TO_BE_SHIPPED
                    );
                    site.setGroup_num(groupNum); // 更新 AddressItem 对象的 group_num
                } else {
                    site.setGroup_num(0); // 如果站点ID为空，默认拼团数为0
                }
            }
        }
        return addressList;
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

        addressItem.setGroup_num(0); // 默认为0

        // 检查站点名称是否已存在
        // 需要先在 AddressMapper 中添加一个 findByAddressName(String addressName) 的方法
        // List<AddressItem> existingSites = addressMapper.findByAddressName(addressItem.getAddress());
        // if (existingSites != null && !existingSites.isEmpty()) {
        //     throw new RuntimeException("站点名称 '" + addressItem.getAddress() + "' 已存在");
        // }

        int affectedRows = addressMapper.addAddress(addressItem);
        if (affectedRows > 0) {
            return addressItem;
        } else {
            // 理论上，如果没有其他数据库错误，这种情况不应该发生
            throw new RuntimeException("新增站点失败，数据库未返回影响行数");
        }
    }
}
