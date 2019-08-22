package com.cskaoyan.project.mall.controllerwx.classify;

import com.cskaoyan.project.mall.domain.*;
import com.cskaoyan.project.mall.service.goods.GoodsService;
import com.cskaoyan.project.mall.service.mall.CategoryService;
import com.cskaoyan.project.mall.service.mall.KeywordService;
import com.cskaoyan.project.mall.service.userService.SearchHistoryService;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 申涛涛
 * @date 2019/8/21 14:16
 */
@Controller
public class WxGoodsController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/wx/goods/count")
    @ResponseBody
    public ResponseUtils queryGoodsCount() {
        ResponseUtils responseUtils = new ResponseUtils<>();

        long goodsCount = goodsService.selectCountGoods();

        Map<String, Object> map = new HashMap();
        map.put("goodsCount",goodsCount);

        if (goodsCount == -1) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("未知错误");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(map);
        }
        return responseUtils;
    }

    @RequestMapping("/wx/goods/category")
    @ResponseBody
    public ResponseUtils queryPageCategory(Integer id) {
        ResponseUtils responseUtils = new ResponseUtils<>();
        if (id == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("id 不能为null");
            return responseUtils;
        }
        Category currentCategory = categoryService.queryCategory(id);
        List<Category> bortherCategory = new ArrayList<>();
        Category parentCategory = null;
        if (currentCategory != null) {
            //查询该 categoryId 的同级 category
            bortherCategory = categoryService.queryAllCategoryByPid(currentCategory.getPid());
            //查询该 categoryId 的父级 category
            parentCategory = categoryService.queryCategory(currentCategory.getPid());
        }

        Map<String, Object> map = new HashMap();
        map.put("bortherCategory",bortherCategory);
        map.put("currentCategory",currentCategory);
        map.put("parentCategory",parentCategory);

        if ("" == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("当前 id 不存在");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(map);
        }
        return responseUtils;
    }

    @RequestMapping("/wx/goods/list")
    @ResponseBody
    public ResponseUtils queryAllGoods(String keyword, Integer categoryId, Integer page, Integer size, String sort, String order) {
        ResponseUtils responseUtils = new ResponseUtils<>();
        if (categoryId == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("categoryId不能为null");
            return responseUtils;
        }
        //通过keyword模糊查询所有商品
        List<Goods> goodsList = goodsService.queryPageOrderByExample(keyword, categoryId, page, size, sort, order);

        //保存商品中的 categoryId
        List<Integer> tempList = new ArrayList();
        for (Goods goods : goodsList) {
            Goods goods1 = goodsService.queryById(goods.getId());
            if (tempList.size() == 0) {
                tempList.add(goods1.getCategoryId());
            }
            for (int i = 0; i < tempList.size(); i++) {
                Integer integer = tempList.get(i);
                if (goods1.getCategoryId().equals(integer)) {
                    continue;
                }
                tempList.add(goods1.getCategoryId());
            }
        }
        List<Category> filterCategoryList = new ArrayList<>();
        //通过 categoryId 查询对应的 category
        for (int i = 0; i < tempList.size(); i++) {
            Category category = categoryService.queryCategory((Integer) tempList.get(i));
            filterCategoryList.add(category);
        }
        if (categoryId != 0) {
            //查询该 category 的信息
            Category category = categoryService.queryCategory(categoryId);
            //查询与该 categoryid 同级的类目
            filterCategoryList = categoryService.queryAllCategoryByPid(category.getPid());
        }
        Map<String, Object> map = new HashMap();
        map.put("count",goodsList.size());
        map.put("filterCategoryList",filterCategoryList);
        map.put("goodsList",goodsList);

        if (goodsList == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("当前 categoryId 不存在");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(map);
        }
        return responseUtils;
    }

    @Autowired
    KeywordService keywordService;
    @Autowired
    SearchHistoryService searchHistoryService;

    @RequestMapping("/wx/search/index")
    @ResponseBody
    public ResponseUtils searchGoodsIndex() {
        ResponseUtils responseUtils = new ResponseUtils<>();
        //查询所有默认关键词
        List<Keyword> defaultKeywords = keywordService.queryKeywordByIsDefault(true);
        List<Keyword> hotKeywords = keywordService.queryKeywordByIsHot(true);
        //获取当前登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<SearchHistory> historyKeywordList = new ArrayList<>();
        if (user != null) {
            //查询该用户的商品搜索历史
            historyKeywordList = searchHistoryService.queryAllSearchHistoryByUserId(user.getId());
        }

        Map<String, Object> map = new HashMap();
        //只需要一个关键词
        map.put("defaultKeyword",defaultKeywords.get(0));
        map.put("hotKeywords",hotKeywords);
        map.put("historyKeywordList",historyKeywordList);

        if ("" == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("当前 categoryId 不存在");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(map);
        }
        return responseUtils;
    }

    @RequestMapping("/wx/search/helper")
    @ResponseBody
    public ResponseUtils searchGoodsHelper(String keyword) {
        ResponseUtils responseUtils = new ResponseUtils<>();

        String[] strings = new String[] {};
        if ("" == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("当前 categoryId 不存在");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(strings);
        }
        return responseUtils;
    }
}
