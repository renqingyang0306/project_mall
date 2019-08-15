package com.cskaoyan.project.mall.controller;

import com.cskaoyan.project.mall.domain.User;
import com.cskaoyan.project.mall.domain.UserExample;
import com.cskaoyan.project.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/15 21:29
 */
@Controller
public class UserController {
    //测试
    @Autowired
    UserService userService;
    @RequestMapping("select")
    @ResponseBody
    public  List<User>  hello(){
        List<User> users = userService.selectByExample(new UserExample());
        return users;
    }
}
