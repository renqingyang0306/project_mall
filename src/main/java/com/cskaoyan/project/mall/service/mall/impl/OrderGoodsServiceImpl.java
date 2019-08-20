package com.cskaoyan.project.mall.service.mall.impl;

import com.cskaoyan.project.mall.domain.OrderGoods;
import com.cskaoyan.project.mall.domain.OrderGoodsExample;
import com.cskaoyan.project.mall.mapper.OrderGoodsMapper;
import com.cskaoyan.project.mall.service.mall.OrderGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/20 20:16
 */
@Service
public class OrderGoodsServiceImpl implements OrderGoodsService {
    @Autowired
    OrderGoodsMapper orderGoodsMapper;
    @Override
    public long countOrderGoodsServiceById() {
        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        orderGoodsExample.createCriteria().andIdIsNotNull();
        long count = orderGoodsMapper.countByExample(orderGoodsExample);
        return count;
    }

    @Override
    public int insert(OrderGoods orderGoods) {
        return orderGoodsMapper.insert(orderGoods);
    }

    @Override
    public List<OrderGoods> queryAllOrderGoods() {
        return orderGoodsMapper.selectByExample(new OrderGoodsExample());
    }

    @Override
    public List<OrderGoods> queryAllOrderGoodsByOrderId(Integer orderId) {
        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        orderGoodsExample.createCriteria().andOrderIdEqualTo(orderId);
        List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
        return orderGoods;
    }

    @Override
    public List<OrderGoods> selectByExample(OrderGoodsExample example) {
        return orderGoodsMapper.selectByExample(example);
    }

    @Override
    public OrderGoods selectOrderGoodsById(Integer id) {
        return orderGoodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateOrderGoodsById(OrderGoods orderGoods) {
        return orderGoodsMapper.updateByPrimaryKey(orderGoods);
    }

    @Override
    public int deleteRealOrderGoodsById(Integer id) {
        return orderGoodsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteLogicOrderGoodsByDeleted(OrderGoods orderGoods) {
        //逻辑删除
        orderGoods.setDeleted(true);
        return orderGoodsMapper.updateByPrimaryKey(orderGoods);
    }
}
