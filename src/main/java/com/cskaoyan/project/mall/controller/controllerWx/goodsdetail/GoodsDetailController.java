package com.cskaoyan.project.mall.controller.controllerWx.goodsdetail;


import com.cskaoyan.project.mall.domain.*;
import com.cskaoyan.project.mall.mapper.CollectMapper;
import com.cskaoyan.project.mall.mapper.CommentMapper;
import com.cskaoyan.project.mall.mapper.FootprintMapper;
import com.cskaoyan.project.mall.service.advertiseService.GroupRulesService;
import com.cskaoyan.project.mall.service.goods.*;
import com.cskaoyan.project.mall.service.mall.BrandService;
import com.cskaoyan.project.mall.service.mall.IssueService;
import com.cskaoyan.project.mall.service.userService.CollectService;
import com.cskaoyan.project.mall.service.userService.FootprintService;
import com.cskaoyan.project.mall.service.userService.UserService;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsDetailController {

    @Autowired
    GoodsService goodsService;
    @Autowired
    GoodsAttributeService goodsAttributeService;
    @Autowired
    GoodsProductService goodsProductService;
    @Autowired
    IssueService issueService;
    @Autowired
    BrandService brandService;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    GoodsSpecificationService goodsSpecificationService;
    @Autowired
    CollectMapper collectMapper;
    @Autowired
    FootprintMapper footprintMapper;
    @Autowired
    GroupRulesService groupRulesService;
    @Autowired
    UserService userService;





    @RequestMapping("wx/goods/detail")
    @ResponseBody
    public ResponseUtils<HashMap> detailesd(@RequestParam(defaultValue = "1") Integer userId, Integer id)//UserId// )
    {
        Goods goods = goodsService.queryById(id);
        //Attribute
        List<GoodsAttribute> goodsAttributes = goodsAttributeService.queryByGoodsId(id);
        //
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationService.queryByGoodsId(id);
        List<Map<String, Object>> goodsSpecificationVo = new ArrayList<>(goodsSpecifications.size());
        for (GoodsSpecification goodsSpecification : goodsSpecifications) {
            Map<String, Object> item = new HashMap<>();
            item.put("规格",goodsSpecification);
            goodsSpecificationVo.add(item);
        }

        List<Issue> issues = issueService.queryPageIssues(1, 4,"","add_time","desc");

        List<GoodsProduct> goodsProducts = goodsProductService.queryByGoodsId(id);
        //团购信息
        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        GrouponRulesExample.Criteria criteria1 = grouponRulesExample.createCriteria();
        criteria1.andGoodsIdEqualTo(id);
        List<GrouponRules> grouponRules = groupRulesService.selectByExample(grouponRulesExample);

        //评论
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andValueIdEqualTo(id);
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        List<Map<String, Object>> commentsVo = new ArrayList<>(comments.size());
        for (Comment comment : comments) {
            Map<String, Object> c = new HashMap<>();
            c.put("id", comment.getId());
            c.put("addTime", comment.getAddTime());
            c.put("content", comment.getContent());
            User user = userService.selectByPrimaryKey(comment.getUserId());
            c.put("nickname", user == null ? "" : user.getNickname());
            c.put("avatar", user == null ? "" : user.getAvatar());
            c.put("picList", comment.getPicUrls());
            commentsVo.add(c);
        }
        Map<String, Object> commentList = new HashMap<>();
        commentList.put("count", comments.size());
        commentList.put("data", commentsVo);
        Brand brand = new Brand();
    if(brandService.queryBrandById(goods.getBrandId())!= null) {
        brand = brandService.queryBrandById(goods.getBrandId());
    }

    // 用户收藏
        int userHasCollect = 0;
        if (userId != null) {
            CollectExample collectExample = new CollectExample();
            CollectExample.Criteria criteria2 = collectExample.createCriteria();
            criteria2.andUserIdEqualTo(userId);
            criteria2.andValueIdEqualTo(id);
            List<Collect> collects = collectMapper.selectByExample(collectExample);
            userHasCollect = collects.get(0).getType();
        }

        // 记录用户的足迹 异步处理
        if (userId != null) {
              //记录用户足迹
                Footprint footprint = new Footprint();
                footprint.setUserId(userId);
                footprint.setGoodsId(id);
                footprintMapper.insert(footprint);
        }


        Map<String, Object> data = new HashMap<>();
        try {
            data.put("info", goods);
            data.put("userHasCollect", userHasCollect);
            data.put("issue", issues);
            data.put("comment", commentsVo);
            data.put("specificationList", goodsSpecificationVo);
            data.put("productList", goodsProducts);
            data.put("attribute", goodsAttributes);
            data.put("brand", brand);
            data.put("groupon", grouponRules);
            //SystemConfig.isAutoCreateShareImage()
            data.put("share", "");

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //商品分享图片地址
        data.put("shareImage", goods.getShareUrl());
        ResponseUtils pageBeanResponseUtils = new ResponseUtils<>();
        pageBeanResponseUtils.setData(data);
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }

    @RequestMapping("wx/goods/related")
    @ResponseBody
    public ResponseUtils<List> detailed( @RequestParam(defaultValue = "1") Integer userId,Integer id)//UserId// )
    {
        //查相关商品
        Goods goods = goodsService.queryById(id);
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        criteria.andCategoryIdEqualTo(goods.getCategoryId());
        List<Goods> goodsList = goodsService.selectByExample(goodsExample);
        ResponseUtils<List> pageBeanResponseUtils = new ResponseUtils<List>();
        pageBeanResponseUtils.setData(goodsList);
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;
    }


 /*   @RequestMapping("goodscount")
    @ResponseBody
    public ResponseUtils<int> detailed( userId,Integer id)//UserId// )
    {
       return null; //购物车，等购物车回显。
    }
*/



}

