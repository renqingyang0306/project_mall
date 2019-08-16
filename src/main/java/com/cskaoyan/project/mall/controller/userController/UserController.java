package com.cskaoyan.project.mall.controller.userController;

import com.cskaoyan.project.mall.domain.User;
import com.cskaoyan.project.mall.service.userService.UserService;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("admin/user")
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("list")
    @ResponseBody
    public ResponseUtils<PageBean> list(int page,int limit,String username,String mobile){
        List<User> users = userService.findAllUser(page, limit, username, mobile);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        PageBean<User> pageBean = new PageBean<>(users, pageInfo.getTotal());
        ResponseUtils<PageBean> responseUtils = new ResponseUtils<>(0,pageBean,"成功");
        return responseUtils;
    }
}
