package com.jojun.groupbuy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @className: Promotion
 * @author: JOJUN-CJL
 * @date: 2025/4/4
 * @Version: 1.0
 * @description: 推荐数据实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    private String id;             // 推荐ID
    private String bannerPicture;  // 横幅图片
    private String title;          // 标题
    private List<PromotionSubType> subTypes; // 子类型列表
}

