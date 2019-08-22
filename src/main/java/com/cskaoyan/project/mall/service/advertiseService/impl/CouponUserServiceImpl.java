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
    public List<CouponUser> queryCouponUserByUserId(Integer userId) {
        CouponUserExample couponUserExample = new CouponUserExample();
        CouponUserExample.Criteria criteria = couponUserExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(userId);
        List<CouponUser> couponUsers = couponUserMapper.selectByExample(couponUserExample);
        return couponUsers;
    }

    @Override
    public CouponUser queryCouponUserById(Integer id) {
        CouponUserExample couponUserExample = new CouponUserExample();
        CouponUserExample.Criteria criteria = couponUserExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andIdEqualTo(id);
        List<CouponUser> couponUsers = couponUserMapper.selectByExample(couponUserExample);
        if (couponUsers.size() > 0) {
            return couponUsers.get(0);
        }
        return null;
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
