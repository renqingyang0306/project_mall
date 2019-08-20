package com.cskaoyan.project.mall.controller.mall;

import com.cskaoyan.project.mall.domain.Order;
import com.cskaoyan.project.mall.service.mall.OrderService;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/18 15:36
 */
@Controller
public class OederController {
    @Autowired
    OrderService orderService;

    @RequestMapping("/admin/order/list")
    @ResponseBody
    public ResponseUtils queryAllOrders(int page, int limit, String sort, String order, Integer userId, String orderSn, Short[] orderStatusArray) {
        List<Order> orders = orderService.queryPageOrderByExample(page, limit, sort, order, userId, orderSn, orderStatusArray);

        PageInfo pageInfo = new PageInfo(orders);
        long total = pageInfo.getTotal();
        PageBean pageBean = new PageBean(orders,total);
        ResponseUtils responseUtils = new ResponseUtils();
        if (orders == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("服务端错误！");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功！");
            responseUtils.setData(pageBean);
        }
        return responseUtils;
    }

    @RequestMapping("/admin/order/detail")
    @ResponseBody
    public ResponseUtils queryOrderDetail(Integer id) {
        Order order = orderService.queryOrderById(id);

        ResponseUtils responseUtils = new ResponseUtils();
        if (order == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("服务端错误！");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功！");
            responseUtils.setData(order);
        }
        return responseUtils;
    }
}
