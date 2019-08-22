package com.cskaoyan.project.mall.vo;

import com.cskaoyan.project.mall.domain.Goods;
import com.cskaoyan.project.mall.domain.Groupon;

import java.math.BigDecimal;

public class GrouponListBean {
    Goods goods;
    int groupon_member;
    BigDecimal groupon_price;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int getGroupon_member() {
        return groupon_member;
    }

    public void setGroupon_member(int groupon_member) {
        this.groupon_member = groupon_member;
    }

    public BigDecimal getGroupon_price() {
        return groupon_price;
    }

    public void setGroupon_price(BigDecimal groupon_price) {
        this.groupon_price = groupon_price;
    }
}
