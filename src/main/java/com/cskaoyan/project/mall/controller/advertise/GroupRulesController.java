package com.cskaoyan.project.mall.controller.advertise;

import com.alibaba.fastjson.JSONObject;
import com.cskaoyan.project.mall.domain.*;
import com.cskaoyan.project.mall.service.advertiseService.GroupRulesService;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class GroupRulesController {
@Autowired
    GroupRulesService groupRulesService;


    @ResponseBody
    @RequestMapping("admin/groupon/list")
    public ResponseUtils<PageBean> list(int page,
                                            int limit,
                                            String sort,
                                            String order,
                                            Integer goodsId){
        String adCacheListName = "GrouponList" +page + limit;
        String adCacheListTotal = "GrouponList";
        List<GrouponRules> ads = null;
        long total = 0;
        GrouponRulesExample couponExample = new GrouponRulesExample();
        GrouponRulesExample.Criteria criteria = couponExample.createCriteria();
        if(!StringUtils.isEmpty(goodsId)){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        couponExample.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        ads = groupRulesService.selectByExample(couponExample);
        PageInfo pageInfo = new PageInfo(ads);
        total = pageInfo.getTotal();
        PageBean<GrouponRules> adPageBean = new PageBean<>();
        adPageBean.setItems(ads);
        adPageBean.setTotal(total);
        ResponseUtils<PageBean> pageBeanResponseUtils = new ResponseUtils<PageBean>();
        pageBeanResponseUtils.setData(adPageBean);
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }
    @ResponseBody
    @RequestMapping("admin/groupon/create")
    public ResponseUtils<GrouponRules>  create(@RequestBody JSONObject jsonObject){
        String expireTime = jsonObject.getString("expireTime");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = null;
        try {
            parse = df.parse(expireTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        jsonObject.put("expireTime",parse);
        GrouponRules ad = jsonObject.toJavaObject(GrouponRules.class);
        //Goods 查询商品信息 放进rules
          ad.setGoodsId(11);
          ad.setGoodsName("11");
        //Goods 查询商品信息 放进rules

        Date date = new Date();
        ad.setAddTime(date);

        groupRulesService.insert(ad);
        ResponseUtils<GrouponRules> pageBeanResponseUtils = new ResponseUtils<GrouponRules>();
        pageBeanResponseUtils.setData(ad);
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }
    @ResponseBody
    @RequestMapping("admin/groupon/delete")
    public ResponseUtils<GrouponRules>  delete(@RequestBody GrouponRules ad){
        groupRulesService.deleteByPrimaryKey(ad.getId());
        ResponseUtils pageBeanResponseUtils = new ResponseUtils<>();
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }
    @ResponseBody
    @RequestMapping("admin/groupon/update")
    public ResponseUtils<GrouponRules>  update(@RequestBody JSONObject jsonObject){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            jsonObject.put("expireTime",df.parse((String) jsonObject.get("expireTime")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GrouponRules ad = jsonObject.toJavaObject(GrouponRules.class);
        ad.setAddTime(CreateDate.createDate());
        groupRulesService.updateByPrimaryKey(ad);
        ResponseUtils<GrouponRules> pageBeanResponseUtils = new ResponseUtils<GrouponRules>();
        pageBeanResponseUtils.setData(ad);
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }
}
