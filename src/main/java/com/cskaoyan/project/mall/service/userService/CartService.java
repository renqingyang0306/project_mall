package com.cskaoyan.project.mall.service.userService;

import com.cskaoyan.project.mall.domain.Cart;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/21 22:40
 */
public interface CartService {
    List<Cart> queryAllCart();
    List<Cart> queryAllCartByUserId(Integer userId);
    List<Cart> queryCartByUserIdAndProductId(Integer userId, Integer productId);
    List<Cart> queryCartByUserIdAndChecked(Integer userId, Boolean checked);

    Cart queryCart(Integer id);

    int insertCart(Cart cart);
    int updateCart(Cart cart);
    int deleteRealCart(Integer id);
    int deleteLogicCart(Cart cart);

    void clearGoods(Integer uid);
}
