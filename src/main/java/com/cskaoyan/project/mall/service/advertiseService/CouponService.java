package com.cskaoyan.project.mall.service.advertiseService;

import com.cskaoyan.project.mall.domain.Coupon;
import com.cskaoyan.project.mall.domain.CouponExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponService {

    int deleteByPrimaryKey(Integer id);

    int insert(Coupon record);

    List<Coupon> selectByExample(CouponExample example);

    Coupon selectByPrimaryKey(Integer id);


    int updateByPrimaryKey(Coupon record);

}
