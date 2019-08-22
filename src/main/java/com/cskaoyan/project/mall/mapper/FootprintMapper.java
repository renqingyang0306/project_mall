package com.cskaoyan.project.mall.mapper;


import com.cskaoyan.project.mall.controllerwx.orders.vo.EchoFootprint;
import com.cskaoyan.project.mall.domain.Footprint;
import com.cskaoyan.project.mall.domain.FootprintExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FootprintMapper {
    long countByExample(FootprintExample example);

    int deleteByExample(FootprintExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Footprint record);

    int insertSelective(Footprint record);

    List<Footprint> selectByExample(FootprintExample example);

    Footprint selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Footprint record, @Param("example") FootprintExample example);

    int updateByExample(@Param("record") Footprint record, @Param("example") FootprintExample example);

    int updateByPrimaryKeySelective(Footprint record);

    int updateByPrimaryKey(Footprint record);

    List<EchoFootprint> echoFootprintListByUid(Integer uid);
}