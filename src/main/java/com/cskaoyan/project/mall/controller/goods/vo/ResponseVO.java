package com.cskaoyan.project.mall.controller.goods.vo;

import com.cskaoyan.project.mall.domain.Comment;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/16
 * @time 15:36
 */
public class ResponseVO<T> {
    T data;
    String errmsg;
    int errno;

    public ResponseVO() {
    }

    public ResponseVO(PageVO<Comment> pageVO, String 成功, String s) {
    }

    public ResponseVO(T data, String errmsg, int errno) {
        this.data = data;
        this.errmsg = errmsg;
        this.errno = errno;
    }

    public ResponseVO(String errmsg, int errno) {
        this.errmsg = errmsg;
        this.errno = errno;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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

    @Override
    public String toString() {
        return "ResponseVO{" +
                "data=" + data +
                ", errmsg='" + errmsg + '\'' +
                ", errno=" + errno +
                '}';
    }
}
