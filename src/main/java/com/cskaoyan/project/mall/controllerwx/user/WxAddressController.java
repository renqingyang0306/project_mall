package com.cskaoyan.project.mall.controllerwx.user;

import com.alibaba.fastjson.JSONObject;
import com.cskaoyan.project.mall.domain.Address;
import com.cskaoyan.project.mall.domain.Region;
import com.cskaoyan.project.mall.domain.User;
import com.cskaoyan.project.mall.service.mall.RegionService;
import com.cskaoyan.project.mall.service.userService.AddressService;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.cskaoyan.project.mall.vo.WxRspAddress;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author 申涛涛
 * @date 2019/8/22 9:30
 */
@Controller
public class WxAddressController {
    @Autowired
    AddressService addressService;
    @Autowired
    RegionService regionService;

    @RequestMapping("/wx/address/list")
    @ResponseBody
    public ResponseUtils queryAddressList() {
        ResponseUtils responseUtils = new ResponseUtils();
        //获取当前登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Address> addressList = new ArrayList<>();
        if (user != null) {
            addressList = addressService.queryAllAddressByUserId(user.getId());
        }

        if (user == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("用户信息丢失");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(addressList);
        }
        return responseUtils;
    }

    @RequestMapping("/wx/address/save")
    @ResponseBody
    public ResponseUtils insertAddress(@RequestBody Address address) {
        ResponseUtils responseUtils = new ResponseUtils();
        //获取当前登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Address> addressList = new ArrayList<>();
        if (user == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("用户未登录");
            return responseUtils;
        }
        Date date = new Date();
        //addressId == 0 说明是新增
        int flag = 0;
        if (address.getId() == 0) {
            address.setId(null);
            address.setDeleted(false);
            address.setAddTime(date);
            address.setUpdateTime(date);
            address.setUserId(user.getId());
            flag = addressService.insertAddress(address);
        } else {
            //查询原有的address信息
            Address address1 = addressService.queryAddressById(address.getId());

            address.setUserId(address1.getUserId());
            //这两项不允许用户编辑时做修改
            address.setAddTime(address1.getAddTime());
            address.setDeleted(address1.getDeleted());

            address.setUpdateTime(date);

            flag = addressService.updateAddress(address);
        }



        if (flag == 0) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("地址编辑或新增失败");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(address.getId());
        }
        return responseUtils;
    }

    @RequestMapping("/wx/address/detail")
    @ResponseBody
    public ResponseUtils insertAddress(Integer id) {
        ResponseUtils responseUtils = new ResponseUtils();
        //获取当前登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Address> addressList = new ArrayList<>();
        if (user == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("用户未登录");
            return responseUtils;
        }
        if (id == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("id 不能为 null");
        }
        Address address = addressService.queryAddressById(id);
        String provinceName = null;
        String cityName = null;
        String areaName = null;
        if (address != null) {
            Region province = regionService.queryRegionById(address.getProvinceId());
            if (province != null) {
                provinceName = province.getName();
            }
            Region city = regionService.queryRegionById(address.getCityId());
            if (city != null) {
                cityName = city.getName();
            }
            Region area = regionService.queryRegionById(address.getAreaId());
            if (area != null) {
                areaName = area.getName();
            }
        }
        WxRspAddress wxRspAddress = transfer(address);
        wxRspAddress.setProvinceName(provinceName);
        wxRspAddress.setCityName(cityName);
        wxRspAddress.setAreaName(areaName);

        if (user == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("用户信息丢失");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(wxRspAddress);
        }
        return responseUtils;
    }

    @RequestMapping("/wx/address/delete")
    @ResponseBody
    public ResponseUtils deleteAddress(@RequestBody JSONObject jsonObject) {
        ResponseUtils responseUtils = new ResponseUtils();
        //获取当前登录的用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("用户未登录");
            return responseUtils;
        }
        Integer id = (Integer) jsonObject.get("id");
        if (id == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("id 不能为 null");
            return responseUtils;
        }
        Address address = addressService.queryAddressById(id);
        if (address == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("该地址数据库不存在");
            return responseUtils;
        }
        //逻辑删除 address
        int deleted = addressService.deleteLogicAddressByDeleted(address);


        if (deleted == 0) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("删除失败");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功");
            responseUtils.setData(null);
        }
        return responseUtils;
    }

    //将 address 转换为前端所需的 WxRspAddress
    private WxRspAddress transfer(Address address) {
        WxRspAddress wxRspAddress = new WxRspAddress();
        wxRspAddress.setId(address.getId());
        wxRspAddress.setName(address.getName());
        wxRspAddress.setUserId(address.getUserId());
        wxRspAddress.setProvinceId(address.getProvinceId());
        wxRspAddress.setCityId(address.getCityId());
        wxRspAddress.setAreaId(address.getAreaId());
        wxRspAddress.setAddress(address.getAddress());
        wxRspAddress.setMobile(address.getMobile());
        wxRspAddress.setIsDefault(address.getIsDefault());
        wxRspAddress.setAddTime(address.getAddTime());
        wxRspAddress.setUpdateTime(address.getUpdateTime());
        wxRspAddress.setDeleted(address.getDeleted());
        return wxRspAddress;
    }
    //将 List<Address> 转换为 List<WxRspAddress>
    private List<WxRspAddress> transfer(List<Address> addresses) {
        List<WxRspAddress> wxRspAddressList = new ArrayList<>();
        for (Address address : addresses) {
            WxRspAddress transfer = transfer(address);
            wxRspAddressList.add(transfer);
        }
        return wxRspAddressList;
    }
}
