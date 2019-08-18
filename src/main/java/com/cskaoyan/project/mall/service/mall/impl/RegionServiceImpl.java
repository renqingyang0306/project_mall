package com.cskaoyan.project.mall.service.mall.impl;

import com.cskaoyan.project.mall.domain.Region;
import com.cskaoyan.project.mall.domain.RegionExample;
import com.cskaoyan.project.mall.mapper.RegionMapper;
import com.cskaoyan.project.mall.service.mall.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/16 15:50
 */
@Service
public class RegionServiceImpl implements RegionService {
    @Autowired
    RegionMapper regionMapper;

    @Override
    public List<Region> queryAllRegion() {
        RegionExample regionExample = new RegionExample();
        List<Region> regions = regionMapper.selectByExample(regionExample);
        return regions;
    }

    @Override
    public long countById() {
        RegionExample regionExample = new RegionExample();
        regionExample.createCriteria().andIdIsNotNull();
        long count = regionMapper.countByExample(regionExample);
        return count;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {

        return regionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Region record) {
        return regionMapper.insert(record);
    }

    @Override
    public List<Region> selectByPid(Integer pid) {
        List<Region> regions = regionMapper.selectByPid(pid);
        return regions;
    }

    @Override
    public List<Region> selectLikeCode(String code) {
        code = code + "__";
        List<Region> regions = regionMapper.selectByLikeCode(code);
        return regions;
    }


    @Override
    public int updateByPrimaryKey(Region record) {
        return regionMapper.updateByPrimaryKey(record);
    }
}
