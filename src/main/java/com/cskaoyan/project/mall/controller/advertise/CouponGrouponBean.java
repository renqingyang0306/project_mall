package com.cskaoyan.project.mall.controller.advertise;

import com.cskaoyan.project.mall.domain.Goods;
import com.cskaoyan.project.mall.domain.Groupon;
import com.cskaoyan.project.mall.domain.GrouponRules;

import java.util.List;

public class CouponGrouponBean {
    private Goods goods;
    private Groupon groupon;
    private GrouponRules rules;
    private List<Groupon> subGroupons;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Groupon getGroupon() {
        return groupon;
    }

    public void setGroupon(Groupon groupon) {
        this.groupon = groupon;
    }

    public GrouponRules getRules() {
        return rules;
    }

    public void setRules(GrouponRules rules) {
        this.rules = rules;
    }

    public List<Groupon> getSubGroupons() {
        return subGroupons;
    }

    public void setSubGroupons(List<Groupon> subGroupons) {
        this.subGroupons = subGroupons;
    }

    public CouponGrouponBean() {
    }

    public CouponGrouponBean(Goods goods, Groupon groupon, GrouponRules rules, List<Groupon> subGroupons) {
        this.goods = goods;
        this.groupon = groupon;
        this.rules = rules;
        this.subGroupons = subGroupons;
    }

    public static CouponGrouponBean createCouponGrouponBean(Goods goods, Groupon groupon, GrouponRules rules, List<Groupon> subGroupons){
        CouponGrouponBean couponGrouponBean = new CouponGrouponBean();
        couponGrouponBean.setGoods(goods);
        couponGrouponBean.setSubGroupons(subGroupons);
        couponGrouponBean.setGroupon(groupon);
        couponGrouponBean.setRules(rules);
        return couponGrouponBean;
    }
}
