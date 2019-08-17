package com.cskaoyan.project.mall.controller.admin;

import com.cskaoyan.project.mall.domain.Log;
import com.cskaoyan.project.mall.service.logService.LogService;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/16 22:49
 */
@RestController
@RequestMapping("admin/log")
public class LogController {

    @Autowired
    LogService logService;
    @RequestMapping("list")
    public ResponseUtils<PageBean> list(int page, int limit, String name){
        List<Log> allList = logService.findAllList(page, limit,name);
        PageInfo<Log> pageInfo=new PageInfo<>(allList);
        PageBean<Log> pageBean=new PageBean<>(allList,pageInfo.getTotal());
        ResponseUtils<PageBean>  responseUtils=new ResponseUtils<>(0,pageBean,"成功");
        return responseUtils;
    }

}
