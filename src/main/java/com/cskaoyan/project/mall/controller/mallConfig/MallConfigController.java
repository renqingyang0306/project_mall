package com.cskaoyan.project.mall.controller.mallConfig;

import com.cskaoyan.project.mall.domain.SystemExample;
import com.cskaoyan.project.mall.domain.System;
import com.cskaoyan.project.mall.service.mallConfig.MallConfigService;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.cskaoyan.project.mall.utils.mallConfig.SystemConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/config")
public class MallConfigController
{
    @Resource
    MallConfigService mallConfigService;

    //@RequestMapping(value = {"mall","express","order","wx"},method = RequestMethod.GET)
    //查询：
    @RequestMapping(value = "mall",method = RequestMethod.GET)
    public ResponseUtils<Map> mallQuery()
    {
        SystemExample systemExample = new SystemExample();
        List<System> systemList = mallConfigService.selectByExample(systemExample);
        HashMap<String, String> mallMap = new HashMap<>();
        //MALL:
        String name= "cskaoyan_mall_mall_name";
        String address="cskaoyan_mall_mall_address";
        String phone="cskaoyan_mall_mall_phone";
        String qq="cskaoyan_mall_mall_qq";
        for(System system:systemList)
        {
            switch (system.getKeyName())
            {
                case "cskaoyan_mall_mall_name":
                    mallMap.put(name,system.getKeyValue());
                    break;
                case "cskaoyan_mall_mall_address":
                    mallMap.put(address,system.getKeyValue());
                    break;
                case "cskaoyan_mall_mall_phone":
                    mallMap.put(phone,system.getKeyValue());
                    break;
                case "cskaoyan_mall_mall_qq":
                    mallMap.put(qq,system.getKeyValue());
                    break;
            }
        }
        //4. 将结果封装到ResponseUtil中：
        ResponseUtils<Map> responseUtils = new ResponseUtils<>(0,mallMap , "成功");
        return responseUtils;
    }
    //更新
    @RequestMapping(value = "mall",method = RequestMethod.POST)
    public ResponseUtils<SystemConfigBean> mallUpdate(@RequestBody SystemConfigBean systemConfigBean)
    {
        //1.通过service层实现更新
        int upans = mallConfigService.updateByMallConfig(systemConfigBean);
        //2. 将结果封装到ResponseUtil中：
        ResponseUtils<SystemConfigBean> responseUtils;

        if(upans!=0)
        {
             responseUtils = new ResponseUtils<SystemConfigBean>(0,systemConfigBean, "成功");
        }
        else
        {
            responseUtils = new ResponseUtils<SystemConfigBean>(1,systemConfigBean,"失败");
        }
        return responseUtils;
    }

    //运费：
    //查询：
    @RequestMapping(value = "express",method = RequestMethod.GET)
    public ResponseUtils<Map> expressQuery()
    {
        SystemExample systemExample = new SystemExample();
        List<System> systemList = mallConfigService.selectByExample(systemExample);
        HashMap<String, String> expressMap = new HashMap<>();
        //express:
        String frMin="cskaoyan_mall_express_freight_min";
        String frValue="cskaoyan_mall_express_freight_value";
        for(System system:systemList)
        {
            switch (system.getKeyName())
            {
                case "cskaoyan_mall_express_freight_min":
                    expressMap.put(frMin,system.getKeyValue());
                    break;
                case "cskaoyan_mall_express_freight_value":
                    expressMap.put(frValue,system.getKeyValue());
                    break;
            }
        }
        //4. 将结果封装到ResponseUtil中：
        ResponseUtils<Map> responseUtils = new ResponseUtils<>(0,expressMap , "成功");
        return responseUtils;
    }
    //更新：
    @RequestMapping(value = "express",method = RequestMethod.POST)
    public ResponseUtils<SystemConfigBean> expressUpdate(@RequestBody SystemConfigBean systemConfigBean)
    {
        //1.通过service层实现更新
        int upans = mallConfigService.updateByFreightConfig(systemConfigBean);
        //2. 将结果封装到ResponseUtil中：
        ResponseUtils<SystemConfigBean> responseUtils;

        if(upans!=0)
        {
            responseUtils = new ResponseUtils<SystemConfigBean>(0,systemConfigBean, "成功");
        }
        else
        {
            responseUtils = new ResponseUtils<SystemConfigBean>(1,systemConfigBean,"失败");
        }
        return responseUtils;
    }

