package com.cskaoyan.project.mall.service.advertiseService.impl;

import com.cskaoyan.project.mall.domain.Groupon;
import com.cskaoyan.project.mall.domain.GrouponExample;
import com.cskaoyan.project.mall.mapper.GrouponMapper;
import com.cskaoyan.project.mall.service.advertiseService.GroupOnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GroupOnServiceImpl implements GroupOnService {
    @Autowired
    GrouponMapper grouponMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return grouponMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Groupon record) {
        return grouponMapper.insert(record);
    }

    @Override
    public List<Groupon> selectByExample(GrouponExample example) {
        return grouponMapper.selectByExample(example);
    }

    @Override
    public Groupon selectByPrimaryKey(Integer id) {
        return grouponMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Groupon record) {
        return grouponMapper.updateByPrimaryKey(record);
    }
}
