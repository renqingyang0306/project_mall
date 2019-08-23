package com.cskaoyan.project.mall.service.userService;

import com.cskaoyan.project.mall.domain.Cart;
import com.cskaoyan.project.mall.domain.CartExample;
import com.cskaoyan.project.mall.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/21 22:44
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartMapper cartMapper;

    @Override
    public List<Cart> queryAllCart() {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        return carts;
    }

    @Override
    public List<Cart> queryAllCartByUserId(Integer userId) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(userId);
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        return carts;
    }

    @Override
    public List<Cart> queryCartByUserIdAndProductId(Integer userId, Integer productId) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(userId);
        criteria.andProductIdEqualTo(productId);
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        return carts;
    }

    @Override
    public List<Cart> queryCartByUserIdAndChecked(Integer userId, Boolean checked) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(userId);
        criteria.andCheckedEqualTo(checked);
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        return carts;
    }

    @Override
    public Cart queryCart(Integer id) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andIdEqualTo(id);
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        if (carts.size() >= 1) {
            return carts.get(0);
        }
        return null;
    }

    @Override
    public int insertCart(Cart cart) {
        return cartMapper.insert(cart);
    }

    @Override
    public int updateCart(Cart cart) {
        return cartMapper.updateByPrimaryKey(cart);
    }

    @Override
    public int deleteRealCart(Integer id) {
        return cartMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteLogicCart(Cart cart) {
        cart.setDeleted(true);
        return cartMapper.updateByPrimaryKey(cart);
    }

    /*
     * description: 下单后删掉购物车里的商品
     * version: 1.0
     * date: 2019/8/22 22:09
     * author: du
     * @Param: [uid]
     * @return: void
     */
    @Override
    public void clearGoods(Integer uid) {
        CartExample example = new CartExample();
        example.or().andUserIdEqualTo(uid).andCheckedEqualTo(true);
        Cart cart = new Cart();
        cart.setDeleted(true);
        cartMapper.updateByExampleSelective(cart, example);
    }
}
