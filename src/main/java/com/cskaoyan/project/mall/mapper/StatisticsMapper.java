package com.cskaoyan.project.mall.mapper;

import java.util.List;
import java.util.Map;

public interface StatisticsMapper
{
    //统计user
    List<Map> queryUser();
    //统计order：
    List<Map> queryOrder();
    //统计goods：
    List<Map> queryGoods();
}
