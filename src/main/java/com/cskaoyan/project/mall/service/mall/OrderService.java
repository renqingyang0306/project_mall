package com.cskaoyan.project.mall.service.mall;

import com.cskaoyan.project.mall.controller.goods.vo.ResponseVO;
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

    ResponseVO queryAllOrdersByUid(Integer uid, int page, int size);

    ResponseVO queryUnpayOrdersByUid(Integer uid, int page, int size);

    ResponseVO queryUndeliveredOrdersByUid(Integer uid, int page, int size);

    ResponseVO queryUnreceivedOrdersByUid(Integer uid, int page, int size);

    ResponseVO queryUnevaluatedOrdersByUid(Integer uid, int page, int size);

    ResponseVO showOrderDetail(int orderId);

    int cancleOrderByOid(int oid);

    int refundByOid(Integer oid);

    int confrimByOid(Integer oid);

    int deleteByPrimaryKey(Integer oid);

    String generateOrderSn(Integer userId);
}
