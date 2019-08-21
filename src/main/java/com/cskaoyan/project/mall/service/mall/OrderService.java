package com.cskaoyan.project.mall.service.mall;

import com.cskaoyan.project.mall.domain.Order;
import com.cskaoyan.project.mall.domain.OrderExample;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/18 16:05
 */
public interface OrderService {
    List<Order> queryPageOrderByExample(int page,int limit,String sort,String order);
    List<Order> queryPageOrderByExample(int page, int limit, String sort, String order, Integer userId, String orderSn, Short[] orderStatusArray);
    Order queryOrderById(Integer id);

    List<Order> queryAllOrder();
    List<Order> selectByExample(OrderExample example);

    int insertOrder(Order order);
    int updateOrderById(Order order);
    int deleteRealOrderById(Integer id);
    int deleteLogicOrderByDeleted(Order order);
}
