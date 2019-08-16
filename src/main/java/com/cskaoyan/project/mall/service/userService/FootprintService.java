package com.cskaoyan.project.mall.service.userService;

import com.cskaoyan.project.mall.domain.Footprint;
import com.cskaoyan.project.mall.domain.FootprintExample;

import java.util.List;

public interface FootprintService {
    List<Footprint> selectByExample(FootprintExample example);

    List<Footprint> findAllFootprint(int page,int limit,Integer userId,Integer goodsId);
}
