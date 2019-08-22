package com.cskaoyan.project.mall.vo;

import com.cskaoyan.project.mall.domain.Goods;

import java.util.List;

public class GoodsListBean {
    List<Goods>  goodsList;
    String name;
    int id;

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
