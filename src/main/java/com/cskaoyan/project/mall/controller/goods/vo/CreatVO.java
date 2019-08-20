package com.cskaoyan.project.mall.controller.goods.vo;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/17
 * @time 16:16
 */
public class CreatVO {
    int errno;
    String errmsg;

    public CreatVO() {
    }

    public CreatVO(int errno, String errmsg) {
        this.errno = errno;
        this.errmsg = errmsg;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return "CreatVO{" +
                "errno=" + errno +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
