package com.cskaoyan.project.mall.service.goods;

import com.cskaoyan.project.mall.domain.Categorylist;
import com.cskaoyan.project.mall.domain.Item;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/20
 * @time 15:53
 */
public interface CartAndBrandService {

    List<Categorylist> queryCartList();

    int queryPidById(int categoryId1);

    List<Item> queryBrandList();
}
