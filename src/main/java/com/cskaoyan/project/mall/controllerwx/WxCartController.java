package com.cskaoyan.project.mall.controllerwx;

import com.alibaba.fastjson.JSONObject;
import com.cskaoyan.project.mall.domain.Cart;
import com.cskaoyan.project.mall.domain.Goods;
import com.cskaoyan.project.mall.domain.GoodsProduct;
import com.cskaoyan.project.mall.domain.User;
import com.cskaoyan.project.mall.service.goods.GoodsProductService;
import com.cskaoyan.project.mall.service.goods.GoodsService;
import com.cskaoyan.project.mall.service.userService.CartService;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author 申涛涛
 * @date 2019/8/22 9:34
 */
@Controller
public class WxCartController {
    @Autowired
    CartService cartService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    GoodsProductService goodsProductService;

    @RequestMapping("/wx/cart/index")
    @ResponseBody
    public ResponseUtils queryCartIndex() {
        ResponseUtils responseUtils = new ResponseUtils();
        //获取当前登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Cart> cartList = new ArrayList<>();
        if (user != null) {
            //查询该用户的购物车信息
            cartList = cartService.queryAllCartByUserId(user.getId());
        }
        Map<String,Object> map = new HashMap<>();
        map.put("cartList",cartList);
        //选中的购物车数量
        int checkedGoodsCount = 0;
        //选中购物车的金额总数
        double checkedGoodsAmount = 0;
        //购物车中的商品总数
        int goodsCount = 0;
        //购物车的金额总数
        double goodsAmount = 0;
        //临时存储购物车中的 goodsId
        //List<Integer> tempList = new ArrayList();
        for (Cart cart : cartList) {
            if (cart.getChecked() == true) {
                checkedGoodsCount = checkedGoodsCount + cart.getNumber();
                checkedGoodsAmount = checkedGoodsAmount + cart.getPrice().doubleValue() * cart.getNumber();
            }
            goodsAmount = goodsAmount + cart.getNumber() * cart.getPrice().doubleValue();
            goodsCount = goodsCount + cart.getNumber();
        }
        Map<String,Object> cartTotal = new HashMap<>();
        cartTotal.put("checkedGoodsAmount",checkedGoodsAmount);
        cartTotal.put("checkedGoodsCount",checkedGoodsCount);
        cartTotal.put("goodsAmount",goodsAmount);
        cartTotal.put("goodsCount",goodsCount);

        map.put("cartTotal",cartTotal);

        if (user == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("用户信息丢失");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(map);
        }
        return responseUtils;
    }

    @RequestMapping("/wx/cart/checked")
    @ResponseBody
    public ResponseUtils queryCartChecked(Integer isChecked, Integer[] productIds) {
        ResponseUtils responseUtils = new ResponseUtils();
        //获取当前登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("用户未登录");
            return responseUtils;
        }
        if (productIds == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("productIds 信息丢失");
            return responseUtils;
        }
        for (Integer productId : productIds) {
            List<Cart> carts = cartService.queryCartByUserIdAndProductId(user.getId(), productId);
            //查到结果
            if (carts.size() != 0) {
                //设置选中状态
                if (isChecked == 1) {
                    carts.get(0).setChecked(true);
                } else if (isChecked == 0) {
                    carts.get(0).setChecked(false);
                }
            }
        }
        return queryCartIndex();
    }
    /*
     * @creator shentaotao
     * @creat date 2019/8/22 14:08
     * @param jsonObject    key： goodsId ; number ; productId
     * @return com.cskaoyan.project.mall.utils.ResponseUtils
     * @throws
     * @since
     */
    @RequestMapping("/wx/cart/add")
    @ResponseBody
    public ResponseUtils insertCart(@RequestBody JSONObject jsonObject) {
        ResponseUtils responseUtils = new ResponseUtils();
        //获取当前登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("用户未登录");
            return responseUtils;
        }
        Integer goodsId = (Integer) jsonObject.get("goodsId");
        Short number = (Short) jsonObject.get("number");
        Integer productId = (Integer) jsonObject.get("productId");
        if (number == null || productId == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("参数格式不正确");
            return responseUtils;
        }
        Goods goods = goodsService.queryById(goodsId);
        GoodsProduct goodsProduct = goodsProductService.queryGoodsProductById(productId);
        Date date = new Date();
        Cart cart = new Cart();
        cart.setNumber(number);
        cart.setUserId(user.getId());
        cart.setGoodsId(goodsId);
        cart.setGoodsSn(goods.getGoodsSn());
        cart.setGoodsName(goods.getName());
        cart.setProductId(productId);
        cart.setPrice(goodsProduct.getPrice());
        cart.setSpecifications(goodsProduct.getSpecifications());
        //默认不被选中
        cart.setChecked(false);
        cart.setPicUrl(goods.getPicUrl());
        cart.setAddTime(date);
        cart.setUpdateTime(date);
        cart.setDeleted(false);

        int insert = cartService.insertCart(cart);

        if (insert == 0) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("添加失败");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(cart.getId());
        }
        return responseUtils;
    }

    /*
     * @creator shentaotao
     * @creat date 2019/8/22 13:18
     * @param jsonObject    key： goodsId ; id ; number ; productId
     * @return com.cskaoyan.project.mall.utils.ResponseUtils
     * @throws
     * @since
     */
    @RequestMapping("/wx/cart/update")
    @ResponseBody
    public ResponseUtils updateCart(@RequestBody JSONObject jsonObject) {
        ResponseUtils responseUtils = new ResponseUtils();
        //获取当前登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("用户未登录");
            return responseUtils;
        }
        Integer goodsId = (Integer) jsonObject.get("goodsId");
        Integer id = (Integer) jsonObject.get("id");
        Short number = (Short) jsonObject.get("number");
        Integer productId = (Integer) jsonObject.get("productId");
        if (id == null || number == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("参数格式不正确");
            return responseUtils;
        }
        Cart cart = cartService.queryCart(id);
        //更新购物车中的商品数量
        cart.setNumber(number);
        int update = cartService.updateCart(cart);

        if (update == 0) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("修改失败");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(null);
        }
        return responseUtils;
    }

    /*
     * @creator shentaotao
     * @creat date 2019/8/22 14:35
     * @param productIds
     * @return com.cskaoyan.project.mall.utils.ResponseUtils
     * @throws
     * @since
     */
    @RequestMapping("/wx/cart/delete")
    @ResponseBody
    public ResponseUtils deleteCart(Integer[] productIds) {
        ResponseUtils responseUtils = new ResponseUtils();
        //获取当前登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("用户未登录");
            return responseUtils;
        }
        if (productIds == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("参数异常");
            return responseUtils;
        }
        int delete = 0;
        for (Integer productId : productIds) {
            List<Cart> carts = cartService.queryCartByUserIdAndProductId(user.getId(), productId);
            for (Cart cart : carts) {
                delete = cartService.deleteLogicCart(cart);
            }
        }

        if (delete == 0) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("修改失败");
        } else {
            return queryCartIndex();
        }
        return null;
    }

}
