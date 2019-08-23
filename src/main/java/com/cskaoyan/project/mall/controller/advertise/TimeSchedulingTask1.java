package com.cskaoyan.project.mall.controller.advertise;

import com.cskaoyan.project.mall.domain.CouponUser;
import com.cskaoyan.project.mall.domain.CouponUserExample;
import com.cskaoyan.project.mall.domain.Order;
import com.cskaoyan.project.mall.mapper.OrderMapper;
import com.cskaoyan.project.mall.service.advertiseService.CouponUserService;
import com.cskaoyan.project.mall.service.mall.OrderService;
import com.cskaoyan.project.mall.service.userService.CartService;
import com.cskaoyan.project.mall.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Component
@Configuration
@EnableScheduling
public class TimeSchedulingTask1 {
@Autowired
CouponUserService couponUserService;
@Autowired
CartService cartService;
@Autowired
    RedisUtil redisUtil;
@Autowired
    OrderMapper orderMapper;
    @Scheduled(cron = "0/3 * * * * ?")
    public void taskForCoupon(){

        double right = System.currentTimeMillis();
        double min = right - 2500;
        double max = min + 2500;
        Set<Object> orderId = redisUtil.rangeByScore("orderId", min, max);
        if(orderId.size()!= 0) {
            for (Object id : orderId) {
                Order order = orderMapper.selectByPrimaryKey(Integer.valueOf(id.toString()));
                order.setOrderStatus((short) 103);
                orderMapper.updateByPrimaryKey(order);
                System.out.println("订单状态更新了");
            }
        }
        System.out.println("定时任务启动,订单状态更新");
    }
}
