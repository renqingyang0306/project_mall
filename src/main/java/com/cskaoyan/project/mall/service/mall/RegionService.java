package com.cskaoyan.project.mall.service.mall;

import com.cskaoyan.project.mall.domain.Region;
import com.cskaoyan.project.mall.domain.RegionExample;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/16 15:47
 */
public interface RegionService {
    List<Region> queryAllRegion();

    long countById();

    int deleteByPrimaryKey(Integer id);

    int insert(Region record);

    List<Region> selectByPid(Integer pid);

    List<Region> selectLikeCode(String code);

    List<Region> selectByExample(RegionExample example);

    int updateByPrimaryKey(Region record);
}
