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
@RestController
public class BrandController {
    @Autowired
    BrandService brandService;

    @RequestMapping("/admin/brand/list")
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

    @RequestMapping("/admin/brand/creat")
    public ResponseUtils insertBrand(Brand brand) {
        Date date = new Date();
        brand.setDeleted(false);
        brand.setAddTime(date);
        brand.setUpdateTime(date);
        brand.setSortOrder((byte) 10);
        int insert = brandService.insertBrand(brand);
        ResponseUtils responseUtils = new ResponseUtils();
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
    public ResponseUtils updateBrandById(@RequestBody Brand brand) {

        int update = brandService.updateBrandById(brand);
        ResponseUtils responseUtils = new ResponseUtils();
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
