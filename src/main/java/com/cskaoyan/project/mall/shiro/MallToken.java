package com.cskaoyan.project.mall.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class MallToken extends UsernamePasswordToken {
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MallToken(String username, String password, String type) {
        super(username, password);
        this.type = type;
    }
}
