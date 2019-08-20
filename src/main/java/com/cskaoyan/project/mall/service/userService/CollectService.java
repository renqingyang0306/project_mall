package com.cskaoyan.project.mall.service.userService;

import com.cskaoyan.project.mall.domain.Collect;
import com.cskaoyan.project.mall.domain.CollectExample;

import java.util.List;

public interface CollectService {
    List<Collect> selectByExample(CollectExample example);

    List<Collect> findAllCollect(int page,int limit,Integer userId,Integer valueId);
}
