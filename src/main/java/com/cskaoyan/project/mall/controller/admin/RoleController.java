package com.cskaoyan.project.mall.controller.admin;

import com.cskaoyan.project.mall.domain.Role;
import com.cskaoyan.project.mall.service.roleService.RoleService;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.cskaoyan.project.mall.utils.RoleBean;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
