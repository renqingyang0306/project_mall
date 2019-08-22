package com.cskaoyan.project.mall.service.userService;

import com.cskaoyan.project.mall.controller.goods.vo.ResponseVO;
import com.cskaoyan.project.mall.controllerwx.orders.vo.EchoFootprint;
import com.cskaoyan.project.mall.controllerwx.orders.vo.FootprintVO;
import com.cskaoyan.project.mall.domain.Footprint;
import com.cskaoyan.project.mall.domain.FootprintExample;
import com.cskaoyan.project.mall.mapper.FootprintMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FootprintServiceImpl implements FootprintService {
    @Autowired
    FootprintMapper footprintMapper;
    @Override
    public List<Footprint> selectByExample(FootprintExample example) {
        return footprintMapper.selectByExample(example);
    }

    @Override
    public List<Footprint> findAllFootprint(int page, int limit, Integer userId, Integer goodsId) {
        PageHelper.startPage(page,limit);
        FootprintExample example = new FootprintExample();
        List<Footprint> footprints = null;
        if(userId == null && goodsId == null){
            footprints = footprintMapper.selectByExample(example);
        } else if(userId != null && goodsId == null){
            example.createCriteria().andUserIdEqualTo(userId);
            footprints = footprintMapper.selectByExample(example);
        } else if(userId == null && goodsId != null){
            example.createCriteria().andGoodsIdEqualTo(goodsId);
            footprints = footprintMapper.selectByExample(example);
        } else {
            example.createCriteria().andUserIdEqualTo(userId)
                                    .andGoodsIdEqualTo(goodsId);
            footprints = footprintMapper.selectByExample(example);
        }
        return footprints;
    }

    @Override
    public ResponseVO<FootprintVO> findFootprintByUid(int page, int size, Integer uid) {
        PageHelper.startPage(page, size);
        List<EchoFootprint> echoFootprints = footprintMapper.echoFootprintListByUid(uid);
        PageInfo<EchoFootprint> pageInfo = new PageInfo<>(echoFootprints);
        FootprintVO footprintVO = new FootprintVO(pageInfo.getList(), pageInfo.getTotal());
        ResponseVO responseVO = new ResponseVO(footprintVO, "成功", 0);
        return responseVO;
    }

}
