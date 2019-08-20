package com.cskaoyan.project.mall.controller.userController;

import com.cskaoyan.project.mall.domain.Address;
import com.cskaoyan.project.mall.service.userService.AddressService;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("admin/address")
public class AddressController {
    @Autowired
    AddressService addressService;
    @RequestMapping("list")
    @ResponseBody
    public ResponseUtils<PageBean> list(int page,int limit,String name,Integer userId){
        List<Address> allAddress = addressService.findAllAddress(page, limit,name,userId);
        PageInfo<Address> pageInfo = new PageInfo<>(allAddress);
        PageBean<Address> pageBean = new PageBean<>(allAddress, pageInfo.getTotal());
        ResponseUtils<PageBean> responseUtils = new ResponseUtils<>(0, pageBean, "成功");
        return responseUtils;
    }
}
