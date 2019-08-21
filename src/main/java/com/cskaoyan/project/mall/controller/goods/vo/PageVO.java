package com.cskaoyan.project.mall.controller.goods.vo;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/16
 * @time 15:36
 */
public class PageVO<T> {
    Long total;

    List<T> items;

    public PageVO(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageVO() {
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PageVO{" +
                "total=" + total +
                ", items=" + items +
                '}';
    }
}
