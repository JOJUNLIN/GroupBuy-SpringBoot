package com.jojun.groupbuy.controller;

import com.jojun.groupbuy.pojo.AddressItem;
import com.jojun.groupbuy.pojo.Result;
import com.jojun.groupbuy.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @className: AddressController
 * @author: JOJUN-CJL
 * @date: 2025/4/24
 * @Version: 1.0
 * @description: 地址控制器
 */
@RestController
@RequestMapping("/user")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 获取用户地址列表
     * @return 统一响应结果包装的地址列表
     */
    @GetMapping("/address")
    public Result<List<AddressItem>> getAddressList() {
        List<AddressItem> addressList = addressService.getAddressList();
        return Result.success(addressList);
    }
}
