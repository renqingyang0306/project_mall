package com.cskaoyan.project.mall.controller.advertise;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cskaoyan.project.mall.domain.Ad;
import com.cskaoyan.project.mall.domain.AdExample;
import com.cskaoyan.project.mall.service.advertiseService.AdvertiseService;
import com.cskaoyan.project.mall.service.advertiseService.impl.AdvertiseServiceImpl;
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

import java.text.SimpleDateFormat;
import java.util.List;

import static org.apache.ibatis.ognl.OgnlOps.longValue;

@Controller
public class AdvertiseController {

@Autowired
  AdvertiseService advertiseService;
@Autowired
RedisUtil redisUtil;
 @ResponseBody
 @RequestMapping("admin/ad/list")
    public ResponseUtils<PageBean>  list(int page,
                                          int limit,
                                         String sort,
                                          String order,
                                        String content,
                                        String name){
  String adCacheListName = "adList" + page + limit;
  String adCacheListTotal = "adListNumber";
  List<Ad> ads = null;
  long total = 0;
  /*if (redisUtil.get(adCacheListName) != null) {
       String  s = (String) redisUtil.get(adCacheListName);
       Json
      System.out.println(o);
      total =  longValue(redisUtil.get(adCacheListTotal));
      System.out.println("redis");
  } else {*/
   AdExample adExample = new AdExample();
     AdExample.Criteria criteria = adExample.createCriteria();
     if(!StringUtils.isEmpty(name)){
         criteria.andNameLike("%"+name+"%");
     }
     if(!StringUtils.isEmpty(content)){
         criteria.andContentLike("%"+content+"%");
     }
   adExample.setOrderByClause(sort + " " + order);
   PageHelper.startPage(page, limit);
   ads = advertiseService.selectByExample(adExample);
    /*  JSONArray.(ads);
      //查到以后缓存到redis里
   redisUtil.set(adCacheListName, ads);*/
   PageInfo pageInfo = new PageInfo(ads);
   total = pageInfo.getTotal();
   //查到以后缓存到redis里
  /* redisUtil.set(adCacheListTotal, total);*/
 /* }*/
      PageBean<Ad> adPageBean = new PageBean<>();
     adPageBean.setItems(ads);
     adPageBean.setTotal(total);
     ResponseUtils<PageBean> pageBeanResponseUtils = new ResponseUtils<PageBean>();
     pageBeanResponseUtils.setData(adPageBean);
     pageBeanResponseUtils.setErrno(0);
     pageBeanResponseUtils.setErrmsg("成功");
     return pageBeanResponseUtils;
 }


    @ResponseBody
    @RequestMapping("admin/ad/create")
    public ResponseUtils<Ad>  create(@RequestBody Ad ad){
     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     ad.setAddTime(CreateDate.createDate());
     advertiseService.insert(ad);

        ResponseUtils<Ad> pageBeanResponseUtils = new ResponseUtils<Ad>();
        pageBeanResponseUtils.setData(ad);
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }
    @ResponseBody
    @RequestMapping("admin/ad/delete")
    public ResponseUtils<Ad>  delete(@RequestBody Ad ad){
        advertiseService.deleteByPrimaryKey(ad.getId());
        ResponseUtils pageBeanResponseUtils = new ResponseUtils<>();
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }
    @ResponseBody
    @RequestMapping("admin/ad/update")
    public ResponseUtils<Ad>  update(@RequestBody Ad ad){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ad.setAddTime(CreateDate.createDate());
        advertiseService.updateByPrimaryKey(ad);
        ResponseUtils<Ad> pageBeanResponseUtils = new ResponseUtils<Ad>();
        pageBeanResponseUtils.setData(ad);
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }
   /* @ResponseBody
    @RequestMapping("admin/ad/list")
    public ResponseUtils<PageBean>  query(@RequestParam(value = "page") int page,
                                         @RequestParam(value = "limit") int limit,
                                         @RequestParam(value = "sort") String sort,
                                         @RequestParam(value = "order") String desc,
                                          @RequestParam(value = "content") String content,
                                          @RequestParam(value = "name") String name){
        String adCacheListName = "adList" + page + limit;
        String adCacheListTotal = "adListNumber";
        List<Ad> ads = null;
        long total = 0;

        AdExample adExample = new AdExample();
        AdExample.Criteria criteria = adExample.createCriteria();
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%"+name+"%");
        }
        if(!StringUtils.isEmpty(content)){
            criteria.andContentLike("%"+content+"%");
        }
        adExample.setOrderByClause(sort + " " + desc);
        PageHelper.startPage(page, limit);
        ads = advertiseService.selectByExample(adExample);

        PageInfo pageInfo = new PageInfo(ads);
        total = pageInfo.getTotal();
        PageBean<Ad> adPageBean = new PageBean<>();
        adPageBean.setItems(ads);
        adPageBean.setTotal(total);
        ResponseUtils<PageBean> pageBeanResponseUtils = new ResponseUtils<PageBean>();
        pageBeanResponseUtils.setData(adPageBean);
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;*/
    }


