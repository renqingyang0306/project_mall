package com.cskaoyan.project.mall.service.mallConfig;

import com.cskaoyan.project.mall.domain.System;
import com.cskaoyan.project.mall.domain.SystemExample;
import com.cskaoyan.project.mall.mapper.SystemMapper;

import javax.annotation.Resource;
import java.util.List;

public class MallConfigServiceImpl implements MallConfigService
{
    @Resource
    SystemMapper systemMapper;
    @Override
    public long countByExample(SystemExample example)
    {
        return systemMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(SystemExample example)
    {
        return systemMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer id)
    {
        return systemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(System record)
    {
        return systemMapper.insert(record);
    }

    @Override
    public int insertSelective(System record)
    {
        return systemMapper.insertSelective(record);
    }

    @Override
    public List<System> selectByExample(SystemExample example)
    {
        return systemMapper.selectByExample(example);
    }

    @Override
    public System selectByPrimaryKey(Integer id)
    {
        return systemMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(System record, SystemExample example)
    {
        return systemMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(System record, SystemExample example)
    {
        return systemMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(System record)
    {
        return systemMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(System record)
    {
        return systemMapper.updateByPrimaryKey(record);
    }
}
