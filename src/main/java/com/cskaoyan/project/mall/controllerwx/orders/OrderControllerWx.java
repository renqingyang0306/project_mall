package com.cskaoyan.project.mall.controllerwx.orders;

import com.cskaoyan.project.mall.controller.goods.vo.ResponseVO;
import com.cskaoyan.project.mall.domain.User;
import com.cskaoyan.project.mall.service.mall.OrderGoodsService;
import com.cskaoyan.project.mall.service.mall.OrderService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
