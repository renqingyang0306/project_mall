package com.cskaoyan.project.mall.service.mall.impl;

import com.cskaoyan.project.mall.domain.Brand;
import com.cskaoyan.project.mall.domain.BrandExample;
import com.cskaoyan.project.mall.mapper.BrandMapper;
import com.cskaoyan.project.mall.service.mall.BrandService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/17 15:23
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandMapper brandMapper;

    @Override
    public List<Brand> queryAllBrand() {
        BrandExample brandExample = new BrandExample();
        brandExample.createCriteria().andDeletedEqualTo(false);
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        return brands;
    }

    @Override
    public List<Brand> queryPageBrands(int page, int limit,String sort,String order) {
        PageHelper.startPage(page,limit);
        BrandExample brandExample = new BrandExample();
        brandExample.createCriteria().andDeletedEqualTo(false);
        brandExample.setOrderByClause(sort + " " + order);
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        return brands;
    }

    @Override
    public List<Brand> queryBrandByExample(Integer page, Integer limit, Integer id, String name, String sort, String order) {
        if (page != null && limit != null) {
            PageHelper.startPage(page,limit);
        }
        BrandExample brandExample = new BrandExample();
        BrandExample.Criteria criteria = brandExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (sort != null && order != null) {
            brandExample.setOrderByClause(sort + " " + order);
        }
        criteria.andIdEqualTo(id);
        name = "%" + name + "%";
        criteria.andNameLike(name);
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        return brands;
    }

    @Override
    public Brand queryBrandById(Integer id) {
        BrandExample brandExample = new BrandExample();
        BrandExample.Criteria criteria = brandExample.createCriteria();
       criteria.andDeletedEqualTo(false);
        criteria.andIdEqualTo(id);
        //id为主键，结果只有一个
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        if (brands.size() > 0) {
            return brands.get(0);
        }
        return null;
    }

    @Override
    public List<Brand> searchBrandById(int page, int limit, Integer id, String sort, String order) {
        PageHelper.startPage(page,limit);
        BrandExample brandExample = new BrandExample();
        BrandExample.Criteria criteria = brandExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andIdEqualTo(id);
        brandExample.setOrderByClause(sort + " " + order);
        List<Brand> brands = brandMapper.selectByExample(brandExample);

        return brands;
    }

    @Override
    public List<Brand> searchBrandByName(int page, int limit, String name,String sort,String order) {
        PageHelper.startPage(page,limit);
        BrandExample brandExample = new BrandExample();
        BrandExample.Criteria criteria = brandExample.createCriteria();
        brandExample.setOrderByClause(sort + " " + order);
        name = "%" + name + "%";
        criteria.andNameLike(name);
        criteria.andDeletedEqualTo(false);
        List<Brand> brands = brandMapper.selectByExample(brandExample);

        return brands;
    }

    @Override
    public List<Brand> searchBrandByIdAndName(int page, int limit,Integer id, String name,String sort,String order) {
        PageHelper.startPage(page,limit);
        BrandExample brandExample = new BrandExample();
        BrandExample.Criteria criteria = brandExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        brandExample.setOrderByClause(sort + " " + order);
        criteria.andIdEqualTo(id);
        name = "%" + name + "%";
        criteria.andNameLike(name);
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        return brands;
    }

    @Override
    public int insertBrand(Brand brand) {
        int insert = brandMapper.insert(brand);
        return insert;
    }

    @Override
    public int updateBrandById(Brand brand) {
        int update = brandMapper.updateByPrimaryKey(brand);
        return update;
    }

    @Override
    public int deleteBrandById(Brand brand) {
        int delete = brandMapper.deleteByPrimaryKey(brand.getId());
        return delete;
    }

    @Override
    public int deleteBrandByDeleted(Brand brand) {
        //通过将字段deleted修改为true实现删除操作
        brand.setDeleted(true);
        int update = brandMapper.updateByPrimaryKey(brand);
        return update;
    }

    @Override
    public List<Brand> selectByExample(BrandExample example) {
        return brandMapper.selectByExample(example);
    }
}
