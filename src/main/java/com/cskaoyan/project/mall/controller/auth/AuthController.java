package com.cskaoyan.project.mall.controller.auth;

import com.cskaoyan.project.mall.domain.Admin;
import com.cskaoyan.project.mall.domain.AdminExample;
import com.cskaoyan.project.mall.service.NumberTotalService;
import com.cskaoyan.project.mall.service.adminService.AdminService;
import com.cskaoyan.project.mall.service.logService.LogHelper;
import com.cskaoyan.project.mall.service.permissionService.PermissionService;
import com.cskaoyan.project.mall.service.roleService.RoleService;
import com.cskaoyan.project.mall.utils.*;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/15 23:47
 */
@RestController
@RequestMapping("admin")
public class AuthController {

    private final Log logger = LogFactory.getLog(AuthController.class);
    @Autowired
    NumberTotalService numberTotalService;
    @Autowired
    AdminService adminService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RoleService roleService;
    @Autowired
    LogHelper logHelper;

    @RequestMapping("auth/login")
    public Object login(@RequestBody  Admin admin, HttpServletRequest request){
        //如果用户名密码为空
        if (admin.getUsername()==null || admin.getPassword()==null) {
            //返回错误信息
            return ResponseUtils.badArgument();
        }
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(admin.getUsername(), admin.getPassword()));
        } catch (UnknownAccountException uae) {
            logHelper.logAuthFail("登录", "用户帐号或密码不正确");
            return ResponseUtils.fail(605, "用户帐号或密码不正确");
        } catch (LockedAccountException lae) {
            logHelper.logAuthFail("登录", "用户帐号已锁定不可用");
            return ResponseUtils.fail(605, "用户帐号已锁定不可用");

        } catch (AuthenticationException ae) {
            logHelper.logAuthFail("登录", "认证失败");
            return ResponseUtils.fail(605, "认证失败");
        }
        subject = SecurityUtils.getSubject();
        //获取认证后的用户信息，通过Realm进行封装的
        Admin admin1 = (Admin) subject.getPrincipal();
        admin.setLastLoginIp(IpUtil.getIpAddr(request));
        admin.setLastLoginTime(new Date());
        adminService.updateByPrimaryKey(admin1);

        logHelper.logAuthSucceed("登录");
        //获取返回taken信息
        Serializable id = subject.getSession().getId();
        return  ResponseUtils.ok(id);
    }
    /*
     * 退出登录
     */
    @RequiresAuthentication
    @RequestMapping("auth/logout")
    public Object logout() {
        Subject currentUser = SecurityUtils.getSubject();
        logHelper.logAuthSucceed("退出");
        currentUser.logout();
        return ResponseUtils.ok();
    }

    @RequestMapping("auth/info")
    public ResponseUtils<RolesBean<String>> info(HttpServletRequest request){
        Subject currentUser = SecurityUtils.getSubject();
        Admin admin = (Admin) currentUser.getPrincipal();
        RolesBean<String> rolesBean=new RolesBean<>();

        //从数据库中取出 当前用户的授权信息,为用户授权,（可以查询role，也可以查询permission）
        int[] roleIds = admin.getRoleIds();
        Set<String> roles = roleService.queryByIds(roleIds);
        Set<String> permissions = permissionService.queryByRoleIds(roleIds);
        Set<String> transfer = PermissionUtils.transfer(permissions);
        rolesBean.setRoles(roles);
        rolesBean.setPerms(transfer);
        rolesBean.setName(admin.getUsername());
        rolesBean.setAvatar(admin.getAvatar());
        ResponseUtils<RolesBean<String>> json =
                new ResponseUtils<RolesBean<String>>(0,rolesBean,"成功");

        return  json;

    }

    @RequestMapping("auth/401")
    public Object page401() {
        return ResponseUtils.unlogin();
    }
    @RequestMapping("auth/403")
    public Object page403() {
        return ResponseUtils.unauthz();
    }

    @RequestMapping("dashboard")
    public ResponseUtils<NumberTotal> dashboard(){
        NumberTotal total=new NumberTotal(
                numberTotalService.goodsTotal(),numberTotalService.orderTotal(),numberTotalService.productTotal(),numberTotalService.userTotal());
        ResponseUtils<NumberTotal> json =
                new ResponseUtils<NumberTotal>(0,total,"成功");
        return  json;
    }
}
