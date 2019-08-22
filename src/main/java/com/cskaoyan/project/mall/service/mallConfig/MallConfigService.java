package com.cskaoyan.project.mall.service.mallConfig;

import com.cskaoyan.project.mall.domain.System;
import com.cskaoyan.project.mall.domain.SystemExample;
import com.cskaoyan.project.mall.utils.mallConfig.SystemConfigBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MallConfigService
{
    long countByExample(SystemExample example);

    int deleteByExample(SystemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(System record);

    int insertSelective(System record);

    List<System> selectByExample(SystemExample example);

    System selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") System record, @Param("example") SystemExample example);

    int updateByExample(@Param("record") System record, @Param("example") SystemExample example);

    int updateByPrimaryKeySelective(System record);

    int updateByPrimaryKey(System record);
    //更新商场配置：
    int updateByMallConfig(SystemConfigBean systemConfigBean);
    //更新运费：
    int updateByFreightConfig(SystemConfigBean systemConfigBean);
    //更新订单：
    int updateByOrderConfig(SystemConfigBean systemConfigBean);
    //更新微信：
    int updateBywxConfig(SystemConfigBean systemConfigBean);

}
