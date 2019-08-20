package com.cskaoyan.project.mall.utils;

import java.util.HashMap;
import java.util.Map;

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
    public static Object ok() {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", 0);
        obj.put("errmsg", "成功");
        return obj;
    }

    public static Object ok(Object data) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", 0);
        obj.put("errmsg", "成功");
        obj.put("data", data);
        return obj;
    }

    public static Object fail() {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", 1);
        obj.put("errmsg", "错误");
        return obj;
    }

    public static Object fail(int errno, String errmsg) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", errno);
        obj.put("errmsg", errmsg);
        return obj;
    }
    public static Object unlogin() {
        return fail(501, "请登录");
    }

    public static Object unauthz() {
        return fail(506, "无操作权限");
    }
    public static Object badArgument() {
        return fail(401, "参数不对");
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
