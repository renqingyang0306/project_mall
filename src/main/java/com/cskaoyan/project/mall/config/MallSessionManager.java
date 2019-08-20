package com.cskaoyan.project.mall.config;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class MallSessionManager extends DefaultWebSessionManager {

    @Override
    protected Serializable getSessionId(ServletRequest servletRequest, ServletResponse response) {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String id = request.getHeader("X-cskaoyanmall-Admin-Token");
        if (id != null && !"".equals(id) )
        {
            return id;
        }
        return super.getSessionId(servletRequest, response);
    }
}
