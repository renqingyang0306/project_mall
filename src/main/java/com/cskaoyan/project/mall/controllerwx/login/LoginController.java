package com.cskaoyan.project.mall.controllerwx.login;

import com.cskaoyan.project.mall.domain.Admin;
import com.cskaoyan.project.mall.domain.Order;
import com.cskaoyan.project.mall.domain.OrderExample;
import com.cskaoyan.project.mall.domain.User;
import com.cskaoyan.project.mall.mapper.OrderMapper;
import com.cskaoyan.project.mall.service.NumberTotalService;
import com.cskaoyan.project.mall.service.adminService.AdminService;
import com.cskaoyan.project.mall.service.logService.LogHelper;
import com.cskaoyan.project.mall.service.permissionService.PermissionService;
import com.cskaoyan.project.mall.service.roleService.RoleService;
import com.cskaoyan.project.mall.service.userService.UserService;
import com.cskaoyan.project.mall.shiro.MallToken;
import com.cskaoyan.project.mall.utils.*;
import com.cskaoyan.project.mall.utils.userbean.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/15 23:47
 */
@RestController
@RequestMapping("wx")
public class LoginController {

    private final Log logger = LogFactory.getLog(LoginController.class);
    @Autowired
    UserService userService;
    @Autowired
    LogHelper logHelper;
    @Autowired
    OrderMapper orderMapper;

    @RequestMapping("auth/login")
    public Object login(@RequestBody  User user, HttpServletRequest request){
        //如果用户名密码为空
        if (user.getUsername()==null || user.getPassword()==null) {
            //返回错误信息
            return ResponseUtils.badArgument();
        }
        Subject subject = SecurityUtils.getSubject();
        MallToken mallToken=new MallToken(user.getUsername(), user.getPassword(),"wx");
        logger.info("正在使用微信登陆");
        try {
            subject.login(mallToken);
        } catch (UnknownAccountException uae) {
            return ResponseUtils.fail(605, "用户帐号或密码不正确");
        } catch (AuthenticationException ae) {
            return ResponseUtils.fail(605, "认证失败");
        }
        subject = SecurityUtils.getSubject();
        //获取认证后的用户信息，通过Realm进行封装的
        User user1 = (User) subject.getPrincipal();
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        user.setLastLoginTime(new Date());
        userService.updateByPrimaryKey(user1);

        //封装用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(user1.getNickname());
        userInfo.setAvatarUrl(user1.getAvatar());
        //封装返回值
        Map<Object, Object> result = new HashMap<>();
        result.put("token", subject.getSession().getId());
        result.put("tokenExpire", new Date());
        result.put("userInfo", userInfo);
        return  ResponseUtils.ok(result);
    }
    /*
     * 退出登录
     */
    @RequiresAuthentication
    @RequestMapping("auth/logout")
    public Object logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return ResponseUtils.ok();
    }

    @RequestMapping("user/index")
    public Object list(HttpServletRequest request) {
        Subject  subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //通过请求头获得userId，进而可以获得一切关于user的信息
        //**************************
        if (user.getId() == null) {
            return ResponseUtils.fail();
        }

        Map<String, Object> data = new HashMap<>();
        Map<Object, Object> myorder = new HashMap<>();
        //未付款 101
        int unpaid=0;
        //待发货==已付款 201
        int unship=0;
        //待收货==已发货 301
        int unrecv=0;
        //未评价==已收货 401
        int uncomment=0;
        //根据userId查询订单信息
        OrderExample example=new OrderExample();
        example.createCriteria().andUserIdEqualTo(user.getId());
        List<Order> orderList = orderMapper.selectByExample(example);
        for (Order order : orderList) {
            if (order.getOrderStatus()==101){
                unpaid++;
            }else  if (order.getOrderStatus()==201){
                unship++;
            }else  if (order.getOrderStatus()==301){
                unrecv++;
            }else  if (order.getOrderStatus()==401){
                uncomment++;
            }
        }
        myorder.put("unpaid",unpaid);
        myorder.put("unship",unship);
        myorder.put("unrecv",unrecv);
        myorder.put("uncomment",uncomment);
        data.put("order", myorder);
        //***********************************

        return ResponseUtils.ok(data);
    }

}
