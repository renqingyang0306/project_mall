package com.cskaoyan.project.mall.controllerwx.GrouponRulesWx;

import com.cskaoyan.project.mall.domain.*;
import com.cskaoyan.project.mall.mapper.OrderMapper;
import com.cskaoyan.project.mall.service.advertiseService.GroupOnService;
import com.cskaoyan.project.mall.service.advertiseService.GroupRulesService;
import com.cskaoyan.project.mall.service.mall.OrderGoodsService;
import com.cskaoyan.project.mall.service.userService.UserService;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GroupRulesWxController {
@Autowired
    GroupRulesService groupRulesService;
@Autowired
GroupOnService groupOnService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserService userService;
    @Autowired
    OrderGoodsService orderGoodsService;

@RequestMapping("wx/groupon/my")
@ResponseBody
    public ResponseUtils<HashMap> groupRules(Integer showType){
    Subject subject = SecurityUtils.getSubject();
    subject = SecurityUtils.getSubject();
    //获取认证后的用户信息，通过Realm进行封装的
    User user = (User) subject.getPrincipal();
    int userId = user.getId();

    GrouponExample grouponExample = new GrouponExample();
    GrouponExample.Criteria criteria = grouponExample.createCriteria();
    criteria.andGrouponIdEqualTo(showType);
    criteria.andUserIdEqualTo(userId);
    List<Groupon> groupons = groupOnService.selectByExample(grouponExample);
    List<Map<String, Object>> grouponVoList = new ArrayList<>(groupons.size());

    Order order;
    GrouponRules rules;
    User creator;
    for (Groupon groupon : groupons) {
        order = orderMapper.selectByPrimaryKey(groupon.getOrderId());
        rules = groupRulesService.selectByPrimaryKey(groupon.getRulesId());
        creator = userService.selectByPrimaryKey(groupon.getCreatorUserId());

        Map<String, Object> grouponVo = new HashMap<>();
        //填充团购信息
        grouponVo.put("id", groupon.getId());
        grouponVo.put("groupon", groupon);
        grouponVo.put("rules", rules);
        grouponVo.put("creator", creator.getNickname());

        int linkGrouponId;
        // 这是一个团购发起记录
        if (groupon.getGrouponId() == 0) {
            linkGrouponId = groupon.getId();
            grouponVo.put("isCreator", creator.getId() == userId);
        } else {
            linkGrouponId = groupon.getGrouponId();
            grouponVo.put("isCreator", false);
        }
        GrouponExample grouponExample1 = new GrouponExample();
        GrouponExample.Criteria criteria1 = grouponExample1.createCriteria();

        criteria1.andCreatorUserIdEqualTo(creator.getId());
        criteria1.andGrouponIdEqualTo(0);
        criteria1.andRulesIdEqualTo(rules.getId());
        List<Groupon> groupons1 = groupOnService.selectByExample(grouponExample1);

        grouponVo.put("joinerCount", groupons1.size() + 1);

        //填充订单信息
        grouponVo.put("orderId", order.getId());
        grouponVo.put("orderSn", order.getOrderSn());
        grouponVo.put("actualPrice", order.getActualPrice());
        grouponVo.put("orderStatusText", OrderUtils.orderStatusText(order));
        grouponVo.put("handleOption", OrderUtils.build(order));


        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        OrderGoodsExample.Criteria criteria2 = orderGoodsExample.createCriteria();
        criteria2.andOrderIdEqualTo(order.getId());
        List<OrderGoods> orderGoodsList = orderGoodsService.selectByExample(orderGoodsExample);


        List<Map<String, Object>> orderGoodsVoList = new ArrayList<>(orderGoodsList.size());
        for (OrderGoods orderGoods : orderGoodsList) {
            Map<String, Object> orderGoodsVo = new HashMap<>();
            orderGoodsVo.put("id", orderGoods.getId());
            orderGoodsVo.put("goodsName", orderGoods.getGoodsName());
            orderGoodsVo.put("number", orderGoods.getNumber());
            orderGoodsVo.put("picUrl", orderGoods.getPicUrl());
            orderGoodsVoList.add(orderGoodsVo);
        }
        grouponVo.put("goodsList", orderGoodsVoList);
        grouponVoList.add(grouponVo);
    }
    Map<String, Object> result = new HashMap<>();
    result.put("count", grouponVoList.size());
    result.put("data", grouponVoList);


    ResponseUtils pageBeanResponseUtils = new ResponseUtils<>();
    pageBeanResponseUtils.setData(result);
    pageBeanResponseUtils.setErrno(0);
    pageBeanResponseUtils.setErrmsg("成功");
    return pageBeanResponseUtils;
}


    @RequestMapping("wx/groupon/detail")
    @ResponseBody
    public ResponseUtils<HashMap> groupRulesIs(Integer grouponId){


        Subject subject = SecurityUtils.getSubject();
        subject = SecurityUtils.getSubject();
        //获取认证后的用户信息，通过Realm进行封装的
        User user = (User) subject.getPrincipal();
        int userId = user.getId();

            Groupon groupon = groupOnService.selectByPrimaryKey(grouponId);
          GrouponRules rules = groupRulesService.selectByPrimaryKey(groupon.getRulesId());
            // 订单信息
           Order order = orderMapper.selectByPrimaryKey(groupon.getOrderId());
            Map<String, Object> orderVo = new HashMap<String, Object>();
            orderVo.put("id", order.getId());
            orderVo.put("orderSn", order.getOrderSn());
            orderVo.put("addTime", order.getAddTime());
            orderVo.put("consignee", order.getConsignee());
            orderVo.put("mobile", order.getMobile());
            orderVo.put("address", order.getAddress());
            orderVo.put("goodsPrice", order.getGoodsPrice());
            orderVo.put("freightPrice", order.getFreightPrice());
            orderVo.put("actualPrice", order.getActualPrice());
            orderVo.put("orderStatusText", OrderUtils.orderStatusText(order));
            orderVo.put("handleOption", OrderUtils.build(order));
            orderVo.put("expCode", order.getShipChannel());
            orderVo.put("expNo", order.getShipSn());


        OrderGoodsExample orderGoodsExample1 = new OrderGoodsExample();
        OrderGoodsExample.Criteria criteria3 = orderGoodsExample1.createCriteria();
        criteria3.andOrderIdEqualTo(order.getId());
        List<OrderGoods> orderGoodsList = orderGoodsService.selectByExample(orderGoodsExample1);


            List<Map<String, Object>> orderGoodsVoList = new ArrayList<>(orderGoodsList.size());
            for (OrderGoods orderGoods : orderGoodsList) {
                Map<String, Object> orderGoodsVo = new HashMap<>();
                orderGoodsVo.put("id", orderGoods.getId());
                orderGoodsVo.put("orderId", orderGoods.getOrderId());
                orderGoodsVo.put("goodsId", orderGoods.getGoodsId());
                orderGoodsVo.put("goodsName", orderGoods.getGoodsName());
                orderGoodsVo.put("number", orderGoods.getNumber());
                orderGoodsVo.put("retailPrice", orderGoods.getPrice());
                orderGoodsVo.put("picUrl", orderGoods.getPicUrl());
                orderGoodsVo.put("goodsSpecificationValues", orderGoods.getSpecifications());
                orderGoodsVoList.add(orderGoodsVo);
            }

            Map<String, Object> result = new HashMap<>();
            result.put("orderInfo", orderVo);
            result.put("orderGoods", orderGoodsVoList);

            User userCreater= userService.selectByPrimaryKey(groupon.getCreatorUserId());
            UserVo creator = new UserVo();
            creator.setNickname(userCreater.getNickname());
            creator.setAvatar(userCreater.getAvatar());
            List<UserVo> joiners = new ArrayList<>();
            joiners.add(creator);
            int linkGrouponId;
            // 这是一个团购发起记录
            if (groupon.getGrouponId() == 0) {
                linkGrouponId = groupon.getId();
            } else {
                linkGrouponId = groupon.getGrouponId();

            }

        GrouponExample grouponExample1 = new GrouponExample();
        GrouponExample.Criteria criteria1 = grouponExample1.createCriteria();

        //登录用户创建的此条订单
        criteria1.andCreatorUserIdEqualTo(userId);
        criteria1.andGrouponIdEqualTo(0);
        criteria1.andRulesIdEqualTo(rules.getId());
        List<Groupon> groupons = groupOnService.selectByExample(grouponExample1);

            UserVo joiner = new UserVo();
            for (Groupon grouponItem : groupons) {
                User user1= userService.selectByPrimaryKey(grouponItem.getUserId());
                joiner.setAvatar(user1.getAvatar());
                joiner.setNickname(user1.getNickname());
                joiners.add(joiner);
            }
            result.put("linkGrouponId", linkGrouponId);
            result.put("creator", creator);
            result.put("joiners", joiners);
            result.put("groupon", groupon);
            result.put("rules", rules);


        ResponseUtils pageBeanResponseUtils = new ResponseUtils<>();
        pageBeanResponseUtils.setData(result);
        pageBeanResponseUtils.setErrno(0);
        pageBeanResponseUtils.setErrmsg("成功");
        return pageBeanResponseUtils;

        }

}
