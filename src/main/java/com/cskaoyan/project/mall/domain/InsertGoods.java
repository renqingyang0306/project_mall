package com.cskaoyan.project.mall.domain;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/17
 * @time 16:19
 */
public class InsertGoods {

    Goods goods;
    List<GoodsAttribute> goodsAttributes;
    List<GoodsProduct> goodsProducts;
    List<GoodsSpecification> goodsSpecifications;


    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public List<GoodsAttribute> getGoodsAttributes() {
        return goodsAttributes;
    }

    public void setAttributes(List<GoodsAttribute> goodsAttributes) {
        this.goodsAttributes = goodsAttributes;
    }

    public List<GoodsProduct> getGoodsProducts() {
        return goodsProducts;
    }

    public void setProducts(List<GoodsProduct> goodsProducts) {
        this.goodsProducts = goodsProducts;
    }

    public List<GoodsSpecification> getGoodsSpecifications() {
        return goodsSpecifications;
    }

    public void setSpecifications(List<GoodsSpecification> goodsSpecifications) {
        this.goodsSpecifications = goodsSpecifications;
    }
}
