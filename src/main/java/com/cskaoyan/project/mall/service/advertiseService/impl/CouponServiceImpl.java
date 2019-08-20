package com.cskaoyan.project.mall.service.advertiseService.impl;

import com.cskaoyan.project.mall.domain.Coupon;
import com.cskaoyan.project.mall.domain.CouponExample;
import com.cskaoyan.project.mall.mapper.CouponMapper;
import com.cskaoyan.project.mall.service.advertiseService.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    CouponMapper couponMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return couponMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Coupon record) {
        return couponMapper.insert(record);
    }

    @Override
    public List<Coupon> selectByExample(CouponExample example) {
        return couponMapper.selectByExample(example);
    }

    @Override
    public Coupon selectByPrimaryKey(Integer id) {
        return couponMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Coupon record) {
        return couponMapper.updateByPrimaryKey(record);
    }
}
