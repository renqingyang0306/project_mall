package com.cskaoyan.project.mall.controller.auth;

import com.cskaoyan.project.mall.domain.Admin;
import com.cskaoyan.project.mall.domain.AdminExample;
import com.cskaoyan.project.mall.service.NumberTotalService;
import com.cskaoyan.project.mall.service.adminService.AdminService;
import com.cskaoyan.project.mall.service.logService.LogHelper;
import com.cskaoyan.project.mall.service.permissionService.PermissionService;
import com.cskaoyan.project.mall.service.roleService.RoleService;
import com.cskaoyan.project.mall.utils.IpUtil;
import com.cskaoyan.project.mall.utils.NumberTotal;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.cskaoyan.project.mall.utils.RolesBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/15 23:47
 */
@RestController
@RequestMapping("admin")
public class AuthController {
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
        AdminExample adminExample=new AdminExample();
        adminExample.createCriteria().andUsernameEqualTo(admin.getUsername()).andPasswordEqualTo(admin.getPassword());
        //获取session，验证是否用户已经登录
        Admin sessionAdmin = (Admin) request.getSession().getAttribute("admin");

        /*Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(new UsernamePasswordToken(admin.getUsername(), admin.getPassword()));

        } catch (UnknownAccountException uae) {
            logHelper.logAuthFail("登录", "用户帐号或密码不正确");
            return ResponseUtils.fail(605, "用户帐号或密码不正确");
        } catch (LockedAccountException lae) {
            logHelper.logAuthFail("登录", "用户帐号已锁定不可用");
            return ResponseUtils.fail(605, "用户帐号已锁定不可用");

        } catch (AuthenticationException ae) {
            logHelper.logAuthFail("登录", "认证失败");
            return ResponseUtils.fail(605, "认证失败");
        }*/

        if (sessionAdmin!=null && admin==null){
           return  ResponseUtils.ok("9e910f0f-0eab-473f-ac04-52a323584516");
        }
        List<Admin> admins = adminService.selectByExample(adminExample);
        if (admins.size()!=0) {
            //用户存在
            Admin loginadmin = admins.get(0);
            request.getSession().setAttribute("admin",loginadmin);
            return  ResponseUtils.ok("9e910f0f-0eab-473f-ac04-52a323584516");
        }else {
            //用户不存在
            return ResponseUtils.fail(605, "用户帐号或密码不正确");
        }
       /* currentUser = SecurityUtils.getSubject();
        Admin admin1 = (Admin) currentUser.getPrincipal();
        admin.setLastLoginIp(IpUtil.getIpAddr(request));
        admin.setLastLoginTime(new Date());
        adminService.updateByPrimaryKey(admin1);

        logHelper.logAuthSucceed("登录");

        return  ResponseUtils.ok("9e910f0f-0eab-473f-ac04-52a323584516");*/
    }
    /*
     *
     */
    @RequiresAuthentication
    @RequestMapping("/logout")
    public Object logout() {
        Subject currentUser = SecurityUtils.getSubject();

        logHelper.logAuthSucceed("退出");
        currentUser.logout();
        return ResponseUtils.ok();
    }

    @RequestMapping("auth/info")
    public ResponseUtils<RolesBean<String>> info(HttpServletRequest request){
        /*Subject currentUser = SecurityUtils.getSubject();
        Admin admin = (Admin) currentUser.getPrincipal();*/
        RolesBean<String> rolesBean=new RolesBean<>();

       //rolesBean.setName(sessionAdmin.getUsername());
        //int[] roleIds = admin.getRoleIds();

        List<String> roles = new ArrayList<>();
        roles.add("超级管理员");
        rolesBean.setRoles(roles);
        List<String> roles1 =  new ArrayList<>();
        roles1.add("*");
        /*roles1.add("GET /admin/log/list");
        roles1.add("POST /admin/admin/create");
        roles1.add("POST /admin/admin/delete");
        roles1.add("POST /admin/admin/update");
        roles1.add("POST /admin/admin/list");*/
        rolesBean.setPerms(roles1);
        rolesBean.setAvatar("http://localhost/static/pic/admin/B6C819328E0642A0BAE58250F4488D0F.jpg");
        ResponseUtils<RolesBean<String>> json =
                new ResponseUtils<RolesBean<String>>(0,rolesBean,"成功");

        return  json;

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
