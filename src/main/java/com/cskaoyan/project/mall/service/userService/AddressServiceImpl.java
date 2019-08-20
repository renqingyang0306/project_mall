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
}
