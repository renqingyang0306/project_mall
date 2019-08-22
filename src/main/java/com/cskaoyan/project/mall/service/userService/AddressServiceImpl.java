package com.cskaoyan.project.mall.service.userService;

import com.cskaoyan.project.mall.domain.Address;
import com.cskaoyan.project.mall.domain.AddressExample;
import com.cskaoyan.project.mall.mapper.AddressMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressServiceImpl implements AddressService{
    @Autowired
    AddressMapper addressMapper;

    @Override
    public List<Address> selectByExample(AddressExample example) {
        return addressMapper.selectByExample(example);
    }

    @Override
    public List<Address> findAllAddress(int page, int limit,String name,Integer userId) {
        PageHelper.startPage(page,limit);
        AddressExample example = new AddressExample();
        List<Address> addresses = null;
        if(name == null && userId == null){
            addresses = addressMapper.selectByExample(example);
        } else if (name != null && userId == null) {
            example.createCriteria().andNameLike("%" + name + "%");
            addresses = addressMapper.selectByExample(example);
        } else if(name == null && userId != null){
            example.createCriteria().andUserIdEqualTo(userId);
            addresses = addressMapper.selectByExample(example);
        } else{
            example.createCriteria().andNameLike("%" + name + "%")
                    .andUserIdEqualTo(userId);
            addresses = addressMapper.selectByExample(example);
        }
        return addresses;
    }

    @Override
    public List<Address> findAllAddress() {
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<Address> addresses = addressMapper.selectByExample(addressExample);
        return addresses;
    }

    @Override
    public Address queryAddressById(Integer id) {
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andIdEqualTo(id);
        List<Address> addresses = addressMapper.selectByExample(addressExample);
        if (addresses.size() > 0) {
            return addresses.get(0);
        }
        return null;
    }

    @Override
    public List<Address> queryAllAddressByUserId(Integer userId) {
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(userId);
        List<Address> addresses = addressMapper.selectByExample(addressExample);
        return addresses;
    }

    @Override
    public int insertAddress(Address address) {
        int insert = addressMapper.insert(address);
        return insert;
    }

    @Override
    public int updateAddress(Address address) {
        return addressMapper.updateByPrimaryKey(address);
    }

    @Override
    public int deleteRealAddressById(Integer id) {
        return addressMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteLogicAddressByDeleted(Address address) {
        address.setDeleted(true);
        return addressMapper.updateByPrimaryKey(address);
    }
}
