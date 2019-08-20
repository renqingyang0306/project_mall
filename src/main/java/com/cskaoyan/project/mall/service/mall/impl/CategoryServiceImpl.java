package com.cskaoyan.project.mall.service.mall.impl;

import com.cskaoyan.project.mall.domain.Category;
import com.cskaoyan.project.mall.domain.CategoryExample;
import com.cskaoyan.project.mall.mapper.CategoryMapper;
import com.cskaoyan.project.mall.service.mall.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/17 18:52
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> queryAllCategory() {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andIdIsNotNull();
        criteria.andDeletedEqualTo(false);
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        return categories;
    }

    @Override
    public List<Category> queryAllCategoryLevel1() {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andPidEqualTo(0);
        criteria.andDeletedEqualTo(false);
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        return categories;
    }

    @Override
    public List<Category> queryAllCategoryByPid(Integer pid) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andPidEqualTo(pid);
        criteria.andDeletedEqualTo(false);
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        return categories;
    }

    @Override
    public Category queryCategory(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertCategory(Category category) {
        int insert = categoryMapper.insert(category);
        if (insert == 1) {
            return category.getId();
        }
        return insert;
    }

    @Override
    public int updateCategoryById(Category category) {
        int update = categoryMapper.updateByPrimaryKey(category);
        return update;
    }

    @Override
    public int deleteCategoryById(Category category) {
        int delete = categoryMapper.deleteByPrimaryKey(category.getId());
        return delete;
    }

    @Override
    public int deleteCategoryByDeleted(Category category) {
        category.setDeleted(true);
        int update = categoryMapper.updateByPrimaryKey(category);
        return update;
    }
}
