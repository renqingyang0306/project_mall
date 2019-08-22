package com.cskaoyan.project.mall.controllerwx.orders.vo;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/21
 * @time 22:43
 */
public class FootprintVO {
    List footprintList;

    Long totalPages;

    public FootprintVO() {
    }

    public FootprintVO(List footprintList, Long totalPages) {
        this.footprintList = footprintList;
        this.totalPages = totalPages;
    }

    public List getFootprintList() {
        return footprintList;
    }

    public void setFootprintList(List footprintList) {
        this.footprintList = footprintList;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }
}
