package com.cskaoyan.project.mall.controllerwx.orders.vo;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/22
 * @time 22:18
 */
public class RePayVO {
    String errmsg;
    int errno;

    public RePayVO() {
    }

    public RePayVO(String errmsg, int errno) {
        this.errmsg = errmsg;
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }
}
