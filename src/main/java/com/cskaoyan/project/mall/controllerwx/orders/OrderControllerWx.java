package com.cskaoyan.project.mall.controllerwx.orders;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cskaoyan.project.mall.controller.goods.vo.CreatVO;
import com.cskaoyan.project.mall.controller.goods.vo.ResponseVO;
import com.cskaoyan.project.mall.controllerwx.orders.vo.OrderMsg;
import com.cskaoyan.project.mall.controllerwx.orders.vo.RePayVO;
import com.cskaoyan.project.mall.domain.*;
import com.cskaoyan.project.mall.service.advertiseService.CouponService;
import com.cskaoyan.project.mall.service.advertiseService.GroupRulesService;
import com.cskaoyan.project.mall.service.goods.GoodsService;
import com.cskaoyan.project.mall.service.mall.CategoryService;
import com.cskaoyan.project.mall.service.mall.OrderGoodsService;
import com.cskaoyan.project.mall.service.mall.OrderService;
import com.cskaoyan.project.mall.service.mall.RegionService;
import com.cskaoyan.project.mall.service.userService.AddressService;
import com.cskaoyan.project.mall.service.userService.CartService;
import com.cskaoyan.project.mall.utils.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.System;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.System.out;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/21
 * @time 14:48
 */

@Controller
@RequestMapping("wx")
public class OrderControllerWx {

    @Autowired
    OrderGoodsService orderGoodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    AddressService addressService;
    @Autowired
    GroupRulesService groupRulesService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CartService cartService;
    @Autowired
    CouponService couponService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    RegionService regionService;
    @Autowired
    RedisUtil redisUtil;


    /*
     * description: showList
     * version: 1.0
     * date: 2019/8/21 16:17
     * author: du
     * @Param: [showType, page, size]
     * @return: com.cskaoyan.project.mall.controller.goods.vo.ResponseVO
     */
    //请求的url:wx/order/list?showType=1&page=1&size=10
    @RequestMapping("order/list")
    @ResponseBody
    public ResponseVO showList(int showType,int page,int size){
        ResponseVO responseVO = null;
        //根据showType查询出未支付的订单
        //showType=1代表的是未支付的订单
        //responseBody的格式
        //得到userId,根据userId对客户的订单进行查询
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer uid = user.getId();
        //根据showType的不同进行全部、待付款、待发货、待收货、待评价
        switch (showType){
            case 0 :
                //查询全部订单
                ResponseVO responseVO1 = orderService.queryAllOrdersByUid(uid, page, size);
                return responseVO1;
            case 1 :
                //查询待付款的订单
                ResponseVO responseVO2 = orderService.queryUnpayOrdersByUid(uid, page, size);
                return responseVO2;
            case 2 :
                //查询待发货的订单
                ResponseVO responseVO3 = orderService.queryUndeliveredOrdersByUid(uid, page, size);
                return responseVO3;
            case 3 :
                //查询待收货的订单
                ResponseVO responseVO4 = orderService.queryUnreceivedOrdersByUid(uid, page, size);
                return responseVO4;
            case 4 :
                ResponseVO responseVO5 = orderService.queryUnevaluatedOrdersByUid(uid, page, size);
                return responseVO5;
            default:
                return new ResponseVO();
        }
    }
    /*
     * description: 根据orderId查出来订单
     * version: 1.0
     * date: 2019/8/21 21:06
     * author: du
     * @Param: [orderId]
     * @return: com.cskaoyan.project.mall.controller.goods.vo.ResponseVO
     */
    @RequestMapping("order/detail")
    @ResponseBody
    public ResponseVO showOrderDetail(int orderId){
        ResponseVO responseVO = orderService.showOrderDetail(orderId);
        return responseVO;
    }

    /*
     * description: 未付款的用户取消订单
     * version: 1.0
     * date: 2019/8/22 10:58
     * author: du
     * @Param: [orderId]
     * @return: com.cskaoyan.project.mall.controller.goods.vo.CreatVO
     */
    //未付款的用户直接取消订单
    @RequestMapping("order/cancel")
    @ResponseBody
    public CreatVO cancelOrder(@RequestBody String orderId){
        JSONObject jsonObject = JSON.parseObject(orderId);
        Integer oid = jsonObject.getInteger("orderId");
        //string-->int
        int cancel = orderService.cancleOrderByOid(oid);

        if (cancel == 1){
            CreatVO creatVO = new CreatVO(0, "成功");
            return creatVO;
        }else {
            CreatVO creatVO = new CreatVO(-1, "失败");
            return creatVO;
        }
    }

