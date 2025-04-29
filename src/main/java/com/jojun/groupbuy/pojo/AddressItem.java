package com.jojun.groupbuy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: AddressItem
 * @author: JOJUN-CJL
 * @date: 2025/4/7
 * @Version: 1.0
 * @description: 收货地址实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressItem {
    /** 收货地址 id */
    private Integer id;

    /** 详细地址 */
    private String address;

    /** 拼团数 */
    private Integer group_num;
}
