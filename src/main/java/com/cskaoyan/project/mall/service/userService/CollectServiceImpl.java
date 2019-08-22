package com.cskaoyan.project.mall.service.userService;

import com.cskaoyan.project.mall.domain.Collect;
import com.cskaoyan.project.mall.domain.CollectExample;
import com.cskaoyan.project.mall.mapper.CollectMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CollectServiceImpl implements CollectService {
    @Autowired
    CollectMapper collectMapper;
    @Override
    public List<Collect> selectByExample(CollectExample example) {
        return collectMapper.selectByExample(example);
    }

    @Override
    public List<Collect> findAllCollect(int page, int limit,Integer userId,Integer valueId) {
        PageHelper.startPage(page,limit);
        CollectExample example = new CollectExample();
        List<Collect> collects = null;
        if(userId == null && valueId == null){
            collects = collectMapper.selectByExample(example);
        } else if(userId != null && valueId == null){
            example.createCriteria().andUserIdEqualTo(userId);
            collects = collectMapper.selectByExample(example);
        } else if(userId == null && valueId != null){
            example.createCriteria().andValueIdEqualTo(valueId);
            collects = collectMapper.selectByExample(example);
        } else {
            example.createCriteria().andValueIdEqualTo(valueId)
                                    .andUserIdEqualTo(userId);
            collects = collectMapper.selectByExample(example);
        }
        return collects;
    }

    @Override
    public List<Collect> queryByType(Integer userId, Byte type, Integer page, Integer i) {
        CollectExample collectExample = new CollectExample();
        CollectExample.Criteria criteria = collectExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andTypeEqualTo(type);
        //collectExample.setOrderByClause("add_time desc");
        int limit = 10;
        PageHelper.startPage(page,limit);
        return collectMapper.selectByExample(collectExample);
    }
}