    /*
     * description: refundOrder
     * version: 1.0
     * date: 2019/8/22 11:19
     * author: du
     * @Param: [orderId]
     * @return: com.cskaoyan.project.mall.controller.goods.vo.CreatVO
     */
    //退款取消订单
    @RequestMapping("order/refund")
    @ResponseBody
    public CreatVO refundOrder(@RequestBody String orderId){
        JSONObject jsonObject = JSON.parseObject(orderId);
        Integer oid = jsonObject.getInteger("orderId");
        int refund = orderService.refundByOid(oid);
        if (refund == 1){
            CreatVO creatVO = new CreatVO(0, "成功");
            return creatVO;
        }else {
            CreatVO creatVO = new CreatVO(-1, "失败");
            return creatVO;
        }
    }

    /*
     * description: confirmOrder
     * version: 1.0
     * date: 2019/8/22 11:19
     * author: du
     * @Param: [orderId]
     * @return: com.cskaoyan.project.mall.controller.goods.vo.CreatVO
     */
    //确认收货
    @RequestMapping("order/confirm")
    @ResponseBody
    public CreatVO confirmOrder(@RequestBody String orderId){
        JSONObject jsonObject = JSON.parseObject(orderId);
        Integer oid = jsonObject.getInteger("orderId");
        int confrim = orderService.confrimByOid(oid);
        if (confrim == 1){
            CreatVO creatVO = new CreatVO(0, "成功");
            return creatVO;
        }else {
            CreatVO creatVO = new CreatVO(-1, "失败");
            return creatVO;
        }
    }
    /*
     * description: deleteOrder
     * version: 1.0
     * date: 2019/8/22 11:18
     * author: du
     * @Param: [orderId]
     * @return: com.cskaoyan.project.mall.controller.goods.vo.CreatVO
     */
    //删除订单
    @RequestMapping("order/delete")
    @ResponseBody
    public CreatVO deleteOrder(@RequestBody String orderId){
        JSONObject jsonObject = JSON.parseObject(orderId);
        Integer oid = jsonObject.getInteger("orderId");
        //根据oid分别删除order表和orderGoods里的订单
        int de1 = orderService.deleteByPrimaryKey(oid);
        int de2 = orderGoodsService.deleteByOid(oid);
        if (de1 == 1 && de2 >= 1) {
            CreatVO creatVO = new CreatVO(0, "成功");
            return creatVO;
        } else {
            CreatVO creatVO = new CreatVO(-1, "失败");
            return creatVO;
        }
    }

