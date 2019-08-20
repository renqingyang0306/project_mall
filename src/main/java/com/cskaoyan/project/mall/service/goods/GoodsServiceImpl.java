package com.cskaoyan.project.mall.service.goods;

import com.cskaoyan.project.mall.controller.goods.vo.PageVO;
import com.cskaoyan.project.mall.controller.goods.vo.ResponseVO;
import com.cskaoyan.project.mall.domain.Goods;
import com.cskaoyan.project.mall.domain.GoodsExample;
import com.cskaoyan.project.mall.mapper.GoodsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/16
 * @time 15:45
 */
@Service
public class GoodsServiceImpl implements GoodsService{
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public List<Goods> selectByExample(GoodsExample example) {
        List<Goods> goods = goodsMapper.selectByExample(example);
        return goods;
    }

    @Override
    public ResponseVO<PageVO<Goods>> query(int page, int limit) {
        /*使用分页插件查询,必须用在查询语句前面*/
        PageHelper.startPage(page,limit);
        /*查询，返回list类型的数据*/
        List<Goods> goods =  goodsMapper.queryAll();
        PageInfo<Goods> pageInfo = new PageInfo<>(goods);
        /*得到total，把得到的list数据塞到items*/
        PageVO<Goods> pageVO = new PageVO<>(pageInfo.getTotal(), pageInfo.getList());
        /*把pageVO当作data塞进去，赋值成功，赋值状态值0*/
        ResponseVO<PageVO<Goods>> responseVO = new ResponseVO<>(pageVO, "成功", 0);
        return responseVO;
    }

    @Override
    public int insert(Goods goods) {
        return goodsMapper.insert(goods);
    }

    @Override
    public Goods queryById(int id) {
        return goodsMapper.queryById(id);
    }

    @Override
    public int deleteById(int id) {
        int i = goodsMapper.deleteByPrimaryKey(id);
        return i;
    }

    @Override
    public int updateByPrimaryKey(Goods goods) {
        int i = goodsMapper.update(goods);
        return i;
    }

    @Override
    public ResponseVO<PageVO<Goods>> fuzzyQuery(int page, int limit, String goodsSn, String name) {
        PageHelper.startPage(page, limit);
        List<Goods> goods = goodsMapper.fuzzyQuery("%" + goodsSn + "%", "%" + name + "%");
        PageInfo<Goods> pageInfo = new PageInfo<>(goods);
        PageVO<Goods> pageVO = new PageVO<>(pageInfo.getTotal(), pageInfo.getList());
        ResponseVO<PageVO<Goods>> responseVO = new ResponseVO<>(pageVO, "成功", 0);
        return responseVO;
    }
}
