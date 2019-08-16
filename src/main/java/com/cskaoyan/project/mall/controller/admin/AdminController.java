package com.cskaoyan.project.mall.controller.admin;

import com.cskaoyan.project.mall.domain.Admin;
import com.cskaoyan.project.mall.service.adminService.AdminService;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.github.pagehelper.PageInfo;
import javafx.beans.binding.ObjectBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/16 11:55
 */
@RestController
@RequestMapping("admin/admin")
public class AdminController {

    @Autowired
    AdminService adminService;
    @RequestMapping("list")
    public ResponseUtils<PageBean>  list(int page,int limit,String username){
        List<Admin> allList = adminService.findAllList(page, limit,username);
        PageInfo<Admin> pageInfo=new PageInfo<>(allList);
        PageBean<Admin> pageBean=new PageBean<>(allList,pageInfo.getTotal());
        ResponseUtils<PageBean>  responseUtils=new ResponseUtils<>(0,pageBean,"成功");
        return responseUtils;
    }
    @RequestMapping("update")
    public ResponseUtils<Admin> update(@RequestBody Admin admin){
        int i = adminService.updateByPrimaryKeySelective(admin);
        ResponseUtils<Admin> responseUtils=null;
        if (i==1){
            Admin admin1 = adminService.selectByPrimaryKey(admin.getId());
            responseUtils=new ResponseUtils<>(0,admin1,"成功");
        }else {
            responseUtils=new ResponseUtils<>(1,admin,"失败");
        }
        return responseUtils;
    }
    @RequestMapping("delete")
    public ResponseUtils<Object> delete(@RequestBody Admin admin){
        int i = adminService.deleteByPrimaryKey(admin.getId());
        ResponseUtils<Object> responseUtils=null;
        if (i==1){
            responseUtils=new ResponseUtils<>(0,"成功");
        }else {
            responseUtils=new ResponseUtils<>(1,"失败");
        }
        return responseUtils;
    }
    @RequestMapping("create")
    public ResponseUtils<Object> insert(@RequestBody Admin admin){
        int i = adminService.insertSelective(admin);
        ResponseUtils<Object> responseUtils=null;
        if (i==1){
            responseUtils=new ResponseUtils<>(0,"成功");
        }else {
            responseUtils=new ResponseUtils<>(1,"失败");
        }
        return responseUtils;
    }

}
