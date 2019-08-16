package com.cskaoyan.project.mall.controller.auth;

import com.cskaoyan.project.mall.utils.NumberTotal;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.cskaoyan.project.mall.utils.RolesBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/15 23:47
 */
@RestController
@RequestMapping("admin")
public class AuthController {
    @RequestMapping("auth/login")
    public ResponseUtils<String> login(){
        ResponseUtils<String> stringResponseJsonUtils =
                new ResponseUtils<>(0,"9e910f0f-0eab-473f-ac04-52a323584516","成功");
        return  stringResponseJsonUtils;

    }
    @RequestMapping("auth/info")
    public ResponseUtils<RolesBean<String>> info(){

        RolesBean<String> rolesBean=new RolesBean<>();
        rolesBean.setName("admin123");
        List<String> roles = new ArrayList<>();
        roles.add("超级管理员");
        rolesBean.setRoles(roles);
        List<String> roles1 =  new ArrayList<>();
        roles1.add("*");
        rolesBean.setPerms(roles1);
        rolesBean.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        ResponseUtils<RolesBean<String>> json =
                new ResponseUtils<RolesBean<String>>(0,rolesBean,"成功");

        return  json;

    }
    @RequestMapping("dashboard")
    public ResponseUtils<NumberTotal> dashboard(){
        NumberTotal numberTotal=new NumberTotal(200,360,260,390);
        ResponseUtils<NumberTotal> json =
                new ResponseUtils<NumberTotal>(0,numberTotal,"成功");
        return  json;
    }
}
