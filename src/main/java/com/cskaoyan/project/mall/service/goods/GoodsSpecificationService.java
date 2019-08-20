package com.cskaoyan.project.mall.service.goods;

import com.cskaoyan.project.mall.domain.GoodsProduct;
import com.cskaoyan.project.mall.domain.GoodsSpecification;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/17
 * @time 17:53
 */
public interface GoodsSpecificationService {
    int insert(GoodsSpecification goodsSpecification);

    List<GoodsSpecification> queryByGoodsId(int id);

    int updateByPrimaryKey(GoodsSpecification goodsSpecification);
}
