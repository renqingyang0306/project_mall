package com.cskaoyan.project.mall.controller.userController;

import com.cskaoyan.project.mall.domain.Collect;
import com.cskaoyan.project.mall.service.userService.CollectService;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("admin/collect")
public class CollectController {
    @Autowired
    CollectService collectService;
    @RequestMapping("list")
    @ResponseBody
    public ResponseUtils<PageBean> list(int page,int limit,Integer userId,Integer valueId ){
        List<Collect> collects = collectService.findAllCollect(page, limit,userId,valueId);
        PageInfo<Collect> pageInfo = new PageInfo<>(collects);
        PageBean<Collect> pageBean = new PageBean<>(collects, pageInfo.getTotal());
        ResponseUtils<PageBean> responseUtils = new ResponseUtils<>(0, pageBean, "成功");
        return responseUtils;
    }
}
