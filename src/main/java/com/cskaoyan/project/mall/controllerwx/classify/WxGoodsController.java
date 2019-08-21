package com.cskaoyan.project.mall.controllerwx.classify;

import com.cskaoyan.project.mall.domain.Category;
import com.cskaoyan.project.mall.domain.Goods;
import com.cskaoyan.project.mall.service.goods.GoodsService;
import com.cskaoyan.project.mall.service.mall.CategoryService;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        List<Category> bortherCategory = categoryService.queryAllCategoryLevel1();
        Category currentCategory = categoryService.queryCategory(id);
        Category parentCategory = categoryService.queryCategory(currentCategory.getPid());
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
    public ResponseUtils queryAllGoods(Integer categoryId, Integer page, Integer size) {
        ResponseUtils responseUtils = new ResponseUtils<>();
        if (categoryId == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("categoryId不能为null");
            return responseUtils;
        }
        //查询该 category 的信息
        Category category = categoryService.queryCategory(categoryId);
        //查询与该 categoryid 同级的类目
        List<Category> filterCategoryList = categoryService.queryAllCategoryByPid(category.getPid());
        List<Goods> goodsList = goodsService.queryPageOrderByExample(categoryId, page, size);
        Map<String, Object> map = new HashMap();
        map.put("count",goodsList.size());
        map.put("filterCategoryList",filterCategoryList);
        map.put("goodsList",goodsList);

        if (category == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("当前 categoryId 不存在");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(map);
        }
        return responseUtils;
    }
}
