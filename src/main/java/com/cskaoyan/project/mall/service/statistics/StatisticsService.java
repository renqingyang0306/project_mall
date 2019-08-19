package com.cskaoyan.project.mall.service.statistics;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public interface StatisticsService
{
    //统计user
    List<Map> queryUser();
    //统计order：
    List<Map> queryOrder();
    //统计goods：
    List<Map> queryGoods();
}
