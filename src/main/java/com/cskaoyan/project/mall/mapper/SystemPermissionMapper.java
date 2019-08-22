package com.cskaoyan.project.mall.mapper;

import com.cskaoyan.project.mall.domain.SystemPermission;
import com.cskaoyan.project.mall.domain.SystemPermissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemPermissionMapper {
    long countByExample(SystemPermissionExample example);

    int deleteByExample(SystemPermissionExample example);

    int deleteByPrimaryKey(String id);

    int insert(SystemPermission record);

    int insertSelective(SystemPermission record);

    List<SystemPermission> selectByExample(SystemPermissionExample example);

    SystemPermission selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SystemPermission record, @Param("example") SystemPermissionExample example);

    int updateByExample(@Param("record") SystemPermission record, @Param("example") SystemPermissionExample example);

    int updateByPrimaryKeySelective(SystemPermission record);

    int updateByPrimaryKey(SystemPermission record);
}