package com.cskaoyan.project.mall.service.goods;

import com.cskaoyan.project.mall.domain.GoodsAttribute;
import com.cskaoyan.project.mall.domain.GoodsProduct;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/17
 * @time 17:53
 */
public interface GoodsProductService {
    int insert(GoodsProduct goodsProduct);

    List<GoodsProduct> queryByGoodsId(int id);

    int updateByPrimaryKey(GoodsProduct goodsProduct);

    GoodsProduct queryGoodsProductById(Integer id);
}
