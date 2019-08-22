package com.cskaoyan.project.mall.service.userService;

import com.cskaoyan.project.mall.domain.Address;
import com.cskaoyan.project.mall.domain.AddressExample;

import java.util.List;

public interface AddressService {
    List<Address> selectByExample(AddressExample example);

    List<Address> findAllAddress(int page,int limit,String name,Integer userId);
    List<Address> findAllAddress();

    List<Address> queryAllAddressByUserId(Integer userId);

    int insertAddress(Address address);
    int updateAddress(Address address);
    int deleteRealAddressById(Integer id);
    int deleteLogicAddressByDeleted(Address address);

    Address queryAddressByUidAndAddressId(Integer uid, int addressId);

    Address queryAddressById(Integer id);
}
