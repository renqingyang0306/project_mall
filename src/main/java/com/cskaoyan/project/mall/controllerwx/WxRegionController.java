package com.cskaoyan.project.mall.controllerwx;

import com.cskaoyan.project.mall.domain.Cart;
import com.cskaoyan.project.mall.domain.Region;
import com.cskaoyan.project.mall.domain.User;
import com.cskaoyan.project.mall.service.mall.RegionService;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/22 10:58
 */
@Controller
public class WxRegionController {
    @Autowired
    RegionService regionService;

    @RequestMapping("/wx/region/list")
    @ResponseBody
    public ResponseUtils queryRegionList(Integer pid) {
        ResponseUtils responseUtils = new ResponseUtils();
        //获取当前登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Cart> cartList = new ArrayList<>();
        if (user == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("用户未登录");
            return responseUtils;
        }
        List<Region> regions = new ArrayList<>();
        if (pid != null) {
            regions = regionService.selectByPid(pid);
        }

        if (pid == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("pid 不能为 null");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(regions);
        }
        return responseUtils;

    }
}
