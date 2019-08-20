package com.cskaoyan.project.mall.service.goods;

import com.cskaoyan.project.mall.domain.Categorylist;
import com.cskaoyan.project.mall.domain.Item;
import com.cskaoyan.project.mall.mapper.CartAndBrandMapper;
import com.cskaoyan.project.mall.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/20
 * @time 15:54
 */
@Service
public class CartAndBrandServiceImpl implements CartAndBrandService {

    @Autowired
    CartAndBrandMapper cartAndBrandMapper;
    @Autowired
    CategoryMapper categoryMapper;


    @Override
    public List<Categorylist> queryCartList() {
        //得到pid=0的大分类
        List<Categorylist> categorylists = cartAndBrandMapper.queryCartList();

        //根据id = pid 进行children 的存储
        for (Categorylist categorylist : categorylists) {
            int value = categorylist.getValue();
            //根据id（value）进行分类下的子分类查询
            List<Item> items = cartAndBrandMapper.queryCartListByPid(value);
            categorylist.setChildren(items);
        }

        return categorylists;
    }

    @Override
    public int queryPidById(int id) {
        int pid = categoryMapper.queryPidById(id);
        return pid;
    }

    @Override
    public List<Item> queryBrandList() {
        List<Item> brands = cartAndBrandMapper.queryBrandList();
        return brands;
    }
}
