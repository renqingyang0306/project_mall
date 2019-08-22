package com.cskaoyan.project.mall.controller.controllerWx.couponWx;

import com.cskaoyan.project.mall.domain.Coupon;
import com.cskaoyan.project.mall.domain.CouponExample;
import com.cskaoyan.project.mall.domain.CouponUser;
import com.cskaoyan.project.mall.domain.CouponUserExample;
import com.cskaoyan.project.mall.service.advertiseService.CouponService;
import com.cskaoyan.project.mall.service.advertiseService.CouponUserService;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class CouponControllerWx {
    @Autowired
    CouponUserService couponUserService;
    @Autowired
    CouponService couponService;

    @RequestMapping("mylist")
    public ResponseUtils<HashMap> mylist(@RequestParam(defaultValue = "1") Integer userId, Integer status, Integer page, Integer size){

        CouponUserExample couponUserExample = new CouponUserExample();
        CouponUserExample.Criteria criteria = couponUserExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andStatusEqualTo((short) status.intValue());
        List<CouponUser> coupons = couponUserService.selectByExample(couponUserExample);
        HashMap<String, Object> couponVoList = new HashMap<>();
        List<CouponVo> couponVos= null;
        for (CouponUser couponUser: coupons) {
            Coupon coupon = couponService.selectByPrimaryKey(couponUser.getCouponId());
            CouponVo couponVo = new CouponVo();
            couponVo.setId(coupon.getId());
            couponVo.setName(coupon.getName());
            couponVo.setDesc(coupon.getDesc());
            couponVo.setTag(coupon.getTag());
            couponVo.setMin(coupon.getMin().toPlainString());
            couponVo.setDiscount(coupon.getDiscount().toPlainString());


            Instant instant = couponUser.getStartTime().toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();

            Instant instant1 = couponUser.getEndTime().toInstant();
            ZoneId zoneId1 = ZoneId.systemDefault();
            LocalDateTime localDateTime1 = instant.atZone(zoneId).toLocalDateTime();


            couponVo.setStartTime(localDateTime);
            couponVo.setEndTime(localDateTime1);
            couponVos.add(couponVo);
        }
        couponVoList.put("count",couponVos.size());
        couponVoList.put("data",couponVos);

        ResponseUtils pageBeanResponseUtils = new ResponseUtils<>();
        pageBeanResponseUtils.setData(couponVoList);
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;

    }
}
