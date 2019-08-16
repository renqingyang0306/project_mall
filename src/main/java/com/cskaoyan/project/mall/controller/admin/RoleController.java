package com.cskaoyan.project.mall.controller.admin;

import com.cskaoyan.project.mall.service.roleService.RoleService;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.cskaoyan.project.mall.utils.RoleBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/16 14:15
 */
@RestController
@RequestMapping("admin/role")
public class RoleController {
    @Autowired
    RoleService roleService;
    @RequestMapping("options")
    public ResponseUtils<List<RoleBean>> list(){
        List<RoleBean> list=roleService.finaAllList();
        ResponseUtils<List<RoleBean>> responseUtils = new ResponseUtils<>(0,list,"成功");
        return responseUtils;
    }
}
