package com.cskaoyan.project.mall.controllerwx.orders.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/21
 * @time 19:22
 */
public class OrderResponseVO {

    /** 真实的价格*/
    BigDecimal actualPrice;
    /** 放入查询到的orders*/
    List goodsList;
    /** 操作选项*/
    HandleOption handleOption;
    /** 数据库里自增的id*/
    int id;
    /** 是否在团购中*/
    boolean isGroupin;
    /** 订单编号*/
    String orderSn;
    /** 订单状态*/
    String orderStatusText;

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public List getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List goodsList) {
        this.goodsList = goodsList;
    }

    public HandleOption getHandleOption() {
        return handleOption;
    }

    public void setHandleOption(HandleOption handleOption) {
        this.handleOption = handleOption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isGroupin() {
        return isGroupin;
    }

    public void setGroupin(boolean groupin) {
        isGroupin = groupin;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getOrderStatusText() {
        return orderStatusText;
    }

    public void setOrderStatusText(String orderStatusText) {
        this.orderStatusText = orderStatusText;
    }
}
