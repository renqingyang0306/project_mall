package com.cskaoyan.project.mall.controllerwx.orders;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cskaoyan.project.mall.controller.goods.vo.CreatVO;
import com.cskaoyan.project.mall.controller.goods.vo.ResponseVO;
import com.cskaoyan.project.mall.controllerwx.orders.vo.OrderMsg;
import com.cskaoyan.project.mall.domain.*;
import com.cskaoyan.project.mall.service.advertiseService.GroupRulesService;
import com.cskaoyan.project.mall.service.mall.CategoryService;
import com.cskaoyan.project.mall.service.mall.OrderGoodsService;
import com.cskaoyan.project.mall.service.mall.OrderService;
import com.cskaoyan.project.mall.service.userService.AddressService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.System;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
        int addressId = OrderMsg.getAddressId();
        int cartId = OrderMsg.getCarid();
        int couponId = OrderMsg.getCouponId();
        int grouponLinkId = OrderMsg.getGrouponLinkId();
        int grouponRulesId = OrderMsg.getGrouponRulesId();
        String message = OrderMsg.getMessage();
        //塞到order和OrderGood两张表里
        // 1.收货地址
        Address address  = addressService.queryAddressByUidAndAddressId(uid,addressId);

        Order order = new Order();
        order.setUserId(uid);
        //放入订单编号orderSn
        order.setOrderSn(orderService.generateOrderSn(uid));
        //放入订单状态码 未付款 102
        short status = 102;
        order.setOrderStatus(status);
        //放入订单用户的地址
        order.setConsignee(address.getName());
        //放入用户的手机号
        order.setMobile(address.getMobile());
        //注入message
        order.setMessage(message);

        //String detailedAddress = address.getProvince() + address.getCity() + address.getCounty() + " " + address.getAddressDetail();

        //order.setAddress(detailedAddress);

        //团购优惠价格
        BigDecimal grouponPrice = new BigDecimal(0.00);
        //根据grouponRulesId，查出团购的优惠方案
        GrouponRules grouponRules = groupRulesService.selectByPrimaryKey(grouponRulesId);

        // 3.货品价格 根据uid查询购物车里的商品列表
        /*List<Cart> checkedGoodsList = null;
        if (cartId == 0) {
            checkedGoodsList = categoryService.(uid);
        } else {
            LitemallCart cart = cartService.findById(cartId);
            checkedGoodsList = new ArrayList<>(1);
            checkedGoodsList.add(cart);
        }

        order.setGoodsPrice(checkedGoodsPrice);
        order.setFreightPrice(freightPrice);
        order.setCouponPrice(couponPrice);
        order.setIntegralPrice(integralPrice);
        order.setOrderPrice(orderTotalPrice);
        order.setActualPrice(actualPrice);*/

        return null;
    }
}
