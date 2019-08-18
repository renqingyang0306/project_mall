package com.cskaoyan.project.mall.controller.userController;

import com.cskaoyan.project.mall.domain.Footprint;
import com.cskaoyan.project.mall.service.userService.FootprintService;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("admin/footprint")
public class FootprintController {
    @Autowired
    FootprintService footprintService;
    @RequestMapping("list")
    @ResponseBody
    public ResponseUtils<PageBean> list(int page,int limit,Integer userId,Integer goodsId){
        List<Footprint> allFootprint = footprintService.findAllFootprint(page, limit, userId, goodsId);
        PageInfo<Footprint> pageInfo = new PageInfo<>(allFootprint);
        PageBean<Footprint> pageBean = new PageBean<>(allFootprint, pageInfo.getTotal());
        ResponseUtils<PageBean> responseUtils = new ResponseUtils<>(0, pageBean, "成功");
        return responseUtils;
    }
}
