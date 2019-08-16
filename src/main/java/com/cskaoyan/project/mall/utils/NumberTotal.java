package com.cskaoyan.project.mall.utils;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/16 9:50
 */
public class NumberTotal {
    private int goodsTotal;

    private int orderTotal;

    private int productTotal;

    private int userTotal;

    public NumberTotal(int goodsTotal, int orderTotal, int productTotal, int userTotal) {
        this.goodsTotal = goodsTotal;
        this.orderTotal = orderTotal;
        this.productTotal = productTotal;
        this.userTotal = userTotal;
    }

    public NumberTotal() {
    }

    public int getGoodsTotal() {
        return goodsTotal;
    }

    public void setGoodsTotal(int goodsTotal) {
        this.goodsTotal = goodsTotal;
    }

    public int getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(int orderTotal) {
        this.orderTotal = orderTotal;
    }

    public int getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(int productTotal) {
        this.productTotal = productTotal;
    }

    public int getUserTotal() {
        return userTotal;
    }

    public void setUserTotal(int userTotal) {
        this.userTotal = userTotal;
    }
}
