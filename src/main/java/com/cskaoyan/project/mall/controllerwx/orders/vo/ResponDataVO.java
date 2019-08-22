package com.cskaoyan.project.mall.controllerwx.orders.vo;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/21
 * @time 20:03
 */
public class ResponDataVO {

    private long total;
    private List data;
    private int pages;

    public ResponDataVO() {
    }

    public ResponDataVO(long total, List data, int pages) {
        this.total = total;
        this.data = data;
        this.pages = pages;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
