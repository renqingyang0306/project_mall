package com.cskaoyan.project.mall.service.roleService;

import com.cskaoyan.project.mall.domain.Role;
import com.cskaoyan.project.mall.domain.RoleExample;
import com.cskaoyan.project.mall.mapper.RoleMapper;
import com.cskaoyan.project.mall.utils.RoleBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/16 14:18
 */
@Service
public class RoleServiceImpl implements  RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Override
    public int deleteByExample(RoleExample example) {
        return roleMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Role record) {
        return roleMapper.insert(record);
    }

    @Override
    public List<Role> selectByExample(RoleExample example) {
        return roleMapper.selectByExample(example);
    }

    @Override
    public Role selectByPrimaryKey(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Role record) {
        return roleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Role record) {
        return roleMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<RoleBean> finaAllList() {
        RoleExample roleExample = new RoleExample();
        List<Role> roles = roleMapper.selectByExample(roleExample);
        //重新改装Role对象，用于查询Admin
        List<RoleBean> list=new ArrayList<>();
        for (Role role : roles) {
            RoleBean roleBean=new RoleBean(role.getId(),role.getName());
            list.add(roleBean);
        }
        return list;
    }
}
