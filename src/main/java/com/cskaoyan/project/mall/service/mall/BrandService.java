package com.cskaoyan.project.mall.service.mall;

import com.cskaoyan.project.mall.domain.Brand;
import com.cskaoyan.project.mall.domain.BrandExample;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/17 15:22
 */
public interface BrandService {
    List<Brand> queryAllBrand();
    List<Brand> queryPageBrands(int page,int limit,String sort,String order);

    List<Brand> queryBrandByExample(Integer page, Integer limit, Integer id, String name, String sort, String order);

    Brand queryBrandById(Integer id);
    List<Brand> searchBrandById(int page, int limit, Integer id, String sort, String order);
    List<Brand> searchBrandByName(int page, int limit, String name, String sort, String order);
    List<Brand> searchBrandByIdAndName(int page, int limit, Integer id, String name, String sort, String order);
    int insertBrand(Brand brand);
    int updateBrandById(Brand brand);
    int deleteBrandById(Brand brand);
    int deleteBrandByDeleted(Brand brand);
    List<Brand> selectByExample(BrandExample example);
}
