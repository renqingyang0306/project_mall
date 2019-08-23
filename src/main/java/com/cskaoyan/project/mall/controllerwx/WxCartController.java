package com.cskaoyan.project.mall.controllerwx;

import com.alibaba.fastjson.JSONObject;
import com.cskaoyan.project.mall.domain.*;
import com.cskaoyan.project.mall.service.advertiseService.CouponService;
import com.cskaoyan.project.mall.service.advertiseService.CouponUserService;
import com.cskaoyan.project.mall.service.goods.GoodsProductService;
import com.cskaoyan.project.mall.service.goods.GoodsService;
import com.cskaoyan.project.mall.service.userService.AddressService;
import com.cskaoyan.project.mall.service.userService.CartService;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
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
    @Autowired
    AddressService addressService;
    @Autowired
    CouponUserService couponUserService;
    @Autowired
    CouponService couponService;

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
        Map<String, Object> map = new HashMap<>();
        map.put("cartList", cartList);
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
        Map<String, Object> cartTotal = new HashMap<>();
        cartTotal.put("checkedGoodsAmount", checkedGoodsAmount);
        cartTotal.put("checkedGoodsCount", checkedGoodsCount);
        cartTotal.put("goodsAmount", goodsAmount);
        cartTotal.put("goodsCount", goodsCount);

        map.put("cartTotal", cartTotal);

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

    /**
     * @param jsonObject Integer isChecked, Integer[] productIds
     * @return com.cskaoyan.project.mall.utils.ResponseUtils
     * @throws
     * @creator shentaotao
     * @creat date 2019/8/22 15:06
     * @since
     */
    @RequestMapping("/wx/cart/checked")
    @ResponseBody
    public ResponseUtils queryCartChecked(@RequestBody JSONObject jsonObject) {
        ResponseUtils responseUtils = new ResponseUtils();
        //获取当前登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("用户未登录");
            return responseUtils;
        }
        Integer isChecked = (Integer) jsonObject.get("isChecked");
        List productIds = (List) jsonObject.get("productIds");
        if (productIds == null || isChecked == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("productIds 信息丢失");
            return responseUtils;
        }
        for (int i = 0; i < productIds.size(); i++) {
            Integer productId = (Integer) productIds.get(i);
            List<Cart> carts = cartService.queryCartByUserIdAndProductId(user.getId(), productId);
            //查到结果
            if (carts.size() > 0) {
                Cart cart = carts.get(0);
                //设置选中状态
                if (isChecked == 1) {
                    cart.setChecked(true);
                } else if (isChecked == 0) {
                    cart.setChecked(false);
                }
                int updateCart = cartService.updateCart(cart);
                if (updateCart == 0) {
                    responseUtils.setErrno(401);
                    responseUtils.setErrmsg("选中失败");
                    return responseUtils;
                }
            }
        }
        return queryCartIndex();
    }

    /**
     * @param cartId
     * @param addressId      用户地址
     * @param couponId
     * @param grouponRulesId
     * @return com.cskaoyan.project.mall.utils.ResponseUtils
     * @throws
     * @creator shentaotao
     * @creat date 2019/8/22 15:52
     * @since
     */
    @RequestMapping("/wx/cart/checkout")
    @ResponseBody
    public ResponseUtils queryCartChecked(Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId) {
        ResponseUtils responseUtils = new ResponseUtils();
        //获取当前登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("用户未登录");
            return responseUtils;
        }
        // 什么？ cart 没用上？ 我咋知道这是干嘛的，传进来了不能浪费，给个查询吧
        Cart cart = new Cart();
        Address checkedAddress = null;
        if (addressId != null && addressId > 0) {
            checkedAddress = addressService.queryAddressById(addressId);
        }
        //订单金额
        BigDecimal orderTotalPrice = new BigDecimal(0);
        List<Cart> checkedGoodsList = new ArrayList<>();
        if (cartId == null || cartId <= 0) {
            //购物车中选中的商品
            checkedGoodsList = cartService.queryCartByUserIdAndChecked(user.getId(), true);
        } else {
            cart = cartService.selectByPrimaryKey(cartId);
            checkedGoodsList.add(cart);
        }
        if (checkedGoodsList != null) {
            for (Cart cart1 : checkedGoodsList) {
                Goods goods = goodsService.queryById(cart1.getGoodsId());
                if (goods != null) {
                    BigDecimal retailPrice = (goods.getRetailPrice()).multiply(BigDecimal.valueOf(cart1.getNumber()));
                    orderTotalPrice = orderTotalPrice.add(retailPrice);
                }
            }
        }
        Coupon coupon = null;
        if (couponId != null && couponId > 0) {
            //查询当前优惠券的详情
            coupon = couponService.selectByPrimaryKey(couponId);
        }
        //查询所有可用的优惠券
        List<CouponUser> couponUsers = couponUserService.queryCouponUserByUserId(user.getId());
        //可用优惠券数量
        Integer availableCouponLength = couponUsers.size();
        //优惠金额
        BigDecimal couponPrice = new BigDecimal(0);
        if (coupon != null) {
            BigDecimal discount = coupon.getDiscount();
            couponPrice = discount;
        }
        //运费
        BigDecimal freightPrice = new BigDecimal(10);
        //实付金额  subtract 是减法
        BigDecimal actualPrice = new BigDecimal(0);
        if (orderTotalPrice != null) {
            actualPrice = orderTotalPrice.subtract(couponPrice).add(freightPrice);
        }
        //商品总价
        BigDecimal goodsTotalPrice = orderTotalPrice;
        //团购金额
        BigDecimal grouponPrice = new BigDecimal(0);

        Map<String, Object> map = new HashMap<>();

        map.put("checkedGoodsList", checkedGoodsList);
        map.put("checkedAddress", checkedAddress);
        map.put("availableCouponLength", availableCouponLength);
        map.put("couponId", couponId);
        map.put("couponPrice", couponPrice);
        map.put("actualPrice", actualPrice);
        map.put("freightPrice", freightPrice);
        map.put("goodsTotalPrice", goodsTotalPrice);
        map.put("grouponPrice", grouponPrice);
        map.put("orderTotalPrice", orderTotalPrice);
        map.put("grouponRulesId", grouponRulesId);
        //为什么还要判断 user ？ 因为不知道判断什么了
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


    /**
     * @param jsonObject key： goodsId ; number ; productId
     * @return com.cskaoyan.project.mall.utils.ResponseUtils
     * @throws
     * @creator shentaotao
     * @creat date 2019/8/22 14:08
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
        Integer number = (Integer) jsonObject.get("number");
        Integer productId = (Integer) jsonObject.get("productId");
        if (number == null || productId == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("参数格式不正确");
            return responseUtils;
        }
        Cart cart = new Cart();
        //查询数据库 cart 是否已存在该条购物信息
        List<Cart> carts = cartService.queryCartByUserIdAndProductId(user.getId(), productId);
        int falg = 0;
        if (carts.size() > 0) {
            cart = carts.get(0);
            number += cart.getNumber();
            cart.setNumber(number.shortValue());
            falg = cartService.updateCart(cart);

        } else {
            Goods goods = goodsService.queryById(goodsId);
            GoodsProduct goodsProduct = goodsProductService.queryGoodsProductById(productId);
            Date date = new Date();
            cart.setNumber(number.shortValue());
            cart.setUserId(user.getId());
            cart.setGoodsId(goodsId);
            cart.setGoodsSn(goods.getGoodsSn());
            cart.setGoodsName(goods.getName());
            cart.setProductId(productId);
            cart.setPrice(goodsProduct.getPrice());
            cart.setSpecifications(Arrays.toString(goodsProduct.getSpecifications()));
            //默认不被选中
            cart.setChecked(false);
            cart.setPicUrl(goods.getPicUrl());
            cart.setAddTime(date);
            cart.setUpdateTime(date);
            cart.setDeleted(false);

            falg = cartService.insertCart(cart);
        }
        //查询 cart 中的商品数量
        List<Cart> carts1 = cartService.queryAllCartByUserId(user.getId());
        if (falg == 0) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("添加失败");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(carts1.size());
        }
        return responseUtils;
    }

    /**
     * @param jsonObject Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId
     * @return com.cskaoyan.project.mall.utils.ResponseUtils
     * @throws
     * @creator shentaotao
     * @creat date 2019/8/22 19:23
     * @since
     */
    @RequestMapping("/wx/cart/fastadd")
    @ResponseBody
    public ResponseUtils insertFastCart(@RequestBody JSONObject jsonObject) {
        ResponseUtils responseUtils = new ResponseUtils();
        //获取当前登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("用户未登录");
            return responseUtils;
        }
        //获取传入的参数
        Integer cartId = (Integer) jsonObject.get("cartId");
        Integer goodsId = (Integer) jsonObject.get("goodsId");
        Integer number = (Integer) jsonObject.get("number");
        Integer productId = (Integer) jsonObject.get("productId");

        if (number == null || productId == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("参数格式不正确");
            return responseUtils;
        }
        Cart cart = new Cart();
        int falg = 0;
        Goods goods = null;
        if (goodsId != null || goodsId != 0) {
            goods = goodsService.queryById(goodsId);
        }
        GoodsProduct goodsProduct = goodsProductService.queryGoodsProductById(productId);
        Date date = new Date();
        cart.setNumber(number.shortValue());
        cart.setUserId(user.getId());
        cart.setGoodsId(goodsId);
        if (goods != null) {
            cart.setGoodsSn(goods.getGoodsSn());
            cart.setGoodsName(goods.getName());
            cart.setPicUrl(goods.getPicUrl());
        }
        cart.setProductId(productId);
        cart.setPrice(goodsProduct.getPrice());
        cart.setSpecifications(Arrays.toString(goodsProduct.getSpecifications()));

        //默认被选中
        cart.setChecked(true);
        cart.setAddTime(date);
        cart.setUpdateTime(date);
        //立即下单的时候生成的订单为逻辑删除状态
        cart.setDeleted(true);

        falg = cartService.insertCart(cart);
        if (falg == 0) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("添加失败");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(cart.getId());
        }
        return responseUtils;
    }

    /**
     * @param jsonObject key： goodsId ; id ; number ; productId
     * @return com.cskaoyan.project.mall.utils.ResponseUtils
     * @throws
     * @creator shentaotao
     * @creat date 2019/8/22 13:18
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
        Integer number = (Integer) jsonObject.get("number");
        Integer productId = (Integer) jsonObject.get("productId");
        if (id == null || number == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("参数格式不正确");
            return responseUtils;
        }
        Cart cart = cartService.queryCart(id);
        //更新购物车中的商品数量
        cart.setNumber(number.shortValue());
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

    /**
     * @param jsonObject    productIds[]
     * @return com.cskaoyan.project.mall.utils.ResponseUtils
     * @throws
     * @creator shentaotao
     * @creat date 2019/8/22 14:35
     * @since
     */
    @RequestMapping("/wx/cart/delete")
    @ResponseBody
    public ResponseUtils deleteCart(@RequestBody JSONObject jsonObject) {
        ResponseUtils responseUtils = new ResponseUtils();
        //获取当前登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("用户未登录");
            return responseUtils;
        }
        List<Integer> productIds = (List) jsonObject.get("productIds");
        if (productIds == null || productIds.size() == 0) {
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
