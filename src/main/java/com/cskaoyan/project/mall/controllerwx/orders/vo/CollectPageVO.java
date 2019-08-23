package com.cskaoyan.project.mall.controllerwx.orders.vo;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/22
 * @time 14:55
 */
public class CollectPageVO<T> {

    Long totalPages;

    List<T> collectList;

    public CollectPageVO() {
    }

    public CollectPageVO(Long totalPages, List<T> collectList) {
        this.totalPages = totalPages;
        this.collectList = collectList;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getCollectList() {
        return collectList;
    }

    public void setCollectList(List<T> collectList) {
        this.collectList = collectList;
    }
}
