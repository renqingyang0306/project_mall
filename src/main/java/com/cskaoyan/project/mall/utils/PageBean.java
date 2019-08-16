package com.cskaoyan.project.mall.utils;

import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/9 10:56
 */
public class PageBean<T> {

    private List<T> items;
    private  long total;

    public PageBean(List<T> items, long total) {
        this.items = items;
        this.total = total;
    }
    public PageBean() {
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
