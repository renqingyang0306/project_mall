package com.cskaoyan.project.mall.controllerwx.couponWx;

import com.alibaba.fastjson.JSONObject;
import com.cskaoyan.project.mall.domain.*;
import com.cskaoyan.project.mall.service.advertiseService.CouponService;
import com.cskaoyan.project.mall.service.advertiseService.CouponUserService;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class CouponControllerWx {
    @Autowired
    CouponUserService couponUserService;
    @Autowired
    CouponService couponService;

    @ResponseBody
    @RequestMapping("wx/coupon/mylist")
    public ResponseUtils<HashMap> mylist(Integer status, Integer page, Integer size){
        Subject subject = SecurityUtils.getSubject();
        subject = SecurityUtils.getSubject();
        //获取认证后的用户信息，通过Realm进行封装的
        User user = (User) subject.getPrincipal();
        int userId = user.getId();
        CouponUserExample couponUserExample = new CouponUserExample();
        CouponUserExample.Criteria criteria = couponUserExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andStatusEqualTo((short) status.intValue());
        List<CouponUser> coupons = couponUserService.selectByExample(couponUserExample);
        HashMap<String, Object> couponVoList = new HashMap<>();
        List<CouponVo> couponVos= new ArrayList<>();

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

    @ResponseBody
    @RequestMapping("wx/coupon/exchange")
   public ResponseUtils exchange(@RequestBody JSONObject jsonObject){
        String code = (String) jsonObject.get("code");
        //获得UserId以后，判断优惠券code是否有效，如果有效往coupon_user里塞
        Subject subject = SecurityUtils.getSubject();
        subject = SecurityUtils.getSubject();
        //获取认证后的用户信息，通过Realm进行封装的
        User user = (User) subject.getPrincipal();
        int userId = user.getId();

        CouponExample couponExample = new CouponExample();
        CouponExample.Criteria criteria = couponExample.createCriteria();
        criteria.andCodeEqualTo(code);

        Date date = new Date();

        criteria.andEndTimeGreaterThan(date);

        List<Coupon> coupons = couponService.selectByExample(couponExample);


        //看优惠券是否有了，
        if(coupons != null){
            int couponId = coupons.get(0).getId();
            CouponUser couponUser = new CouponUser();
            couponUser.setUserId(userId);
            couponUser.setStatus((short) 0);
            couponUser.setCouponId(couponId);
            couponUser.setAddTime(new Date());
            couponUser.setEndTime(coupons.get(0).getEndTime());
            couponUser.setStartTime(coupons.get(0).getStartTime());

            couponUserService.insert(couponUser);

        }else {
            ResponseUtils pageBeanResponseUtils = new ResponseUtils<>();
            pageBeanResponseUtils.setErrno(741);
            pageBeanResponseUtils.setErrmsg("优惠券领取失败");
            return pageBeanResponseUtils;
        }
        ResponseUtils pageBeanResponseUtils = new ResponseUtils<>();
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("优惠券领取成功");
        return pageBeanResponseUtils;
    }
}
