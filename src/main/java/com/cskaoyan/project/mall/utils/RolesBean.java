package com.cskaoyan.project.mall.utils;

import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/15 23:55
 */
public class RolesBean<T>{
    List<T> roles;
    String name;
    List<T> perms;
    String avatar;
   /* "roles":["超级管理员"],
            "name":"admin123","perms":["*"],
            "":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"*/

    public List<T> getRoles() {
        return roles;
    }

    public void setRoles(List<T> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getPerms() {
        return perms;
    }

    public void setPerms(List<T> perms) {
        this.perms = perms;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public RolesBean(List<T> roles, String name, List<T> perms, String avatar) {
        this.roles = roles;
        this.name = name;
        this.perms = perms;
        this.avatar = avatar;
    }

    public RolesBean() {
    }
}


































