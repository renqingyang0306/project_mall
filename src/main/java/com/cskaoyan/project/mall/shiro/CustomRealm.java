package com.cskaoyan.project.mall.shiro;

import com.cskaoyan.project.mall.domain.Admin;
import com.cskaoyan.project.mall.domain.User;
import com.cskaoyan.project.mall.service.adminService.AdminService;
import com.cskaoyan.project.mall.service.permissionService.PermissionService;
import com.cskaoyan.project.mall.service.roleService.RoleService;
import com.cskaoyan.project.mall.service.userService.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/19 10:20
 */
@Component
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    AdminService adminService;
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    UserService userService;
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //关注凭证（密码）
        //获取执行验证的用户名，和密码
        //方法一
        /*String username = (String) authenticationToken.getPrincipal();
        String password = (String) authenticationToken.getCredentials();*/
        //方法二
        MallToken mallToken = (MallToken) authenticationToken;
        String username = mallToken.getUsername();
        String password = new String(mallToken.getPassword());
        String type = mallToken.getType();
        if ("admin".equals(type)){
            //根据用户名 去查询数据库中的密码
            List<Admin> adminList= adminService.findAdminByUsername(username);
            Assert.state(adminList.size() < 2, "同一个用户名存在两个账户");
            if (adminList.size() == 0) {
                throw new UnknownAccountException("找不到用户（" + username + "）的帐号信息");
            }
            Admin admin = adminList.get(0);
            //参数1：principle是要给到授权使用的
            //参数2：通常写来源于数据库的密码 如果和subject。login中token的密码匹配 才能通过认证
            //参数3：当前的域名（基本没用）
            return new SimpleAuthenticationInfo(admin, admin.getPassword(),"admin");
        }
        else{
            //微信用户登录
            List<User> userList = userService.findUserByUsernameAndPassword(username, password);
            if (userList.size() == 0) {
                throw new UnknownAccountException("找不到用户（" + username + "）的帐号信息");
            }
            User user = userList.get(0);

            return new SimpleAuthenticationInfo(user, user.getPassword(), "wx");
        }

    }

    //授权,本次项目没用，用的时候方法需要加入请求权限：@RequiresPermissions("admin:ad:list")
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalS) {
        if (principalS == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        //来源于SimpleAuthenticationInfo的第一个参数，可以是String类型，也可以是Javabean
        Admin admin = (Admin) principalS.getPrimaryPrincipal();
        //从数据库中取出 当前用户的授权信息,为用户授权,（可以查询role，也可以查询permission）
        int[] roleIds = admin.getRoleIds();
        Set<String> roles = roleService.queryByIds(roleIds);
        Set<String> permissions = permissionService.queryByRoleIds(roleIds);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        return info;
    }
}
