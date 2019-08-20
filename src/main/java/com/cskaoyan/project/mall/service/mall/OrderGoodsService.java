package com.cskaoyan.project.mall.service.mall;

import com.cskaoyan.project.mall.domain.OrderGoods;
import com.cskaoyan.project.mall.domain.OrderGoodsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/20 20:14
 */
public interface OrderGoodsService {
    long countOrderGoodsServiceById();

    int insert(OrderGoods orderGoods);

    List<OrderGoods> queryAllOrderGoods();

    List<OrderGoods> queryAllOrderGoodsByOrderId(Integer orderId);

    List<OrderGoods> selectByExample(OrderGoodsExample example);

    OrderGoods selectOrderGoodsById(Integer id);

    int updateOrderGoodsById(OrderGoods orderGoods);

    int deleteRealOrderGoodsById(Integer id);

    int deleteLogicOrderGoodsByDeleted(OrderGoods orderGoods);

}
