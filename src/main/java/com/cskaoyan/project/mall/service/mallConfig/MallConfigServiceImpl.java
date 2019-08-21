package com.cskaoyan.project.mall.service.mallConfig;

import com.cskaoyan.project.mall.domain.System;
import com.cskaoyan.project.mall.domain.SystemExample;
import com.cskaoyan.project.mall.mapper.SystemMapper;
import com.cskaoyan.project.mall.utils.mallConfig.SystemConfigBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class MallConfigServiceImpl implements MallConfigService
{
    @Resource
    SystemMapper systemMapper;
    @Override
    public long countByExample(SystemExample example)
    {
        return systemMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(SystemExample example)
    {
        return systemMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer id)
    {
        return systemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(System record)
    {
        return systemMapper.insert(record);
    }

    @Override
    public int insertSelective(System record)
    {
        return systemMapper.insertSelective(record);
    }

    @Override
    public List<System> selectByExample(SystemExample example)
    {
        return systemMapper.selectByExample(example);
    }

    @Override
    public System selectByPrimaryKey(Integer id)
    {
        return systemMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(System record, SystemExample example)
    {
        return systemMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(System record, SystemExample example)
    {
        return systemMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(System record)
    {
        return systemMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(System record)
    {
        return systemMapper.updateByPrimaryKey(record);
    }




    //商场配置：
    /*//查询：
    @Override
    public Map<String, String> selectByMallConfig(SystemConfigBean systemConfigBean)
    {
        Map<String, String> mallMap = queryMallMap(systemConfigBean);

        return null;
    }*/
    //更新：
    @Override
    public int updateByMallConfig(SystemConfigBean systemConfigBean)
    {
        Map<String, String> mallMap = queryMallMap(systemConfigBean);
        int upans=0;
        Set<String> mKeySet = mallMap.keySet();
        for (String key : mKeySet)
        {
            String value = mallMap.get(key);
            SystemExample systemExample = new SystemExample();
            systemExample.createCriteria().andKeyNameEqualTo(key);
            List<System> systemList = systemMapper.selectByExample(systemExample);
            if(systemList.size()>0)
            {
                //给第一个system对象的key_value赋值，也就是创建一个用来更新用的system对象！
                systemList.get(0).setKeyValue(value);//用作下面的参数system
                upans = systemMapper.updateByPrimaryKeySelective(systemList.get(0));
                if (upans==0){
                    break;
                }
            }
        }
        return upans;
    }

    public  Map<String,String> queryMallMap(SystemConfigBean systemConfigBean)
    {
        String address="cskaoyan_mall_mall_address";
        String phone="cskaoyan_mall_mall_phone";
        String qq="cskaoyan_mall_mall_qq";
        String name="cskaoyan_mall_mall_name";
        Map<String,String> map=new HashMap<>();
        map.put(address,systemConfigBean.getCskaoyan_mall_mall_address());
        map.put(phone,systemConfigBean.getCskaoyan_mall_mall_phone());
        map.put(qq,systemConfigBean.getCskaoyan_mall_mall_qq());
        map.put(name,systemConfigBean.getCskaoyan_mall_mall_name());
        return map;
    }

    //运费：
    //更新：
    @Override
    public int updateByFreightConfig(SystemConfigBean systemConfigBean)
    {
        Map<String, String> freightMap = getFreightMap(systemConfigBean);
        Set<String> fKeySet = freightMap.keySet();

        int upans=0;
        for(String key:fKeySet)
        {
            //根据键---获取对应的值，为下面的更新做准备！
            String value = freightMap.get(key);
            SystemExample systemExample = new SystemExample();
            systemExample.createCriteria().andKeyNameEqualTo(key);
            List<System> systemList = systemMapper.selectByExample(systemExample);
            if(systemList.size()>0)
            {
                //首先利用key查出的value建立一个参数对象system:
                systemList.get(0).setKeyValue(value);
                upans = systemMapper.updateByPrimaryKeySelective(systemList.get(0));
                if(upans==0)
                    break;
            }

        }
        return upans;
    }

    public Map<String,String> getFreightMap(SystemConfigBean systemConfigBean)
    {
        String frMin="cskaoyan_mall_express_freight_min";
        String frValue="cskaoyan_mall_express_freight_value";
        HashMap<String, String> fmap = new HashMap<>();
        fmap.put(frMin,systemConfigBean.getCskaoyan_mall_express_freight_min());
        fmap.put(frValue,systemConfigBean.getCskaoyan_mall_express_freight_value());
        return fmap;
    }

    //更新订单：
    @Override
    public int updateByOrderConfig(SystemConfigBean systemConfigBean)
    {
        //1. 调用map，建立keyset，对key进行遍历：
        Map<String, String> orderMap = getOrderMap(systemConfigBean);
        Set<String> orderKeySet = orderMap.keySet();
        int upans=0;
        for(String key: orderKeySet)
        {
            //1. 利用key，建立更新所用参数：
            String value = orderMap.get(key);

            //1. 建立查询的example：
            SystemExample systemExample = new SystemExample();
            systemExample.createCriteria().andKeyNameEqualTo(key);
            List<System> systemList = systemMapper.selectByExample(systemExample);
            if(systemList.size()>0)
            {
                systemList.get(0).setKeyValue(value);
                upans = systemMapper.updateByPrimaryKeySelective(systemList.get(0));
                if(upans==0)
                    break;
            }
        }
        return upans;
    }



    public Map<String,String> getOrderMap(SystemConfigBean systemConfigBean)
    {
        HashMap<String, String> orderMap = new HashMap<>();
        //获取字段：
        String unpaid="cskaoyan_mall_order_unpaid";
        String unconfirm="cskaoyan_mall_order_unconfirm";
        String comment="cskaoyan_mall_order_comment";
        //键值匹配：
        orderMap.put(unpaid,systemConfigBean.getCskaoyan_mall_order_unpaid());
        orderMap.put(unconfirm,systemConfigBean.getCskaoyan_mall_order_unconfirm());
        orderMap.put(comment,systemConfigBean.getCskaoyan_mall_order_comment());

        return orderMap;
    }

    //更新微信：
    @Override
    public int updateBywxConfig(SystemConfigBean systemConfigBean)
    {
        Map<String, String> wxMap = getwxMap(systemConfigBean);
        Set<String> wxKeySet = wxMap.keySet();
        int upans=0;
        for(String key:wxKeySet)
        {
            SystemExample systemExample = new SystemExample();
            systemExample.createCriteria().andKeyNameEqualTo(key);
            List<System> systemList = systemMapper.selectByExample(systemExample);
            if(systemList.size()>0)
            {
                String value = wxMap.get(key);
                systemList.get(0).setKeyValue(value);
                upans = systemMapper.updateByPrimaryKeySelective(systemList.get(0));
                if(upans==0)
                {
                    break;
                }
            }
        }
        return upans;
    }
    public Map<String,String> getwxMap(SystemConfigBean systemConfigBean)
    {
        HashMap<String, String> wxMap = new HashMap<>();
        String indexnew="cskaoyan_mall_wx_index_new";
        String share="cskaoyan_mall_wx_share";
        String indexhot="cskaoyan_mall_wx_index_hot";
        String catloggoods="cskaoyan_mall_wx_catlog_goods";
        String catloglist="cskaoyan_mall_wx_catlog_goods";
        String indexbrand="cskaoyan_mall_wx_index_brand";
        String indextopic="cskaoyan_mall_wx_index_topic";
        wxMap.put(indexnew,systemConfigBean.getCskaoyan_mall_wx_index_new());
        wxMap.put(share,systemConfigBean.getCskaoyan_mall_wx_share());
        wxMap.put(indexhot,systemConfigBean.getCskaoyan_mall_wx_index_hot());
        wxMap.put(catloggoods,systemConfigBean.getCskaoyan_mall_wx_catlog_goods());
        wxMap.put(catloglist,systemConfigBean.getCskaoyan_mall_wx_catlog_list());
        wxMap.put(indexbrand,systemConfigBean.getCskaoyan_mall_wx_index_brand());
        wxMap.put(indextopic,systemConfigBean.getCskaoyan_mall_wx_index_topic());
        return wxMap;
    }

}
