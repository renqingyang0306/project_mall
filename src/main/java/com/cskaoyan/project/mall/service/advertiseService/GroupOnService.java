package com.cskaoyan.project.mall.service.advertiseService;

import com.cskaoyan.project.mall.domain.Groupon;
import com.cskaoyan.project.mall.domain.GrouponExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupOnService {


    int deleteByPrimaryKey(Integer id);

    int insert(Groupon record);


    List<Groupon> selectByExample(GrouponExample example);

    Groupon selectByPrimaryKey(Integer id);


    int updateByPrimaryKey(Groupon record);
}
