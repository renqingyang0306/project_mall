package com.cskaoyan.project.mall.service.goods;

import com.cskaoyan.project.mall.controller.goods.vo.PageVO;
import com.cskaoyan.project.mall.controller.goods.vo.ResponseVO;
import com.cskaoyan.project.mall.domain.Comment;
import com.cskaoyan.project.mall.domain.Goods;
import com.cskaoyan.project.mall.domain.GoodsExample;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/16
 * @time 15:45
 */

public interface GoodsService {

    List<Goods> selectByExample(GoodsExample example);

    ResponseVO<PageVO<Goods>> query(int page, int limit);

    int insert(Goods goods);

    Goods queryById(int id);

    int deleteById(int id);

    int updateByPrimaryKey(Goods goods);

    ResponseVO<PageVO<Goods>> fuzzyQuery(int page, int limit, String goodsSn, String name);
    //2019/08/21 13:35 申涛涛
    List<Goods> queryPageOrderByExample(Integer categoryId, Integer page, Integer size);
    long selectCountGoods();

}
