package com.cskaoyan.project.mall.service.goods;

import com.cskaoyan.project.mall.domain.GoodsAttribute;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/17
 * @time 17:52
 */
public interface GoodsAttributeService {

    int insert(GoodsAttribute goodsAttribute);

    List<GoodsAttribute> queryByGoodsId(int id);

    int updateByPrimaryKey(GoodsAttribute goodsAttribute);
}
