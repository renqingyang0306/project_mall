package com.cskaoyan.project.mall.service.advertiseService;

import com.cskaoyan.project.mall.domain.Ad;
import com.cskaoyan.project.mall.domain.AdExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdvertiseService {
    /**
     * 用主键删除
     * */

    int deleteByPrimaryKey(Integer id);
   /**
    * 插入单个
    * */
    int insert(Ad record);
    /**
     * 查询list
     * */

    List<Ad> selectByExample(AdExample example);
    /**
     * 查询单个
     * */
    Ad selectByPrimaryKey(Integer id);
    /**
     * 更新单个
     * */
    int updateByPrimaryKey(Ad record);
}
