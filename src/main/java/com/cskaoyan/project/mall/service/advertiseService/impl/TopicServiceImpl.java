package com.cskaoyan.project.mall.service.advertiseService.impl;

import com.cskaoyan.project.mall.domain.Topic;
import com.cskaoyan.project.mall.domain.TopicExample;
import com.cskaoyan.project.mall.mapper.TopicMapper;
import com.cskaoyan.project.mall.service.advertiseService.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
   @Autowired
    TopicMapper topicMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return topicMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Topic record) {
        return topicMapper.insert(record);
    }

    @Override
    public List<Topic> selectByExample(TopicExample example) {
        return topicMapper.selectByExample(example);
    }

    @Override
    public Topic selectByPrimaryKey(Integer id) {
        return topicMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Topic record) {
        return topicMapper.updateByPrimaryKey(record);
    }
}
