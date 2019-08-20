package com.cskaoyan.project.mall.service.advertiseService;

import com.cskaoyan.project.mall.domain.CouponUser;
import com.cskaoyan.project.mall.domain.CouponUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponUserService {

    List<CouponUser> selectByExample(CouponUserExample example);

    int insert(CouponUser record);

    int updateByPrimaryKey(CouponUser record);
}
