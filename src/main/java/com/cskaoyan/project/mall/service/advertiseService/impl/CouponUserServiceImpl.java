package com.cskaoyan.project.mall.service.advertiseService.impl;

import com.cskaoyan.project.mall.domain.CouponUser;
import com.cskaoyan.project.mall.domain.CouponUserExample;
import com.cskaoyan.project.mall.mapper.CouponUserMapper;
import com.cskaoyan.project.mall.service.advertiseService.CouponUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CouponUserServiceImpl implements CouponUserService {
   @Autowired
    CouponUserMapper couponUserMapper;
    @Override
    public List<CouponUser> selectByExample(CouponUserExample example) {
        return couponUserMapper.selectByExample(example);
    }

    @Override
    public int insert(CouponUser record) {
        return couponUserMapper.insert(record);
    }

    @Override
    public int updateByPrimaryKey(CouponUser record) {
        return couponUserMapper.updateByPrimaryKey(record);
    }
}
