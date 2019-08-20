package com.cskaoyan.project.mall.controller.mallConfig;

import com.cskaoyan.project.mall.domain.SystemExample;
import com.cskaoyan.project.mall.domain.System;
import com.cskaoyan.project.mall.service.mallConfig.MallConfigService;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/config")
public class MallConfigController
{
    @Autowired
    MallConfigService mallConfigService;

    @RequestMapping("mall")
    public ResponseUtils mall()
    {
        //1.设置查询条件：对数据库中system表中的key_name列进行查询：deleted字段用来标记是否被删除了
        SystemExample systemExample = new SystemExample();
        systemExample.or().andKeyNameLike("cskaoyan_mall_%").andDeletedEqualTo(false);
        //2.通过service层实现查询
        List<System> systemList = mallConfigService.selectByExample(systemExample);
        //3.将结果存储到map中：需要遍历！！
        Map<String,String> keynameMap = new HashMap<>();
        for(System system:systemList)
        {
            keynameMap.put(system.getKeyName(),system.getKeyValue());
        }
        //4. 将结果封装到ResponseUtil中：
        ResponseUtils<Map> responseUtils = new ResponseUtils<>(0, keynameMap, "成功");
        return responseUtils;
    }
    /*@RequestMapping("updateMall")
    public ResponseUtils updateMall()
    {
        return null;
    }*/
}

