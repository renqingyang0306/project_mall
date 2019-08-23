package com.cskaoyan.project.mall.controllerwx.classify;

import com.alibaba.fastjson.JSONObject;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

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
    @Autowired
    SearchHistoryService searchHistoryService;
    @Autowired
    KeywordService keywordService;

    //当用户没有登录的时候存储本地搜索历史
//    List<SearchHistory> cacheHistoryKeywordList = new ArrayList<>();

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
        //查询该 categoryId 的信息
        Category currentCategory = categoryService.queryCategory(id);
        //如果 currentCategory 是一级类目，将其第一个二级类目作为 currentCategory
        if (currentCategory.getPid() == 0) {
            List<Category> categories = categoryService.queryAllCategoryByPid(id);
            if (categories.size() > 0) {
                currentCategory = categories.get(0);
            }
        }
        List<Category> brotherCategory = new ArrayList<>();
        Category parentCategory = null;
        if (currentCategory != null) {
            //查询该 categoryId 的同级 category
            brotherCategory = categoryService.queryAllCategoryByPid(currentCategory.getPid());
            //查询该 categoryId 的父级 category
            parentCategory = categoryService.queryCategory(currentCategory.getPid());
        }

        Map<String, Object> map = new HashMap();
        map.put("brotherCategory",brotherCategory);
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
            responseUtils.setErrmsg("categoryId 不能为 null");
            return responseUtils;
        }
        //获取当前登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<SearchHistory> historyKeywordList = new ArrayList<>();
        //将 keyword 保存到搜索历史中
        SearchHistory searchHistory = new SearchHistory();
        int insert = 0;
        //用户未登录则记录搜索历史到本地缓存 本地缓存未实现
        if (user != null && keyword != null) {
           //查询该 keyword 在数据库中是否存在
            List<SearchHistory> searchHistories = searchHistoryService.queryAllSearchHistoryByUserIdAndKeyword(user.getId(), keyword);
            if (searchHistories != null && searchHistories.size() > 0) {
                insert = 2;
            } else {    // 没有该 keyword 则新增搜索历史
                Date date = new Date();
                searchHistory.setUserId(user.getId());
                searchHistory.setKeyword(keyword);
                //直接设置搜索来源为 wx
                searchHistory.setFrom("wx");
                searchHistory.setAddTime(date);
                searchHistory.setUpdateTime(date);
                //默认为未删除状态
                searchHistory.setDeleted(false);
                insert = searchHistoryService.insertSearchHistory(searchHistory);
            }
        }
        //用于查询保存关键字查询的 categpryId
        List<Goods> tempGoodsList = goodsService.queryPageOrderByExample(keyword, null, page, size, sort, order);
        //通过keyword模糊查询所有商品
        List<Goods> goodsList = goodsService.queryPageOrderByExample(keyword, categoryId, page, size, sort, order);

        //保存商品中的 categoryId
        List<Integer> tempList = new ArrayList();
        for (Goods goods : tempGoodsList) {
            Goods goods1 = goodsService.queryById(goods.getId());
            if (tempList.size() == 0) {
                tempList.add(goods1.getCategoryId());
            }
            //查看缓存的 tempList 中是否有重复的 categoryId
            if (! tempList.contains(goods.getCategoryId())) {
                tempList.add(goods.getCategoryId());
            }
            /*for (int i = 0; i < tempList.size(); i++) {
                Integer integer = tempList.get(i);
                if (goods1.getCategoryId().equals(integer)) {
                    continue;
                }
                tempList.add(goods1.getCategoryId());
            }*/
        }
        List<Category> filterCategoryList = new ArrayList<>();
        //通过 categoryId 查询对应的 category
        for (int i = 0; i < tempList.size(); i++) {
            Category category = categoryService.queryCategory((Integer) tempList.get(i));
            filterCategoryList.add(category);
        }
        /*if (categoryId != 0) {
            //查询该 category 的信息
            Category category = categoryService.queryCategory(categoryId);
            //查询与该 categoryid 同级的类目
            filterCategoryList = categoryService.queryAllCategoryByPid(category.getPid());
        }*/
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

    @RequestMapping("/wx/search/index")
    @ResponseBody
    public ResponseUtils searchGoodsIndex() {
        ResponseUtils responseUtils = new ResponseUtils<>();
        //查询所有默认关键词
        List<Keyword> defaultKeywords = keywordService.queryKeywordByIsDefault(true);
        List<Keyword> hotKeywordList = keywordService.queryKeywordByIsHot(true);
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
        map.put("hotKeywordList",hotKeywordList);
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

    /**
     * @creator shentaotao
     * @creat date 2019/8/23 11:47
     * @param keyword   搜索框输入的参数
     * @return com.cskaoyan.project.mall.utils.ResponseUtils
     * @throws
     * @since
     */
    @RequestMapping("/wx/search/helper")
    @ResponseBody
    public ResponseUtils searchGoodsHelper(String keyword) {
        ResponseUtils responseUtils = new ResponseUtils<>();
        List<Keyword> keywords = new ArrayList<>();
        if (keyword != null) {
            keywords = keywordService.searchKeywordByIsHotAndKeyword(true, keyword);
        }
        List<String> list = new ArrayList<>();
        if (keywords.size() > 0) {
            for (Keyword keyword1 : keywords) {
                list.add(keyword1.getKeyword());
            }
        }

        if (keywords.size() == -1) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("不可能出错！！！！");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(list);
        }
        return responseUtils;
    }

    /**
     * @creator shentaotao
     * @creat date 2019/8/23 11:46
     * @param jsonObject    我也不知道有什么，反正就是个 {}
     * @return com.cskaoyan.project.mall.utils.ResponseUtils
     * @throws
     * @since
     */
    @RequestMapping("/wx/search/clearhistory")
    @ResponseBody
    public ResponseUtils clearSearchHistory(@RequestBody JSONObject jsonObject) {
        ResponseUtils responseUtils = new ResponseUtils<>();
        //获取当前登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        int delete = 0;
        if (user != null) {
            //查询该用户的搜索历史
            List<SearchHistory> searchHistories = searchHistoryService.queryAllSearchHistoryByUserId(user.getId());
            if (searchHistories != null) {
                // 删除该用户的全部搜索历史
                for (int i = 0; i < searchHistories.size(); i++) {
                    delete = searchHistoryService.deleteLogicSearchHistoryByDeleted(searchHistories.get(i));
                }
            }
        }

        if (delete == 0) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("删除搜索历史失败");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(null);
        }
        return responseUtils;
    }
}
