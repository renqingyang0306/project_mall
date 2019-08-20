package com.cskaoyan.project.mall.controller.advertise;

import com.alibaba.fastjson.JSONObject;
import com.cskaoyan.project.mall.domain.Ad;
import com.cskaoyan.project.mall.domain.Topic;
import com.cskaoyan.project.mall.domain.TopicExample;
import com.cskaoyan.project.mall.service.advertiseService.TopicService;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.List;
@Controller
public class TopicController {
    @Autowired
    TopicService topicService;
    @ResponseBody
    @RequestMapping("admin/topic/list")
    public ResponseUtils<PageBean> list(int page,
                                        int limit,
                                        String sort,
                                        String order,
                                        String title,
                                        String subtitle){
        String adCacheListName = "adList" + page + limit;
        String adCacheListTotal = "adListNumber";
        List<Topic> ads = null;
        long total = 0;
  /*if (redisUtil.get(adCacheListName) != null) {
       String  s = (String) redisUtil.get(adCacheListName);
       Json
      System.out.println(o);
      total =  longValue(redisUtil.get(adCacheListTotal));
      System.out.println("redis");
  } else {*/
        TopicExample couponExample = new TopicExample();
        TopicExample.Criteria criteria = couponExample.createCriteria();
        if(!StringUtils.isEmpty(title)){
            criteria.andTitleLike("%"+title+"%");
        }
        if(!StringUtils.isEmpty(subtitle)){
            criteria.andSubtitleLike("%"+title+"%");
        }

        couponExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        ads = topicService.selectByExample(couponExample);
    /*  JSONArray.(ads);
      //查到以后缓存到redis里
   redisUtil.set(adCacheListName, ads);*/
        PageInfo pageInfo = new PageInfo(ads);
        total = pageInfo.getTotal();
        //查到以后缓存到redis里
        /* redisUtil.set(adCacheListTotal, total);*/
        /* }*/
        PageBean<Topic> adPageBean = new PageBean<>();
        adPageBean.setItems(ads);
        adPageBean.setTotal(total);
        ResponseUtils<PageBean> pageBeanResponseUtils = new ResponseUtils<PageBean>();
        pageBeanResponseUtils.setData(adPageBean);
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }


    @ResponseBody
    @RequestMapping("admin/topic/create")
    public ResponseUtils<Topic>  create(@RequestBody JSONObject jsonObject){
        jsonObject.put("goods","[]");
        Topic ad = jsonObject.toJavaObject(Topic.class);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ad.setAddTime(CreateDate.createDate());
        topicService.insert(ad);
        ResponseUtils<Topic> pageBeanResponseUtils = new ResponseUtils<Topic>();
        pageBeanResponseUtils.setData(ad);
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }
    @ResponseBody
    @RequestMapping("admin/topic/delete")
    public ResponseUtils<Topic>  delete(@RequestBody Topic ad){
        topicService.deleteByPrimaryKey(ad.getId());
        ResponseUtils pageBeanResponseUtils = new ResponseUtils<>();
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }
    @ResponseBody
    @RequestMapping("admin/topic/update")
    public ResponseUtils<Topic>  update(@RequestBody Topic ad){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ad.setAddTime(CreateDate.createDate());
        topicService.updateByPrimaryKey(ad);
        ResponseUtils<Topic> pageBeanResponseUtils = new ResponseUtils<Topic>();
        pageBeanResponseUtils.setData(ad);
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }
}
