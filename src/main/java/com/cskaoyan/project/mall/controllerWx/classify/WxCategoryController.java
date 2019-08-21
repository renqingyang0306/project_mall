package com.cskaoyan.project.mall.controllerWx.classify;

import com.cskaoyan.project.mall.domain.Category;
import com.cskaoyan.project.mall.domain.Goods;
import com.cskaoyan.project.mall.domain.GoodsSpecification;
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
 * @date 2019/8/21 12:32
 */
@Controller
public class WxCategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    GoodsService goodsService;

    @RequestMapping("/wx/catalog/current")
    @ResponseBody
    public ResponseUtils queryAllCategory(Integer id) {
        ResponseUtils responseUtils = new ResponseUtils<>();
        if (id == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("服务端错误");
            return responseUtils;
        }
        Category currentCategory = categoryService.queryCategory(id);
        List<Category> currentSubCategory = categoryService.queryAllCategoryByPid(id);

        Map<String,Object> map = new HashMap();
        map.put("currentCategory",currentCategory);
        map.put("currentSubCategory",currentSubCategory);

        if (currentCategory == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("当前 id 不存在");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(map);
        }
        return responseUtils;
    }

    @RequestMapping("/wx/catalog/index")
    @ResponseBody
    public ResponseUtils indexCategory() {
        ResponseUtils responseUtils = new ResponseUtils<>();
        //查询全部一级类目
        List<Category> categoryList = categoryService.queryAllCategoryLevel1();
        //查询第一个一级类目的详情
        Category currentCategory = categoryList.get(0);
        //查询该一级类目下的全部二级类目
        List<Category> currentSubCategory = categoryService.queryAllCategoryByPid(currentCategory.getId());

        Map<String,Object> map = new HashMap();
        map.put("categoryList",categoryList);
        map.put("currentCategory",currentCategory);
        map.put("currentSubCategory",currentSubCategory);

        if (categoryList == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("没有一级类目");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(map);
        }
        return responseUtils;
    }


}
