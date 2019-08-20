package com.cskaoyan.project.mall.service.mall.impl;

import com.cskaoyan.project.mall.domain.Order;
import com.cskaoyan.project.mall.domain.OrderExample;
import com.cskaoyan.project.mall.mapper.OrderMapper;
import com.cskaoyan.project.mall.service.mall.OrderService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/18 16:08
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Override
    public List<Order> queryPageOrderByExample(int page, int limit, String sort, String order) {
        PageHelper.startPage(page,limit);
        OrderExample orderExample = new OrderExample();
        orderExample.setOrderByClause(sort + " " + order);
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andIdIsNotNull();
        criteria.andDeletedEqualTo(false);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        return orders;
    }

    @Override
    public List<Order> queryPageOrderByExample(int page, int limit, String sort, String order, Integer userId, String orderSn, Short[] orderStatusArray) {
        PageHelper.startPage(page,limit);
        OrderExample orderExample = new OrderExample();
        orderExample.setOrderByClause(sort + " " + order);
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andIdIsNotNull();
        criteria.andDeletedEqualTo(false);
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (orderSn != null) {
            criteria.andOrderSnEqualTo(orderSn);
        }
        if (orderStatusArray != null) {
            for (Short aShort : orderStatusArray) {
                OrderExample.Criteria criteria1 = orderExample.createCriteria();
                orderExample.or(criteria1.andOrderStatusEqualTo(aShort));
            }
            //orderExample.or().andOrderStatusEqualTo((short) 2).andOrderStatusEqualTo((short) 1);

        }
        List<Order> orders = orderMapper.selectByExample(orderExample);
        return orders;
    }

    @Override
    public Order queryOrderById(Integer id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        return order;
    }

    @Override
    public int insertOrder(Order order) {
        int insert = orderMapper.insert(order);
        return insert;
    }

    @Override
    public int updateOrderById(Order order) {
        int update = orderMapper.updateByPrimaryKey(order);
        return update;
    }

    @Override
    public int deleteRealOrderById(Integer id) {
        int delete = orderMapper.deleteByPrimaryKey(id);
        return delete;
    }

    @Override
    public int deleteLogicOrderByDeleted(Order order) {
        order.setDeleted(true);
        int update = orderMapper.updateByPrimaryKey(order);
        return update;
    }
}
