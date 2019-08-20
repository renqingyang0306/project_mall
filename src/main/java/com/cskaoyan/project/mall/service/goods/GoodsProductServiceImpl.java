package com.cskaoyan.project.mall.service.goods;

import com.cskaoyan.project.mall.domain.GoodsProduct;
import com.cskaoyan.project.mall.mapper.GoodsProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/17
 * @time 20:11
 */
@Service
public class GoodsProductServiceImpl implements GoodsProductService{
    @Autowired
    GoodsProductMapper goodsProductMapper;

    @Override
    public int insert(GoodsProduct goodsProduct) {
        return goodsProductMapper.insert(goodsProduct);
    }

    @Override
    public List<GoodsProduct> queryByGoodsId(int id) {
        List<GoodsProduct> goodsProducts = goodsProductMapper.queryByGoodsId(id);
        return goodsProducts;
    }

    @Override
    public int updateByPrimaryKey(GoodsProduct goodsProduct) {
        int i = goodsProductMapper.updateByPrimaryKey(goodsProduct);
        return i;
    }
}
