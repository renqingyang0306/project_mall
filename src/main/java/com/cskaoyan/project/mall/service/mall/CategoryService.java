package com.cskaoyan.project.mall.service.mall;

import com.cskaoyan.project.mall.domain.Category;

import java.util.Date;
import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/17 18:50
 */
public interface CategoryService {
    List<Category> queryAllCategory();
    List<Category> queryAllCategoryLevel1();
    List<Category> queryAllCategoryByPid(Integer pid);

    Category queryCategory(Integer id);
    int insertCategory(Category category);
    int updateCategoryById(Category category);
    int deleteCategoryById(Category category);
    int deleteCategoryByDeleted(Category category);

}
