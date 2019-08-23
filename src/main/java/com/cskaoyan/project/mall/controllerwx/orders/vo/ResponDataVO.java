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

    Long count;

    List data;

    int totalPages;

    public ResponDataVO() {
    }

    public ResponDataVO(Long count, List data, int totalPages) {
        this.count = count;
        this.data = data;
        this.totalPages = totalPages;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
