package com.cskaoyan.project.mall.controller.advertise;

import com.alibaba.fastjson.JSONObject;
import com.cskaoyan.project.mall.domain.*;
import com.cskaoyan.project.mall.service.advertiseService.CouponService;
import com.cskaoyan.project.mall.service.advertiseService.CouponUserService;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.RedisUtil;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class CouponController {

@Autowired
    CouponService couponService;
@Autowired
    CouponUserService couponUserService;

 @ResponseBody
 @RequestMapping("admin/coupon/list")
    public ResponseUtils<PageBean>  list( int page,
                                          int limit,
                                          String sort,
                                          String order,
                                          Integer status,
                                          String name,
                                          Integer type){
  String adCacheListName = "adList" + page + limit;
  String adCacheListTotal = "adListNumber";
  List<Coupon> ads = null;
  long total = 0;
  /*if (redisUtil.get(adCacheListName) != null) {
       String  s = (String) redisUtil.get(adCacheListName);
       Json
      System.out.println(o);
      total =  longValue(redisUtil.get(adCacheListTotal));
      System.out.println("redis");
  } else {*/
     CouponExample couponExample = new CouponExample();
     CouponExample.Criteria criteria = couponExample.createCriteria();
     if(!StringUtils.isEmpty(name)){
         criteria.andNameLike("%"+name+"%");
     }
     if(!StringUtils.isEmpty(status)){
         criteria.andStatusEqualTo((short) status.intValue());
     }
     if(!StringUtils.isEmpty(type)){
         criteria.andTypeEqualTo((short) type.intValue());
     }
     couponExample.setOrderByClause(sort + " " + order);
   PageHelper.startPage(page, limit);
   ads = couponService.selectByExample(couponExample);
    /*  JSONArray.(ads);
      //查到以后缓存到redis里
   redisUtil.set(adCacheListName, ads);*/
   PageInfo pageInfo = new PageInfo(ads);
   total = pageInfo.getTotal();
   //查到以后缓存到redis里
  /* redisUtil.set(adCacheListTotal, total);*/
 /* }*/
      PageBean<Coupon> adPageBean = new PageBean<>();
     adPageBean.setItems(ads);
     adPageBean.setTotal(total);
     ResponseUtils<PageBean> pageBeanResponseUtils = new ResponseUtils<PageBean>();
     pageBeanResponseUtils.setData(adPageBean);
     pageBeanResponseUtils.setErrno(0);
     pageBeanResponseUtils.setErrmsg("成功");
     return pageBeanResponseUtils;
 }


    @ResponseBody
    @RequestMapping("admin/coupon/create")
    public ResponseUtils<Coupon>  create(@RequestBody JSONObject jsonObject){
        //手动把json格式中与Coupon冲突的属性值修改正确
        jsonObject.put("goodsValue","[]");




        //FastJson重新把修改格式过的Json解析Coupon对象
        Coupon coupon = jsonObject.toJavaObject(Coupon.class);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        coupon.setAddTime(CreateDate.createDate());
        couponService.insert(coupon);
        ResponseUtils<Coupon> pageBeanResponseUtils = new ResponseUtils<Coupon>();
        pageBeanResponseUtils.setData(coupon);
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }
    @ResponseBody
    @RequestMapping("admin/coupon/delete")
    public ResponseUtils<Coupon>  delete(@RequestBody Coupon coupon){

        couponService.deleteByPrimaryKey(coupon.getId());
        ResponseUtils pageBeanResponseUtils = new ResponseUtils<>();
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }
    @ResponseBody
    @RequestMapping("admin/coupon/update")
    public ResponseUtils<Coupon>  update(@RequestBody Coupon coupon){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        coupon.setAddTime(CreateDate.createDate());
        couponService.updateByPrimaryKey(coupon);
        ResponseUtils<Coupon> pageBeanResponseUtils = new ResponseUtils<Coupon>();
        pageBeanResponseUtils.setData(coupon);
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }

    @ResponseBody
    @RequestMapping("admin/coupon/read")
    public ResponseUtils<Coupon>  delete(int id){
        Coupon coupon = couponService.selectByPrimaryKey(id);
        ResponseUtils pageBeanResponseUtils = new ResponseUtils<>();
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        pageBeanResponseUtils.setData(coupon);
        return pageBeanResponseUtils;
    }

    @ResponseBody
    @RequestMapping("admin/coupon/listuser")
    public ResponseUtils<PageBean>  listuser( int page,
                                          int limit,
                                          String sort,
                                          String order,
                                          Integer couponId,
                                              Integer status,
                                              Integer userId){
        String adCacheListName = "CouponList" +couponId+page + limit;
        String adCacheListTotal = "adListNumber"+couponId;
        List<CouponUser> ads = null;
        long total = 0;
            CouponUserExample couponExample = new CouponUserExample();
        CouponUserExample.Criteria criteria = couponExample.createCriteria();
        /*criteria.andIdEqualTo(couponId);*/
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo((short) status.intValue());
        }
        couponExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        ads = couponUserService.selectByExample(couponExample);
        PageInfo pageInfo = new PageInfo(ads);
        total = pageInfo.getTotal();
        PageBean<CouponUser> adPageBean = new PageBean<>();
        adPageBean.setItems(ads);
        adPageBean.setTotal(total);
        ResponseUtils<PageBean> pageBeanResponseUtils = new ResponseUtils<PageBean>();
        pageBeanResponseUtils.setData(adPageBean);
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }
}


