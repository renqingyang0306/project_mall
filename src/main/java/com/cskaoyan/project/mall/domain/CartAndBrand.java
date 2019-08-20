package com.cskaoyan.project.mall.domain;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/16
 * @time 20:05
 */
public class CartAndBrand {
    List<Item> brandList;

    List<Categorylist> categoryList;

    public CartAndBrand() {
    }

    public CartAndBrand(List<Item> brandList, List<Categorylist> categoryList) {
        this.brandList = brandList;
        this.categoryList = categoryList;
    }

    public List<Item> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Item> brandList) {
        this.brandList = brandList;
    }

    public List<Categorylist> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Categorylist> categoryList) {
        this.categoryList = categoryList;
    }
}
