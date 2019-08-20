package com.cskaoyan.project.mall.controller.mall;

import com.cskaoyan.project.mall.domain.Brand;
import com.cskaoyan.project.mall.service.mall.BrandService;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/17 15:13
 */
@Controller
public class BrandController {
    @Autowired
    BrandService brandService;

    @RequestMapping("/admin/brand/list")
    @ResponseBody
    public ResponseUtils searchBrandByIdAndName(int page, int limit, Integer id, String name, String sort, String order) {
        List<Brand> brands = null;
        if (id == null && name == null) {
            brands = brandService.queryPageBrands(page, limit, sort, order);
        } else if (id != null && name == null) {
            brands = brandService.searchBrandById(page, limit, id, sort, order);
        } else if (id == null && name != null) {
            brands = brandService.searchBrandByName(page, limit, name, sort, order);
        } else {
            brands = brandService.searchBrandByIdAndName(page, limit, id, name, sort, order);
        }
        PageInfo pageInfo = new PageInfo(brands);
        long total = pageInfo.getTotal();
        PageBean pageBean = new PageBean(brands,total);
        ResponseUtils responseUtils = new ResponseUtils();
        if (brands == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("服务端错误！");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功！");
            responseUtils.setData(pageBean);
        }
        return responseUtils;
    }

    @RequestMapping("/admin/brand/create")
    @ResponseBody
    public ResponseUtils insertBrand(@RequestBody Brand brand) {
        ResponseUtils responseUtils = new ResponseUtils();
        if (brand.getName() == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("品牌商名称不能为空！");
            return responseUtils;
        }
        if (brand.getDesc() == null) {
            brand.setDesc("");
        }
        if (brand.getPicUrl() == null) {
            brand.setPicUrl("");
        }
        Date date = new Date();
        brand.setDeleted(false);
        brand.setAddTime(date);
        brand.setUpdateTime(date);
        brand.setSortOrder((byte) 10);
        int insert = brandService.insertBrand(brand);

        if (insert == 0) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("服务端错误！");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功！");
            responseUtils.setData(brand);
        }
        return responseUtils;
    }

    @RequestMapping("/admin/brand/update")
    @ResponseBody
    public ResponseUtils updateBrandById(@RequestBody Brand brand) {
        ResponseUtils responseUtils = new ResponseUtils();

        if (brand.getName() == null || brand.getId() == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("id 或 品牌商名称不能为 null");
            return responseUtils;
        }
        Brand brand1 = brandService.queryBrandById(brand.getId());
        //查询该品牌制造商是否存在
        if (brand1 == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("品牌制造商不存在");
            return responseUtils;
        }
        //若是没有传以下数据，则取原数据
        if (brand.getDesc() == null) {
            brand.setDesc(brand1.getDesc());
        }
        if (brand.getPicUrl() == null) {
            brand.setPicUrl(brand1.getPicUrl());
        }

        //add_time和deleted不允许用户编辑时修改
        brand.setAddTime(brand1.getAddTime());
        brand.setDeleted(brand1.getDeleted());

        brand.setUpdateTime(new Date());

        int update = brandService.updateBrandById(brand);

        if (update == 0) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("服务端错误！");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功！");
            responseUtils.setData(brandService.queryBrandById(brand.getId()));
        }
        return responseUtils;
    }

    @RequestMapping("/admin/brand/delete")
    @ResponseBody
    public ResponseUtils deleteBrandByDeleted(@RequestBody Brand brand) {
        Brand brand1 = brandService.queryBrandById(brand.getId());
        int delete = brandService.deleteBrandByDeleted(brand1);
        ResponseUtils responseUtils = new ResponseUtils();
        if (delete == 0) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("服务端错误！");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功！");
            responseUtils.setData(brandService.queryBrandById(brand.getId()));
        }
        return responseUtils;
    }
}
