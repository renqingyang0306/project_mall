package com.cskaoyan.project.mall.controller.mall;

import com.cskaoyan.project.mall.domain.Region;
import com.cskaoyan.project.mall.service.mall.RegionService;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.cskaoyan.project.mall.vo.Children;
import com.cskaoyan.project.mall.vo.ResponseRegions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 申涛涛
 * @date 2019/8/16 15:46
 */
@Controller
public class RegionController {
    @Autowired
    RegionService regionService;

    @RequestMapping("/admin/region/list")
    @ResponseBody
    public ResponseUtils queryAllRegion() {
        List<ResponseRegions> responseRegionsList = new ArrayList<>();
        //查询省份信息
        List<Region> baseRegions = regionService.selectByPid(0);

        for (Region region : baseRegions) {
            ResponseRegions responseRegions = new ResponseRegions();
            responseRegions.setId(region.getId());
            responseRegions.setName(region.getName());
            responseRegions.setType(region.getType());
            responseRegions.setCode(region.getCode());
            //查询市级信息
            String code = String.valueOf(region.getCode());
            List<Region> regions1 = regionService.selectLikeCode(code);
            List<Children> childrenList = new ArrayList<>();

            for (Region region1 : regions1) {
               Children children = new Children(region1.getId(),region1.getName(),region1.getType(),region1.getCode());
                String code1 = String.valueOf(region1.getCode());
                //查询区级信息
                List<Region> regions2 = regionService.selectLikeCode(code1);
                //存放区县级信息
                List<Map<String,String>> mapList = new ArrayList<>();
                //封装区县级信息
                for (Region region2 : regions2) {
                    Map<String,String> map = new HashMap<>();
                    map.put("id", String.valueOf(region2.getId()));
                    map.put("name", region2.getName());
                    map.put("type", String.valueOf(region2.getType()));
                    map.put("code", String.valueOf(region2.getCode()));
                    mapList.add(map);
                }
                /*Map<String,List> stringListMap = new HashMap<>();
                stringListMap.put("children",mapList);*/
                children.setChildren(mapList);
                childrenList.add(children);
            }
            responseRegions.setChildren(childrenList);
            responseRegionsList.add(responseRegions);
        }

        ResponseUtils responseUtils = new ResponseUtils();
        if (responseRegionsList == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("服务端错误！");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功！");
            responseUtils.setData(responseRegionsList);
        }
        return responseUtils;
    }
}
