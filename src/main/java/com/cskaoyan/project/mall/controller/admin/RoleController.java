package com.cskaoyan.project.mall.controller.admin;

import com.cskaoyan.project.mall.domain.Permission;
import com.cskaoyan.project.mall.domain.Role;
import com.cskaoyan.project.mall.service.permissionService.PermissionService;
import com.cskaoyan.project.mall.service.permissionService.SystemPermissionService;
import com.cskaoyan.project.mall.service.roleService.RoleService;
import com.cskaoyan.project.mall.utils.JacksonUtil;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.cskaoyan.project.mall.utils.RoleBean;
import com.cskaoyan.project.mall.vo.PermVo;
import com.github.pagehelper.PageInfo;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/16 14:15
 */
@RestController
@RequestMapping("admin/role")
public class RoleController {
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    SystemPermissionService systemPermissionService;

    @RequestMapping("options")
    public ResponseUtils<List<RoleBean>> options(){
        List<RoleBean> list=roleService.finaAllList();
        ResponseUtils<List<RoleBean>> responseUtils = new ResponseUtils<>(0,list,"成功");
        return responseUtils;
    }
    @RequestMapping("list")
    public Object  list(int page,int limit,String name){
        List<Role> allList = roleService.findAllList(page, limit,name);
        PageInfo<Role> pageInfo=new PageInfo<>(allList);
        PageBean<Role> pageBean=new PageBean<>(allList,pageInfo.getTotal());
        return  ResponseUtils.ok(pageBean);
    }
    @RequestMapping("update")
    public Object update(@RequestBody Role role){
        int i = roleService.updateByPrimaryKeySelective(role);
        if (i==1){
            return ResponseUtils.ok();
        }else {
            return ResponseUtils.fail();
        }

    }
    @RequestMapping("delete")
    public Object delete(@RequestBody Role role){
        int i = roleService.deleteByPrimaryKey(role.getId());
        if (i==1){
           return  ResponseUtils.ok();
        }else {
            return  ResponseUtils.fail(642,"当前角色存在管理员，不能删除");
        }
    }
    @RequestMapping("create")
    public Object insert(@RequestBody Role role){
        int i = roleService.insertSelective(role);
        ResponseUtils<Object> responseUtils=null;
        if (i==1){
            return  ResponseUtils.ok(role);
        }else {
            return  ResponseUtils.fail();
        }
    }
    //查询系统全部权限
    @GetMapping("permissions")
    public Object permissions( int roleId){
        Map<String,Object> map=new HashMap<>();
        Set<String> queryByRoleId = permissionService.queryByRoleId(roleId);
        List<PermVo> systemPermissions = systemPermissionService.findAllSystemPermisssion();
        map.put("assignedPermissions",queryByRoleId);
        map.put("systemPermissions",systemPermissions);

        return ResponseUtils.ok(map);
    }
    /**
     * 更新管理员的权限
     * @param body
     * @return
     */
    @PostMapping("/permissions")
    public Object updatePermissions(@RequestBody String body) {
        Integer roleId = JacksonUtil.parseInteger(body, "roleId");
        List<String> permissions = JacksonUtil.parseStringList(body, "permissions");
        if (roleId == null || permissions == null) {
            return ResponseUtils.badArgument();
        }

        // 如果修改的角色是超级权限，则拒绝修改。
        if (permissionService.checkSuperPermission(roleId)) {
            return ResponseUtils.fail(604, "当前角色的超级权限不能变更");
        }
        // 先删除旧的权限，再更新新的权限
        permissionService.deleteByExample(roleId);
        for (String permission : permissions) {
            Permission mallPermission = new Permission();
            mallPermission.setRoleId(roleId);
            mallPermission.setPermission(permission);
            mallPermission.setAddTime(new Date());
            mallPermission.setUpdateTime(new Date());
            permissionService.insertSelective(mallPermission);
        }
        return ResponseUtils.ok();
    }

}
