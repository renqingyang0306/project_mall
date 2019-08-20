package com.cskaoyan.project.mall.service.goods;

import com.cskaoyan.project.mall.domain.GoodsAttribute;
import com.cskaoyan.project.mall.mapper.GoodsAttributeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/17
 * @time 20:10
 */
@Service
public class GoodsAttributeServiceImpl implements GoodsAttributeService{
    @Autowired
    GoodsAttributeMapper goodsAttributeMapper;
    @Override
    public int insert(GoodsAttribute goodsAttribute) {
        return goodsAttributeMapper.insert(goodsAttribute);
    }

    @Override
    public List<GoodsAttribute> queryByGoodsId(int id) {
        List<GoodsAttribute> goodsAttributes = goodsAttributeMapper.queryByGoodsId(id);

        return goodsAttributes;
    }

    @Override
    public int updateByPrimaryKey(GoodsAttribute goodsAttribute) {
        int i = goodsAttributeMapper.updateByPrimaryKey(goodsAttribute);
        return i;
    }
}
