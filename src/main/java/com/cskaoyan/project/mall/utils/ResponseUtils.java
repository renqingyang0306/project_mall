package com.cskaoyan.project.mall.utils;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/15 23:31
 */
//页面回显的json工具
public class ResponseUtils<T> {
    private Integer errno;
    private T data;
    private String errmsg;

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
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

    public ResponseUtils() {
    }

    public ResponseUtils(Integer errno, T data, String errmsg) {
        this.errno = errno;
        this.data = data;
        this.errmsg = errmsg;
    }
    public ResponseUtils(Integer errno, T data) {
        this.errno = errno;
        this.data = data;
    }
    public ResponseUtils(Integer errno, String errmsg) {
        this.errno = errno;
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return "ResponseJsonUtils{" +
                "errno=" + errno +
                ", data=" + data +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
