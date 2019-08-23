package com.cskaoyan.project.mall.controller.advertise;

import com.cskaoyan.project.mall.domain.CouponExample;
import com.cskaoyan.project.mall.domain.CouponUser;
import com.cskaoyan.project.mall.domain.CouponUserExample;
import com.cskaoyan.project.mall.service.advertiseService.CouponService;
import com.cskaoyan.project.mall.service.advertiseService.CouponUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Configuration
@EnableScheduling
public class TimeSchedulingTask {
@Autowired
CouponUserService couponUserService;
    @Scheduled(cron = "0 0/1 * * * ?")
    public void taskForCoupon(){
        //每隔1小时分钟刷新，是否有过期的优惠券
        Date dates = new Date();
        CouponUserExample couponExample = new CouponUserExample();
        CouponUserExample.Criteria criteria = couponExample.createCriteria();
        criteria.andStatusEqualTo((short) 0);
        //查询出没有使用的优惠券
        List<CouponUser> couponUsers = couponUserService.selectByExample(couponExample);
        int count = 0;
        for (CouponUser couponUser: couponUsers) {
            Date date = new Date();
            long time = date.getTime();
            if (couponUser.getEndTime() != null) {
                long endTime = couponUser.getEndTime().getTime();
                //说明过期了
                if (time >= endTime) {
                    //该条优惠券信息数据库变过期
                    couponUser.setStatus((short) 2);
                    couponUser.setUpdateTime(new Date());
                    couponUserService.updateByPrimaryKey(couponUser);
                    count ++;
                }
            }
        }
        System.out.println("定时任务启动"+dates+" 次数 "+count);
    }
}
