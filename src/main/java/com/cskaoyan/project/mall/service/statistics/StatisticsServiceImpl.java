package com.cskaoyan.project.mall.service.statistics;

import com.cskaoyan.project.mall.mapper.StatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service
public class StatisticsServiceImpl implements StatisticsService
{
    @Resource
    StatisticsMapper statisticsMapper;
    @Override
    public List<Map> queryUser()
    {
        return statisticsMapper.queryUser();
    }

    @Override
    public List<Map> queryOrder()
    {
        return statisticsMapper.queryOrder();
    }

    @Override
    public List<Map> queryGoods()
    {
        return statisticsMapper.queryGoods();
    }
}
