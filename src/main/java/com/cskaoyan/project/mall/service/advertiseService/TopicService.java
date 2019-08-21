package com.cskaoyan.project.mall.service.advertiseService;

import com.cskaoyan.project.mall.domain.Topic;
import com.cskaoyan.project.mall.domain.TopicExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TopicService {


    int deleteByPrimaryKey(Integer id);

    int insert(Topic record);


    List<Topic> selectByExample(TopicExample example);

    Topic selectByPrimaryKey(Integer id);


    int updateByPrimaryKey(Topic record);
}
