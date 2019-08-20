package com.cskaoyan.project.mall.service.goods;

import com.cskaoyan.project.mall.domain.GoodsSpecification;
import com.cskaoyan.project.mall.mapper.GoodsSpecificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/17
 * @time 20:12
 */
@Service
public class GoodsSpecificationServiceImpl implements GoodsSpecificationService{
    @Autowired
    GoodsSpecificationMapper goodsSpecificationMapper;
    @Override
    public int insert(GoodsSpecification goodsSpecification) {
        return goodsSpecificationMapper.insert(goodsSpecification);
    }

    @Override
    public List<GoodsSpecification> queryByGoodsId(int id) {
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.queryByGoodsId(id);
        return goodsSpecifications;
    }

    @Override
    public int updateByPrimaryKey(GoodsSpecification goodsSpecification) {
        int i = goodsSpecificationMapper.updateByPrimaryKey(goodsSpecification);
        return i;
    }
}
