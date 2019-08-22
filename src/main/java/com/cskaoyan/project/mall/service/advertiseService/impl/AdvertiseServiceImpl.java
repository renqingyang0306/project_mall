package com.cskaoyan.project.mall.service.advertiseService.impl;

import com.cskaoyan.project.mall.domain.Ad;
import com.cskaoyan.project.mall.domain.AdExample;
import com.cskaoyan.project.mall.mapper.AdMapper;
import com.cskaoyan.project.mall.service.advertiseService.AdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertiseServiceImpl  implements AdvertiseService {
    @Autowired(required = false)
    AdMapper adMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return adMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Ad record) {
        return adMapper.insert(record);
    }

    @Override
    public List<Ad> selectByExample(AdExample example) {
        return adMapper.selectByExample(example);
    }

    @Override
    public Ad selectByPrimaryKey(Integer id) {
        return adMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Ad record) {
        return adMapper.updateByPrimaryKey(record);
    }
}
