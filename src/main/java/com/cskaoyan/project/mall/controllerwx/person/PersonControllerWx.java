package com.cskaoyan.project.mall.controllerwx.person;

import com.cskaoyan.project.mall.controller.goods.vo.PageVO;
import com.cskaoyan.project.mall.controller.goods.vo.ResponseVO;
import com.cskaoyan.project.mall.controllerwx.orders.vo.CollectPageVO;
import com.cskaoyan.project.mall.controllerwx.orders.vo.FootprintVO;
import com.cskaoyan.project.mall.domain.Collect;
import com.cskaoyan.project.mall.domain.Goods;
import com.cskaoyan.project.mall.domain.User;
import com.cskaoyan.project.mall.service.goods.GoodsService;
import com.cskaoyan.project.mall.service.userService.CollectService;
import com.cskaoyan.project.mall.service.userService.FootprintService;
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

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/21
 * @time 22:06
 */
@Controller
@RequestMapping("wx")
public class PersonControllerWx {

    @Autowired
    FootprintService footprintService;
    @Autowired
    CollectService collectService;
    @Autowired
    GoodsService goodsService;

    //获得访问足迹
    @RequestMapping("footprint/list")
    @ResponseBody
    public ResponseVO getFootprintList(int page, int size){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer uid = user.getId();
        ResponseVO<FootprintVO> responseVO = footprintService.findFootprintByUid(page, size, uid);
        return responseVO;
    }

    //获得个人收藏
    @RequestMapping("collect/list")
    @ResponseBody
    public ResponseVO list(Byte type,Integer page,Integer limit){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer userId = user.getId();
        //查出collect表的信息
        List<Collect> collectList = collectService.queryByType(userId, type, page, limit);

        List<Object> collects = new ArrayList<>(collectList.size());
        for (Collect collect : collectList) {
            //注入collectList
            Map<String, Object> map = new HashMap<>();
            map.put("id", collect.getId());
            map.put("type", collect.getType());
            map.put("valueId", collect.getValueId());
            Goods goods = goodsService.findById(collect.getValueId());
            map.put("name", goods.getName());
            map.put("brief", goods.getBrief());
            map.put("picUrl", goods.getPicUrl());
            map.put("retailPrice", goods.getRetailPrice());
            collects.add(map);
        }
        //放到到pageVO里
        /*PageVO pageVO = new PageVO();
        pageVO.setItems(collects);*/
        CollectPageVO<Object> pageVO = new CollectPageVO<>();
        long tatol = 2 ;
        pageVO.setTotalPages(tatol);
        pageVO.setCollectList(collects);
        ResponseVO responseVO = new ResponseVO(pageVO, "成功", 0);
        return responseVO;
    }





}
