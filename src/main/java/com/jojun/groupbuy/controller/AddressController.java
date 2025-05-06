package com.jojun.groupbuy.controller;

import com.jojun.groupbuy.pojo.AddressItem;
import com.jojun.groupbuy.pojo.Result;
import com.jojun.groupbuy.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className: AddressController
 * @author: JOJUN-CJL
 * @date: 2025/4/24
 * @Version: 1.0
 * @description: 地址控制器
 */
@RestController
@RequestMapping
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 获取用户地址列表(用户使用)
     * @return 统一响应结果包装的地址列表
     */
    @GetMapping("/user/address")
    public Result<List<AddressItem>> getAddressList() {
        List<AddressItem> addressList = addressService.getAddressList();
        return Result.success(addressList);
    }

    /**
     * 获取所有拼团站点列表 (供管理员使用)
     * @return 统一响应结果包装的站点列表
     */
    @GetMapping("/attendant/sites")
    public Result<List<AddressItem>> getAllSites() {
        List<AddressItem> siteList = addressService.getAddressList(); // 复用现有的service方法
        return Result.success(siteList);
    }

    /**
     * 新增拼团站点
     * @param addressItem 包含新站点信息的请求体 (JSON格式)
     * @return 统一响应结果，成功时可能包含创建的站点信息
     */
    // 使用 POST 方法创建新资源
    @PostMapping("/attendant/sites")
    public Result<AddressItem> addSite(@RequestBody AddressItem addressItem) { // @RequestBody 用于接收JSON数据
//        log.info("请求新增站点: {}", addressItem);
        try {
            // 可以在Controller层面做一些初步校验，更复杂的业务校验应在Service层
            if (addressItem.getAddress() == null || addressItem.getAddress().trim().isEmpty()) {
                return Result.fail("站点名称不能为空");
            }
            if (addressItem.getAddress().length() > 20) { // 示例：名称长度校验
                return Result.fail("站点名称过长，最多20字符");
            }
            // group_num 可以在Service层设默认值，或者前端必须传递

            AddressItem createdSite = addressService.addSite(addressItem);
//            log.info("站点新增成功: {}", createdSite);
            return Result.success(createdSite);
        } catch (IllegalArgumentException e) { // 捕获Service层抛出的参数校验异常
//            log.warn("新增站点失败，参数无效: {}", e.getMessage());
            return Result.fail(e.getMessage());
        } catch (RuntimeException e) { // 捕获Service层可能抛出的其他运行时异常（如“站点已存在”）
//            log.error("新增站点时发生运行时错误: {}", e.getMessage(), e); // 记录堆栈信息
            return Result.fail("新增站点失败: " + e.getMessage());
        } catch (Exception e) { // 捕获其他所有未预料的异常
//            log.error("新增站点时发生未知错误", e);
            return Result.fail("新增站点时发生未知错误，请联系管理员");
        }
    }
}