    //order：
    //查询：
    @RequestMapping(value = "order",method = RequestMethod.GET)
    public ResponseUtils<Map> orderQuery()
    {
        SystemExample systemExample = new SystemExample();
        List<System> systemList = mallConfigService.selectByExample(systemExample);
        HashMap<String, String> orderMap = new HashMap<>();
        //order:
        String unpaid="cskaoyan_mall_order_unpaid";
        String unconfirm="cskaoyan_mall_order_unconfirm";
        String comment="cskaoyan_mall_order_comment";
        for(System system:systemList)
        {
            switch (system.getKeyName())
            {
                case "cskaoyan_mall_order_unpaid":
                    orderMap.put(unpaid,system.getKeyValue());
                    break;
                case "cskaoyan_mall_order_unconfirm":
                    orderMap.put(unconfirm,system.getKeyValue());
                    break;
                case "cskaoyan_mall_order_comment":
                    orderMap.put(comment,system.getKeyValue());
                    break;
            }
        }
        //4. 将结果封装到ResponseUtil中：
        ResponseUtils<Map> responseUtils = new ResponseUtils<>(0,orderMap , "成功");
        return responseUtils;
    }
    //更新：
    @RequestMapping(value = "order",method = RequestMethod.POST)
    public ResponseUtils<SystemConfigBean> expressupdate(@RequestBody SystemConfigBean systemConfigBean)
    {
        //1.通过service层实现更新
        int upans = mallConfigService.updateByOrderConfig(systemConfigBean);
        //2. 将结果封装到ResponseUtil中：
        ResponseUtils<SystemConfigBean> responseUtils;

        if(upans!=0)
        {
            responseUtils = new ResponseUtils<SystemConfigBean>(0,systemConfigBean, "成功");
        }
        else
        {
            responseUtils = new ResponseUtils<SystemConfigBean>(1,systemConfigBean,"失败");
        }
        return responseUtils;
    }

    //微信：
    //查询：
    @RequestMapping(value = "wx",method = RequestMethod.GET)
    public ResponseUtils<Map> wxQuery()
    {
        SystemExample systemExample = new SystemExample();
        List<System> systemList = mallConfigService.selectByExample(systemExample);
        HashMap<String, String> wxMap = new HashMap<>();
        //wx:
        String share="cskaoyan_mall_wx_share";
        String indexhot="cskaoyan_mall_wx_index_hot";
        String catloggoods="cskaoyan_mall_wx_catlog_goods";
        String catloglist="cskaoyan_mall_wx_catlog_list";
        String indexbrand="cskaoyan_mall_wx_index_brand";
        String indextopic="cskaoyan_mall_wx_index_topic";
        String indexnew="cskaoyan_mall_wx_index_new";
        for(System system:systemList)
        {
            switch (system.getKeyName())
            {
                case "cskaoyan_mall_wx_share":
                    wxMap.put(share,system.getKeyValue());
                    break;
                case "cskaoyan_mall_wx_index_hot":
                    wxMap.put(indexhot,system.getKeyValue());
                    break;
                case "cskaoyan_mall_wx_catlog_goods":
                    wxMap.put(catloggoods,system.getKeyValue());
                    break;
                case "cskaoyan_mall_wx_catlog_list":
                    wxMap.put(catloglist,system.getKeyValue());
                    break;
                case "cskaoyan_mall_wx_index_brand":
                    wxMap.put(indexbrand,system.getKeyValue());
                    break;
                case "cskaoyan_mall_wx_index_topic":
                    wxMap.put(indextopic,system.getKeyValue());
                    break;
                case "cskaoyan_mall_wx_index_new":
                    wxMap.put(indexnew,system.getKeyValue());
                    break;
            }
        }
        //4. 将结果封装到ResponseUtil中：
        ResponseUtils<Map> responseUtils = new ResponseUtils<>(0,wxMap , "成功");
        return responseUtils;
    }
    //更新：
    @RequestMapping(value = "wx",method = RequestMethod.POST)
    public ResponseUtils<SystemConfigBean> wxUpdate(@RequestBody SystemConfigBean systemConfigBean)
    {
        //1.通过service层实现更新
        int upans = mallConfigService.updateBywxConfig(systemConfigBean);
        //2. 将结果封装到ResponseUtil中：
        ResponseUtils<SystemConfigBean> responseUtils;

        if(upans!=0)
        {
            responseUtils = new ResponseUtils<SystemConfigBean>(0,systemConfigBean, "成功");
        }
        else
        {
            responseUtils = new ResponseUtils<SystemConfigBean>(1,systemConfigBean,"失败");
        }
        return responseUtils;
    }


}

