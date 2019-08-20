package com.cskaoyan.project.mall.service.advertiseService.impl;

import com.cskaoyan.project.mall.domain.GrouponRules;
import com.cskaoyan.project.mall.domain.GrouponRulesExample;
import com.cskaoyan.project.mall.mapper.GrouponRulesMapper;
import com.cskaoyan.project.mall.service.advertiseService.GroupRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GroupRulesServiceImpl implements GroupRulesService {
    @Autowired
    GrouponRulesMapper grouponRulesMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return grouponRulesMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(GrouponRules record) {
        return grouponRulesMapper.insert(record);
    }

    @Override
    public List<GrouponRules> selectByExample(GrouponRulesExample example) {
        return grouponRulesMapper.selectByExample(example);
    }

    @Override
    public GrouponRules selectByPrimaryKey(Integer id) {
        return grouponRulesMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(GrouponRules record) {
        return grouponRulesMapper.updateByPrimaryKey(record);
    }
}