    //提交订单
    @RequestMapping("order/submit")
    @ResponseBody
    public ResponseVO submitOrder(@RequestBody OrderMsg OrderMsg) {
        //得到userId,根据userId对客户的订单进行查询
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer uid = user.getId();
        //订单的详情
        Integer addressId = OrderMsg.getAddressId();
        Integer cartId = OrderMsg.getCartId();
        Integer couponId = OrderMsg.getCouponId();
        Integer grouponLinkId = OrderMsg.getGrouponLinkId();
        Integer grouponRulesId = OrderMsg.getGrouponRulesId();
        String message = OrderMsg.getMessage();

        //收货地址
        Address address  = addressService.queryAddressByUidAndAddressId(uid,addressId);

        Order order = new Order();
        //uid 用户id
        order.setUserId(uid);
        //orderSn 订单编号
        order.setOrderSn(orderService.generateOrderSn(uid));
        //订单状态码 未付款 101
        short status = 101;
        order.setOrderStatus(status);
        //收货人
        order.setConsignee(address.getName());
        //用户的手机号
        order.setMobile(address.getMobile());
        //message 用户订单留言
        order.setMessage(message);
        Region region1 = regionService.queryRegionById(address.getProvinceId());
        String province = null;
        if (region1 != null){
            province = region1.getName();
        }
        Region region2 = regionService.queryRegionById(address.getCityId());
        String city = null;
        if (region2 != null){
            city = region2.getName();
        }
        Region region3 = regionService.queryRegionById(address.getAreaId());
        String area = null;
        if (region3 != null){
            area = region3.getName();
        }
        String detailedAddress = province + city + area + " " + address.getAddress();
        //detailedAddress 详细地址
        order.setAddress(detailedAddress);

        //积分金额
        BigDecimal integralPrice = new BigDecimal(0.00);
        order.setIntegralPrice(integralPrice);
        //orderTotalPrice订单总金额
        BigDecimal orderTotalPrice = new BigDecimal(0);
        //购物车中选中的商品
        List<Cart> checkedGoodsList = cartService.queryCartByUserIdAndChecked(user.getId(), true);
        if (checkedGoodsList != null) {
            for (Cart cart : checkedGoodsList) {
                Goods goods = goodsService.queryById(cart.getGoodsId());
                if (goods != null) {
                    BigDecimal retailPrice = (goods.getRetailPrice()).multiply(BigDecimal.valueOf(cart.getNumber()));
                    orderTotalPrice = orderTotalPrice.add(retailPrice);
                }
            }
        }
        //如果直接购买，总金额就从cartId里得到
        if (cartId != 0){
            Cart cart = cartService.selectByPrimaryKey(cartId);
            orderTotalPrice = cart.getPrice();
        }
        //couponPrice优惠金额
        Coupon coupon = null;
        if (couponId != null && couponId > 0) {
            //查询当前优惠券的详情
            coupon = couponService.selectByPrimaryKey(couponId);
        }

        BigDecimal couponPrice = new BigDecimal(0);
        if (coupon != null) {
            BigDecimal discount = coupon.getDiscount();
            couponPrice = discount;
        }
        //商品总价
        BigDecimal goodsTotalPrice = orderTotalPrice;
        //goodsTotalPrice 商品总价格
        order.setGoodsPrice(goodsTotalPrice);
        //orderTotalPrice 订单总费用
        order.setOrderPrice(orderTotalPrice);
        //freightPrice运费
        BigDecimal freightPrice = new BigDecimal(0.00);
        freightPrice = BigDecimal.valueOf(10);
        BigDecimal actualPrice = new BigDecimal(0);
        if (orderTotalPrice != null) {
            actualPrice = orderTotalPrice.subtract(couponPrice).add(freightPrice);
            //actualPrice = orderTotalPrice.add(freightPrice);
        }
        order.setFreightPrice(freightPrice);

        //添加团购金额
        BigDecimal grouponPrice = new BigDecimal(0);
        order.setGrouponPrice(grouponPrice);
        //添加优惠券金额
        order.setCouponPrice(couponPrice);
        //添加实际支付的金额
        order.setActualPrice(actualPrice);
        //添加字段
        order.setDeleted(false);
        Date now = new Date();
        order.setAddTime(now);
        order.setUpdateTime(now);
        //添加订单表项
        orderService.add(order);
        //当cartId = 0;orderGoods表添加商品项
        if (cartId == 0){
            for (Cart cart : checkedGoodsList) {
                // 订单商品
                OrderGoods orderGoods = new OrderGoods();
                orderGoods.setOrderId(order.getId());
                orderGoods.setGoodsId(cart.getGoodsId());
                orderGoods.setGoodsSn(cart.getGoodsSn());
                orderGoods.setProductId(cart.getProductId());
                orderGoods.setGoodsName(cart.getGoodsName());
                orderGoods.setPicUrl(cart.getPicUrl());
                orderGoods.setPrice(cart.getPrice());
                orderGoods.setNumber(cart.getNumber());
                orderGoods.setSpecifications(cart.getSpecifications());
                orderGoods.setAddTime(now);
                orderGoods.setUpdateTime(now);
                orderGoods.setDeleted(false);
                orderGoodsService.add(orderGoods);
            }
            // 删除购物车里面的商品信息
            cartService.clearGoods(uid);
        }
        //直接购买的
        if (cartId != 0){
            //根据cartId查询出来的商品
            Cart cart = cartService.selectByPrimaryKey(cartId);
            if (cart != null){
                OrderGoods orderGoods = new OrderGoods();
                orderGoods.setOrderId(order.getId());
                orderGoods.setGoodsId(cart.getGoodsId());
                orderGoods.setGoodsSn(cart.getGoodsSn());
                orderGoods.setProductId(cart.getProductId());
                orderGoods.setGoodsName(cart.getGoodsName());
                orderGoods.setPicUrl(cart.getPicUrl());
                orderGoods.setPrice(cart.getPrice());
                orderGoods.setNumber(cart.getNumber());
                orderGoods.setSpecifications(cart.getSpecifications());
                orderGoods.setAddTime(now);
                orderGoods.setDeleted(false);
                orderGoodsService.add(orderGoods);
            }
        }
        //返回成功下单的信息
        Map<String, Object> data = new HashMap<>();
        Integer orderId = order.getId();
        //放入redis zset队列
        String str = Integer.toString(orderId);
        Double l = Double.valueOf(System.currentTimeMillis()+20000);
        redisUtil.add("orderId",str,l);
        data.put("orderId", orderId);
        ResponseVO responseVO = new ResponseVO(data, "成功", 0);
        return responseVO;
    }

    //未付款的信息
    @RequestMapping("order/prepay")
    @ResponseBody
    public RePayVO prepayOrder(@RequestBody OrderMsg OrderMsg){
        RePayVO rePayVO = new RePayVO("订单不能支付", 742);
        return rePayVO;
    }
}
