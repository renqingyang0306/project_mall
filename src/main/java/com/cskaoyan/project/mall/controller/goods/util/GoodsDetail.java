package com.cskaoyan.project.mall.controller.goods.util;

import com.cskaoyan.project.mall.domain.Goods;
import com.cskaoyan.project.mall.domain.GoodsAttribute;
import com.cskaoyan.project.mall.domain.GoodsProduct;
import com.cskaoyan.project.mall.domain.GoodsSpecification;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/19
 * @time 12:36
 */
public class GoodsDetail {
    List<GoodsAttribute> attributes;

    Integer[] categoryIds;

    Goods goods;

    List<GoodsProduct> products;

    List<GoodsSpecification> specifications;

    public GoodsDetail() {
    }

    public GoodsDetail(List<GoodsAttribute> attributes, Integer[] categoryIds, Goods goods, List<GoodsProduct> products, List<GoodsSpecification> specifications) {
        this.attributes = attributes;
        this.categoryIds = categoryIds;
        this.goods = goods;
        this.products = products;
        this.specifications = specifications;
    }

    public List<GoodsAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<GoodsAttribute> attributes) {
        this.attributes = attributes;
    }

    public Integer[] getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(Integer[] categoryIds) {
        this.categoryIds = categoryIds;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public List<GoodsProduct> getProducts() {
        return products;
    }

    public void setProducts(List<GoodsProduct> products) {
        this.products = products;
    }

    public List<GoodsSpecification> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(List<GoodsSpecification> specifications) {
        this.specifications = specifications;
    }
}
