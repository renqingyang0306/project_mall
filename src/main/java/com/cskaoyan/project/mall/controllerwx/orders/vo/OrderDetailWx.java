package com.cskaoyan.project.mall.controllerwx.orders.vo;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/21
 * @time 21:36
 */
public class OrderDetailWx {

    List orderGoods;

    OrderInfo orderInfo;

    public OrderDetailWx() {
    }

    public OrderDetailWx(List orderGoods, OrderInfo orderInfo) {
        this.orderGoods = orderGoods;
        this.orderInfo = orderInfo;
    }

    public List getOrderGoods() {
        return orderGoods;
    }

    public void setOrderGoods(List orderGoods) {
        this.orderGoods = orderGoods;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

}
