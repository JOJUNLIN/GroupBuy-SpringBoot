package com.jojun.groupbuy.service.impl;

import com.jojun.groupbuy.mapper.ProductMapper;
import com.jojun.groupbuy.pojo.PageResult;
import com.jojun.groupbuy.pojo.Product;
import com.jojun.groupbuy.pojo.ProductQuery;
import com.jojun.groupbuy.pojo.Promotion;
import com.jojun.groupbuy.pojo.PromotionSubType;
import com.jojun.groupbuy.service.ProductService;
import com.jojun.groupbuy.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: PromotionServiceImpl
 * @author: JOJUN-CJL
 * @date: 2025/4/4
 * @Version: 1.0
 * @description: 推荐服务实现类
 */
@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private ProductService productService;

    // 推荐类型常量
    private static final String TYPE_SPECIAL = "special";    // 特惠推荐
    private static final String TYPE_HOT = "hot";            // 爆款推荐
    private static final String TYPE_ONE_STOP = "one-stop";  // 一站全买
    private static final String TYPE_NEW_GOODS = "new";      // 新鲜好物

    @Override
    public Promotion getSpecialPromotion(Integer page, Integer pageSize) {
        return getPromotionByType(TYPE_SPECIAL, page, pageSize);
    }

    @Override
    public Promotion getHotPromotion(Integer page, Integer pageSize) {
        return getPromotionByType(TYPE_HOT, page, pageSize);
    }

    @Override
    public Promotion getOneStopPromotion(Integer page, Integer pageSize) {
        return getPromotionByType(TYPE_ONE_STOP, page, pageSize);
    }

    @Override
    public Promotion getNewGoodsPromotion(Integer page, Integer pageSize) {
        return getPromotionByType(TYPE_NEW_GOODS, page, pageSize);
    }

    @Override
    public Promotion getPromotionByType(String type, Integer page, Integer pageSize) {
        // 如果页码未指定，默认为第1页
        if (page == null) {
            page = 1;
        }

        // 如果每页条数未指定，默认为10条
        if (pageSize == null) {
            pageSize = 10;
        }

        // 根据不同类型返回不同的推荐数据
        switch (type) {
            case TYPE_HOT:
                return buildHotPromotion(page, pageSize);
            case TYPE_ONE_STOP:
                return buildOneStopPromotion(page, pageSize);
            case TYPE_NEW_GOODS:
                return buildNewGoodsPromotion(page, pageSize);
            default:
                // 默认返回特惠推荐
                return buildSpecialPromotion(page, pageSize);
        }
    }

    /**
     * 构建特惠推荐数据
     * @param page 页码
     * @param pageSize 每页条数
     * @return 特惠推荐
     */
    private Promotion buildSpecialPromotion(Integer page, Integer pageSize) {
        // 构建特惠推荐数据
        Promotion promotion = new Promotion();
        promotion.setId("897682543");
        promotion.setTitle("特惠推荐");
        promotion.setBannerPicture("http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-05-20/658defeb-6df2-4fe3-9440-0be40ad9f65f.jpg");

        // 创建子分类列表
        List<PromotionSubType> subTypes = new ArrayList<>();

        // 添加"抢先尝鲜"子分类
        PromotionSubType firstSubType = new PromotionSubType();
        firstSubType.setId("912000341");
        firstSubType.setTitle("抢先尝鲜");

        // 获取商品数据
        ProductQuery query = new ProductQuery();
        query.setPage(page++);
        query.setPageSize(pageSize);
        query.setSortField("orderNum");
        query.setSortOrder(false); // 降序排列
        PageResult<Product> firstPageResult = productService.findByPage(query);
        firstSubType.setGoodsItems(firstPageResult);

        // 添加"新品预告"子分类
        PromotionSubType secondSubType = new PromotionSubType();
        secondSubType.setId("912000342");
        secondSubType.setTitle("新品预告");

        query.setPage(page);

        // 在实际业务中，可以针对不同子类型设置不同的查询条件
        // 这里简单使用相同的查询条件作为示例
        PageResult<Product> secondPageResult = productService.findByPage(query);
        secondSubType.setGoodsItems(secondPageResult);

        // 将子分类添加到列表
        subTypes.add(firstSubType);
        subTypes.add(secondSubType);

        // 设置子分类列表
        promotion.setSubTypes(subTypes);

        return promotion;
    }

    /**
     * 构建爆款推荐数据
     * @param page 页码
     * @param pageSize 每页条数
     * @return 爆款推荐
     */
    private Promotion buildHotPromotion(Integer page, Integer pageSize) {
        // 构建爆款推荐数据
        Promotion promotion = new Promotion();
        promotion.setId("896807072");
        promotion.setTitle("爆款推荐");
        promotion.setBannerPicture("http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-05-20/e5e01cf0-5cb0-4b7d-8c55-cae5b628affa.jpg");

        // 创建子分类列表
        List<PromotionSubType> subTypes = new ArrayList<>();

        // 基础查询对象
        ProductQuery query = new ProductQuery();
        query.setPage(page++);
        query.setPageSize(pageSize);
        query.setSortField("orderNum");
        query.setSortOrder(false); // 降序排列

        // 添加"24小时热榜"子分类
        PromotionSubType firstSubType = new PromotionSubType();
        firstSubType.setId("772990341");
        firstSubType.setTitle("24小时热榜");
        PageResult<Product> firstPageResult = productService.findByPage(query);
        firstSubType.setGoodsItems(firstPageResult);

        query.setPage(page++);

        // 添加"热销总榜"子分类
        PromotionSubType secondSubType = new PromotionSubType();
        secondSubType.setId("772990342");
        secondSubType.setTitle("热销总榜");
        PageResult<Product> secondPageResult = productService.findByPage(query);
        secondSubType.setGoodsItems(secondPageResult);

        query.setPage(page);

        // 添加"人气周榜"子分类
        PromotionSubType thirdSubType = new PromotionSubType();
        thirdSubType.setId("772990343");
        thirdSubType.setTitle("人气周榜");
        PageResult<Product> thirdPageResult = productService.findByPage(query);
        thirdSubType.setGoodsItems(thirdPageResult);

        // 将子分类添加到列表
        subTypes.add(firstSubType);
        subTypes.add(secondSubType);
        subTypes.add(thirdSubType);

        // 设置子分类列表
        promotion.setSubTypes(subTypes);

        return promotion;
    }

    /**
     * 构建一站全买推荐数据
     * @param page 页码
     * @param pageSize 每页条数
     * @return 一站全买推荐
     */
    private Promotion buildOneStopPromotion(Integer page, Integer pageSize) {
        // 构建一站全买推荐数据
        Promotion promotion = new Promotion();
        promotion.setId("625755297");
        promotion.setTitle("一站全买");
        promotion.setBannerPicture("http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-05-20/73145f12-4685-4223-a105-feea8e531cf6.jpg");

        // 创建子分类列表
        List<PromotionSubType> subTypes = new ArrayList<>();

        // 基础查询对象
        ProductQuery query = new ProductQuery();
        query.setPage(page++);
        query.setPageSize(pageSize);
        query.setSortField("orderNum");
        query.setSortOrder(false); // 降序排列

        // 添加"搞定熊孩子"子分类
        PromotionSubType firstSubType = new PromotionSubType();
        firstSubType.setId("872990341");
        firstSubType.setTitle("搞定熊孩子");
        PageResult<Product> firstPageResult = productService.findByPage(query);
        firstSubType.setGoodsItems(firstPageResult);

        query.setPage(page++);

        // 添加"家里不凌乱"子分类
        PromotionSubType secondSubType = new PromotionSubType();
        secondSubType.setId("872990342");
        secondSubType.setTitle("家里不凌乱");
        PageResult<Product> secondPageResult = productService.findByPage(query);
        secondSubType.setGoodsItems(secondPageResult);

        query.setPage(page);

        // 添加"让音质更出众"子分类
        PromotionSubType thirdSubType = new PromotionSubType();
        thirdSubType.setId("872990343");
        thirdSubType.setTitle("让音质更出众");
        PageResult<Product> thirdPageResult = productService.findByPage(query);
        thirdSubType.setGoodsItems(thirdPageResult);

        // 将子分类添加到列表
        subTypes.add(firstSubType);
        subTypes.add(secondSubType);
        subTypes.add(thirdSubType);

        // 设置子分类列表
        promotion.setSubTypes(subTypes);

        return promotion;
    }

    /**
     * 构建新鲜好物推荐数据
     * @param page 页码
     * @param pageSize 每页条数
     * @return 新鲜好物推荐
     */
    private Promotion buildNewGoodsPromotion(Integer page, Integer pageSize) {
        // 构建新鲜好物推荐数据
        Promotion promotion = new Promotion();
        promotion.setId("814787192");
        promotion.setTitle("新鲜好物");
        promotion.setBannerPicture("http://yjy-xiaotuxian-dev.oss-cn-beijing.aliyuncs.com/picture/2021-05-20/84abb5b1-8344-49ae-afc1-9cb932f3d593.jpg");

        // 创建子分类列表
        List<PromotionSubType> subTypes = new ArrayList<>();

        // 基础查询对象
        ProductQuery query = new ProductQuery();
        query.setPage(page++);
        query.setPageSize(pageSize);
        query.setSortField("orderNum");
        query.setSortOrder(false); // 降序排列

        // 添加"抢先尝鲜"子分类
        PromotionSubType firstSubType = new PromotionSubType();
        firstSubType.setId("642000231");
        firstSubType.setTitle("抢先尝鲜");
        PageResult<Product> firstPageResult = productService.findByPage(query);
        firstSubType.setGoodsItems(firstPageResult);

        query.setPage(page);

        // 添加"新品预告"子分类
        PromotionSubType secondSubType = new PromotionSubType();
        secondSubType.setId("642000232");
        secondSubType.setTitle("新品预告");
        PageResult<Product> secondPageResult = productService.findByPage(query);
        secondSubType.setGoodsItems(secondPageResult);

        // 将子分类添加到列表
        subTypes.add(firstSubType);
        subTypes.add(secondSubType);

        // 设置子分类列表
        promotion.setSubTypes(subTypes);

        return promotion;
    }
}
