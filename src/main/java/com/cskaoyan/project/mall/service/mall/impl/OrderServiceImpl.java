package com.cskaoyan.project.mall.service.mall.impl;

import com.cskaoyan.project.mall.controller.goods.vo.ResponseVO;
import com.cskaoyan.project.mall.controllerwx.orders.vo.*;
import com.cskaoyan.project.mall.domain.Order;
import com.cskaoyan.project.mall.domain.OrderExample;
import com.cskaoyan.project.mall.domain.OrderGoods;
import com.cskaoyan.project.mall.domain.OrderGoodsExample;
import com.cskaoyan.project.mall.mapper.OrderGoodsMapper;
import com.cskaoyan.project.mall.mapper.OrderMapper;
import com.cskaoyan.project.mall.service.mall.OrderGoodsService;
import com.cskaoyan.project.mall.service.mall.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/18 16:08
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderGoodsMapper orderGoodsMapper;

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
    public List<Order> queryAllOrder() {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        return orders;
    }

    @Override
    public List<Order> selectByExample(OrderExample example) {
        return orderMapper.selectByExample(example);
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

    @Override
    public ResponseVO queryAllOrdersByUid(Integer uid, int page, int size) {
        //根据uid查询所有的订单
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(uid);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        //放回合适的JavaBean
        ResponseVO responseVO = getResponseVOByOrderList(orders,page,size);
        return responseVO;
    }
    /*
     * description: 查询所有没有支付的订单
     * version: 1.0
     * date: 2019/8/21 20:10
     * author: du
     * @Param: [uid, page, size]
     * @return: com.cskaoyan.project.mall.controller.goods.vo.ResponseVO
     */
    @Override
    public ResponseVO queryUnpayOrdersByUid(Integer uid, int page, int size) {
        //根据uid查询所有的未支付订单
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(uid);
        //101代表未支付的订单
        short status = 101;
        criteria.andOrderStatusEqualTo(status);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        ResponseVO responseVO = getResponseVOByOrderList(orders,page,size);
        return responseVO;
    }

    /*
     * description: 查询未发货的订单
     * version: 1.0
     * date: 2019/8/21 20:35
     * author: du
     * @Param: [uid, page, size]
     * @return: com.cskaoyan.project.mall.controller.goods.vo.ResponseVO
     */
    @Override
    public ResponseVO queryUndeliveredOrdersByUid(Integer uid, int page, int size) {
        //根据uid查询所有的未发货订单
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(uid);
        //201代表未发货的订单
        short status = 201;
        criteria.andOrderStatusEqualTo(status);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        ResponseVO responseVO = getResponseVOByOrderList(orders,page,size);
        return responseVO;
    }

    /*
     * description: 查询未收货的订单
     * version: 1.0
     * date: 2019/8/21 20:35
     * author: du
     * @Param: [uid, page, size]
     * @return: com.cskaoyan.project.mall.controller.goods.vo.ResponseVO
     */
    @Override
    public ResponseVO queryUnreceivedOrdersByUid(Integer uid, int page, int size) {
        //查询所有的未收货订单
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(uid);
        //301代表未发货的订单
        short status = 301;
        criteria.andOrderStatusEqualTo(status);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        ResponseVO responseVO = getResponseVOByOrderList(orders,page,size);
        return responseVO;
    }

    /*
     * description: 查询未评价订单
     * version: 1.0
     * date: 2019/8/21 20:34
     * author: du
     * @Param: [uid, page, size]
     * @return: com.cskaoyan.project.mall.controller.goods.vo.ResponseVO
     */
    @Override
    public ResponseVO queryUnevaluatedOrdersByUid(Integer uid, int page, int size) {
        //查询所有的未收货订单
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(uid);
        //401、402代表未发货的订单
        /*short status = 301;
        criteria.andOrderStatusEqualTo(status);*/
        short status1 = 401;
        short status2 = 402;
        criteria.andOrderStatusBetween(status1,status2);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        ResponseVO responseVO = getResponseVOByOrderList(orders,page,size);
        return responseVO;
    }

    /*
     * description: 根据orderId查询出订单详情
     * version: 1.0
     * date: 2019/8/21 21:24
     * author: du
     * @Param: [orderId]
     * @return: com.cskaoyan.project.mall.controller.goods.vo.ResponseVO
     */
    @Override
    public ResponseVO showOrderDetail(int orderId) {
        OrderInfo orderInfo = new OrderInfo();
        OrderDetailWx orderDetailWx = new OrderDetailWx();
        //根据orderId查询orderGoods
        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        OrderGoodsExample.Criteria criteria = orderGoodsExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andOrderIdEqualTo(orderId);

        List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
        //把orderGoods放进去
        orderDetailWx.setOrderGoods(orderGoods);
        //封装orderInfo
        Order order = orderMapper.selectByPrimaryKey(orderId);
        Short orderStatus = order.getOrderStatus();
        HandleOption handleOption = new HandleOption();
        switch (orderStatus) {
            case 101:
                HandleOption.set101(handleOption);
                orderInfo.setOrderStatusText("未付款");
                break;
            case 102:
                HandleOption.set102(handleOption);
                orderInfo.setOrderStatusText("已取消");
                break;
            case 103:
                HandleOption.set103(handleOption);
                orderInfo.setOrderStatusText("已取消(系统)");
                break;
            case 201:
                HandleOption.set201(handleOption);
                orderInfo.setOrderStatusText("已付款");
                break;
            case 202:
                HandleOption.set202(handleOption);
                orderInfo.setOrderStatusText("已付款");
                break;
            case 301:
                HandleOption.set301(handleOption);
                orderInfo.setOrderStatusText("已发货");
                break;
            case 401:
                HandleOption.set401(handleOption);
                orderInfo.setOrderStatusText("已收货");
                break;
            case 402:
                HandleOption.set402(handleOption);
                orderInfo.setOrderStatusText("已收货");
                break;
        }
        orderInfo.setActualPrice(order.getActualPrice());
        orderInfo.setAddTime(order.getAddTime());
        orderInfo.setAddress(order.getAddress());
        orderInfo.setConsignee(order.getConsignee());
        orderInfo.setCouponPrice(order.getCouponPrice());
        orderInfo.setFreightPrice(order.getFreightPrice());
        orderInfo.setGoodsPrice(order.getGoodsPrice());
        orderInfo.setHandleOption(handleOption);
        orderInfo.setId(orderId);
        orderInfo.setMobile(order.getMobile());
        orderInfo.setOrderSn(order.getOrderSn());
        orderDetailWx.setOrderGoods(orderGoods);
        orderDetailWx.setOrderInfo(orderInfo);
        ResponseVO<OrderDetailWx> responseVO = new ResponseVO<>(orderDetailWx, "成功", 0);
        return responseVO;

    }

    /*
     * description: 对orderList进行封装
     * version: 1.0
     * date: 2019/8/21 20:36
     * author: du
     * @Param: [orders, page, size]
     * @return: com.cskaoyan.project.mall.controller.goods.vo.ResponseVO
     */
    //对取出来的orders进行封装
    public ResponseVO getResponseVOByOrderList(List<Order> orders,int page, int size){
        PageHelper.startPage(page, size);
        //传建存放东西的javabean
        List<OrderResponseVO> orderResponseList = new LinkedList<>();

        //从order中取出要用的数据
        for (Order order : orders) {
            OrderResponseVO orderResponse = new OrderResponseVO();
            HandleOption handleOption = new HandleOption();
            //得到订单的orderId
            Integer orderId = order.getId();
            Short orderStatus = order.getOrderStatus();
            switch (orderStatus) {
                case 101:
                    HandleOption.set101(handleOption);
                    orderResponse.setOrderStatusText("未付款");
                    break;
                case 102:
                    HandleOption.set102(handleOption);
                    orderResponse.setOrderStatusText("已取消");
                    break;
                case 103:
                    HandleOption.set103(handleOption);
                    orderResponse.setOrderStatusText("已取消(系统)");
                    break;
                case 201:
                    HandleOption.set201(handleOption);
                    orderResponse.setOrderStatusText("已付款");
                    break;
                case 202:
                    HandleOption.set202(handleOption);
                    orderResponse.setOrderStatusText("已取消(退款)");
                    break;
                case 301:
                    HandleOption.set301(handleOption);
                    orderResponse.setOrderStatusText("已发货");
                    break;
                case 401:
                    HandleOption.set401(handleOption);
                    orderResponse.setOrderStatusText("已收货");
                    break;
                case 402:
                    HandleOption.set402(handleOption);
                    orderResponse.setOrderStatusText("已收货");
                    break;
            }
            //注入id
            orderResponse.setId(order.getId());
            //注入actualPrice
            orderResponse.setActualPrice(order.getActualPrice());
            //注入orderSn
            orderResponse.setOrderSn(order.getOrderSn());
            //注入HandleOption
            orderResponse.setHandleOption(handleOption);
            //注入groupIN
            orderResponse.setGroupin(false);
            //根据orderId查出所有的OrderGoods,并注入goodsList
            OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
            OrderGoodsExample.Criteria criteria = orderGoodsExample.createCriteria();
            criteria.andDeletedEqualTo(false);
            criteria.andOrderIdEqualTo(orderId);
            List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);

            orderResponse.setGoodsList(orderGoods);
            orderResponseList.add(orderResponse);
        }
        //把orderResponseList塞到合适的bean
        PageInfo<OrderResponseVO> pageInfo = new PageInfo<>(orderResponseList);
        ResponDataVO responDataVO = new ResponDataVO(pageInfo.getTotal(), pageInfo.getList(), pageInfo.getPages());
        ResponseVO responseVO = new ResponseVO(responDataVO, "成功", 0);
        return responseVO;

    }
}
