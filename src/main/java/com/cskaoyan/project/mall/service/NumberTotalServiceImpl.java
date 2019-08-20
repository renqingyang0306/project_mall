package com.cskaoyan.project.mall.service;

import com.cskaoyan.project.mall.domain.*;
import com.cskaoyan.project.mall.mapper.GoodsMapper;
import com.cskaoyan.project.mall.mapper.GoodsProductMapper;
import com.cskaoyan.project.mall.mapper.OrderMapper;
import com.cskaoyan.project.mall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/17 15:34
 */
@Service
public class NumberTotalServiceImpl implements NumberTotalService {

    @Resource
    GoodsProductMapper goodsProductMapper;
    @Resource
    GoodsMapper goodsMapper;
    @Resource
    OrderMapper orderMapper;
    @Resource
    UserMapper userMapper;

    @Override
    public long goodsTotal(){
        return goodsMapper.countByExample(new GoodsExample());
    }
    @Override
    public long orderTotal(){
        return orderMapper.countByExample(new OrderExample());
    }
    @Override
    public long  productTotal() {
        return goodsProductMapper.countByExample(new GoodsProductExample());
    }
    @Override
    public  long userTotal(){
        return userMapper.countByExample(new UserExample());
    }
}
