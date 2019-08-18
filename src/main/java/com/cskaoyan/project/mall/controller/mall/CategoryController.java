package com.cskaoyan.project.mall.controller.mall;

import com.cskaoyan.project.mall.domain.Category;
import com.cskaoyan.project.mall.service.mall.CategoryService;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.cskaoyan.project.mall.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

/**
 * @author 申涛涛
 * @date 2019/8/17 18:45
 */
@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/admin/category/list")
    @ResponseBody
    public ResponseUtils queryAllCategory() {
        List<Category> categories = categoryService.queryAllCategory();
        List<CategoryVo> categoryVoList = transBean(categories);
        List<CategoryVo> response = new ArrayList<>();
        for (CategoryVo categoryVo : categoryVoList) {

            if (categoryVo.getPid() == 0) {
                //获取二级类目
                List<CategoryVo> categoryVoList1 = recursionLevel(categoryVo, categoryVoList);
                categoryVo.setChildren(categoryVoList1);
                response.add(categoryVo);
            }
        }
        ResponseUtils responseUtils = new ResponseUtils();
        if (categories == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("服务端错误！");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功！");
            responseUtils.setData(response);
        }
        return responseUtils;

    }

    @RequestMapping("/admin/category/l1")
    @ResponseBody
    public ResponseUtils queryAllCategoryL1() {
        List<Category> categories = categoryService.queryAllCategoryLevel1();
        List<Map<String,String>> list = new ArrayList<>();
        for (Category category : categories) {
            Map<String,String> map = new HashMap<>();
            map.put("value", String.valueOf(category.getId()));
            map.put("lable",category.getName());
            list.add(map);
        }
        ResponseUtils responseUtils = new ResponseUtils();
        if (categories == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("服务端错误！");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功！");
            responseUtils.setData(list);
        }
        return responseUtils;

    }

    @RequestMapping("/admin/category/update")
    @ResponseBody
    public ResponseUtils updateCategoryById(@RequestBody Category categorie) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        categorie.setUpdateTime(new Date());
        int update = categoryService.updateCategoryById(categorie);
        ResponseUtils responseUtils = new ResponseUtils();
        if (update == 0) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("服务端错误！");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功！");
            responseUtils.setData(null);
        }
        return responseUtils;

    }

    @RequestMapping("/admin/category/create")
    @ResponseBody
    public ResponseUtils createCategory(@RequestBody Category category) {
        Date date = new Date();
        category.setAddTime(date);
        category.setUpdateTime(date);
        category.setSortOrder((byte) 10);
        category.setDeleted(false);
        //添加成功后category就会被添加成功的category覆盖
        int insert = categoryService.insertCategory(category);
        //查询刚刚添加成功的category信息
        //System.out.println(category);
        ResponseUtils responseUtils = new ResponseUtils();
        if (insert == 0) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("服务端错误！");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功！");
            responseUtils.setData(category);
        }
        return responseUtils;

    }

    @RequestMapping("/admin/category/delete")
    @ResponseBody
    public ResponseUtils deleteCategoryByDeleted(@RequestBody Category category) {
        //查询该category为几级类目
        String level = category.getLevel();
        int delete = 0;
        if ("L1".equals(level)) {
            List<Category> categories = categoryService.queryAllCategoryByPid(category.getId());
            //删除所有二级类目
            for (Category category1 : categories) {
                 delete = categoryService.deleteCategoryByDeleted(category1);
                //逻辑删除失败的时候退出该循环
                if (delete == 0) break;
            }
            //删除该一级类目
            delete = categoryService.deleteCategoryByDeleted(category);
        } else if ("L2".equals(level)) {
            delete = categoryService.deleteCategoryByDeleted(category);
        }

        ResponseUtils responseUtils = new ResponseUtils();
        if (delete == 0) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("服务端错误！");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功！");
            responseUtils.setData(null);
        }
        return responseUtils;

    }
    //获取一级类目对应的二级类目
    private List<CategoryVo> recursionLevel(CategoryVo categoryVo, List<CategoryVo> initCategoryVos) {
        List<CategoryVo> list = new ArrayList<>();
        for (CategoryVo cvo : initCategoryVos) {
            if (cvo.getPid().equals(categoryVo.getId())) {
                list.add(cvo);
            }
        }
        return list;
    }

    //将category转换为categoryVo
    private List<CategoryVo> transBean(List<Category> categories) {
            List<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categories) {
            CategoryVo categoryVo = new CategoryVo();

            categoryVo.setDesc(category.getDesc());
            categoryVo.setIconUrl(category.getIconUrl());
            categoryVo.setId(category.getId());
            categoryVo.setKeywords(category.getKeywords());
            categoryVo.setLevel(category.getLevel());
            categoryVo.setName(category.getName());
            categoryVo.setPicUrl(category.getPicUrl());

            categoryVo.setPid(category.getPid());
            categoryVo.setAddTime(category.getAddTime());
            categoryVo.setSortOrder(category.getSortOrder());
            categoryVo.setUpdateTime(category.getUpdateTime());
            categoryVo.setDeleted(category.getDeleted());

            categoryVoList.add(categoryVo);
        }

       return categoryVoList;
    }
}
