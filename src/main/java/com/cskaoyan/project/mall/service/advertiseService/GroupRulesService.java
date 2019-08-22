package com.cskaoyan.project.mall.service.advertiseService;

import com.cskaoyan.project.mall.domain.GrouponRules;
import com.cskaoyan.project.mall.domain.GrouponRulesExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupRulesService {


    int deleteByPrimaryKey(Integer id);

    int insert(GrouponRules record);


    List<GrouponRules> selectByExample(GrouponRulesExample example);

    GrouponRules selectByPrimaryKey(Integer id);


    int updateByPrimaryKey(GrouponRules record);
}
