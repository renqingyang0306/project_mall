package com.cskaoyan.project.mall.controller.advertise;

import com.cskaoyan.project.mall.domain.*;
import com.cskaoyan.project.mall.mapper.GoodsMapper;
import com.cskaoyan.project.mall.service.advertiseService.GroupOnService;
import com.cskaoyan.project.mall.service.advertiseService.GroupRulesService;
import com.cskaoyan.project.mall.service.goods.GoodsService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class GroupOnController  {
    @Autowired
    GroupOnService groupOnService;
    @Autowired
    GroupRulesService groupRulesService;
    @Autowired
    GoodsService goodsService;

    @ResponseBody
    @RequestMapping("admin/groupon/listRecord")
    public ResponseUtils<PageBean> list(int page,
                                        int limit,
                                        String sort,
                                        String order,
                                        String goodsId) {
        //total放groupon的数量
        //goods调用别人的接口查询


        List<CouponGrouponBean> result = new ArrayList<>();
        //说明是全局要全部列表
        if (StringUtils.isEmpty(goodsId)) {
            GrouponExample grouponExample = new GrouponExample();
            GrouponExample.Criteria criteria = grouponExample.createCriteria();
            //GouponId等于0说明是发起人
            criteria.andGrouponIdEqualTo(0);
            List<Groupon> groupons = groupOnService.selectByExample(grouponExample);

            for (Groupon groupon : groupons) {
                Integer rulesId = groupon.getRulesId();
                GrouponRules grouponRules = groupRulesService.selectByPrimaryKey(rulesId);
                //goods调用别人的接口查询
                //**************** 查询商品
                Goods goods = goodsService.queryById(grouponRules.getGoodsId());

                //查响应人的信息
                GrouponExample grouponExample1 = new GrouponExample();
                GrouponExample.Criteria criteria1 = grouponExample1.createCriteria();
                //GrouponId不等于0说明是此发起人相应的响应人
                criteria1.andGrouponIdNotEqualTo(0);
                criteria1.andCreatorUserIdEqualTo(groupon.getCreatorUserId());
                criteria1.andRulesIdEqualTo(groupon.getRulesId());
                List<Groupon> grouponed = groupOnService.selectByExample(grouponExample1);
                CouponGrouponBean couponGrouponBean = CouponGrouponBean.createCouponGrouponBean(goods, groupon, grouponRules, grouponed);
                result.add(couponGrouponBean);
            }

        }else {
            //goods调用别人的GoodsID接口查询
            //**************** 查询商品

            Goods goods = goodsService.queryById(Integer.parseInt(goodsId));
            GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
            GrouponRulesExample.Criteria criteria = grouponRulesExample.createCriteria();
            criteria.andGoodsIdEqualTo(Integer.parseInt(goodsId));
            //找到此商品的所有团购规则
            List<GrouponRules> grouponRules = groupRulesService.selectByExample(grouponRulesExample);
            for (GrouponRules grouponRule:grouponRules) {
                GrouponExample grouponExample = new GrouponExample();
                GrouponExample.Criteria criteria1 = grouponExample.createCriteria();
                //每一个团购规则下此商品的所有发起人的信息
                criteria1.andRulesIdEqualTo(grouponRule.getId());
                criteria1.andGrouponIdEqualTo(0);
                List<Groupon> groupons = groupOnService.selectByExample(grouponExample);
                //每一个发起人中找到响应其订单的用户
                for (Groupon groupon: groupons) {
                    GrouponExample grouponExample1 = new GrouponExample();
                    GrouponExample.Criteria criteria2 = grouponExample1.createCriteria();
                    //响应此订单的响应人的特征
                    criteria2.andGrouponIdNotEqualTo(0);
                    criteria2.andRulesIdEqualTo(grouponRule.getId());
                    criteria2.andCreatorUserIdEqualTo(groupon.getCreatorUserId());
                    List<Groupon> grouponed = groupOnService.selectByExample(grouponExample1);
                    CouponGrouponBean couponGrouponBean = CouponGrouponBean.createCouponGrouponBean(goods, groupon, grouponRule, grouponed);
                    result.add(couponGrouponBean);
                }
            }
        }

        PageBean<CouponGrouponBean> adPageBean = new PageBean<>();
        adPageBean.setItems(result);
        adPageBean.setTotal(result.size());

            ResponseUtils<PageBean> pageBeanResponseUtils = new ResponseUtils<PageBean>();
            pageBeanResponseUtils.setData(adPageBean);
            pageBeanResponseUtils.setErrno(0);
            pageBeanResponseUtils.setErrmsg("成功");
            return pageBeanResponseUtils;
    }

   /* @ResponseBody
    @RequestMapping("admin/groupon/create")
    public ResponseUtils<Groupon>  create(@RequestBody Groupon ad){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ad.setAddTime(CreateDate.createDate());
        groupOnService.insert(ad);
        ResponseUtils<Groupon> pageBeanResponseUtils = new ResponseUtils<Groupon>();
        pageBeanResponseUtils.setData(ad);
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }
    @ResponseBody
    @RequestMapping("admin/groupon/delete")
    public ResponseUtils<Groupon>  delete(@RequestBody Groupon ad){
        groupOnService.deleteByPrimaryKey(ad.getId());
        ResponseUtils pageBeanResponseUtils = new ResponseUtils<>();
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }
    @ResponseBody
    @RequestMapping("admin/groupon/update")
    public ResponseUtils<Groupon>  update(@RequestBody Groupon ad){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ad.setAddTime(CreateDate.createDate());
        groupOnService.updateByPrimaryKey(ad);
        ResponseUtils<Groupon> pageBeanResponseUtils = new ResponseUtils<Groupon>();
        pageBeanResponseUtils.setData(ad);
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }*/
}
