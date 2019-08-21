package com.cskaoyan.project.mall.service.userService;

import com.cskaoyan.project.mall.domain.User;
import com.cskaoyan.project.mall.domain.UserExample;
import com.cskaoyan.project.mall.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public int deleteByExample(UserExample example) {
        return userMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public List<User> selectByExample(UserExample example) {

        return userMapper.selectByExample(example);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<User> findAllUser(int page, int limit,String username,String mobile) {
        PageHelper.startPage(page, limit);
        UserExample userExample = new UserExample();
        List<User> users = null;
        if(username == null && mobile ==null) {
            users = userMapper.selectByExample(userExample);
        } else if(username != null && mobile == null){
            userExample.createCriteria().andUsernameLike("%" + username + "%");
            users = userMapper.selectByExample(userExample);
        } else if(username == null && mobile != null){
            userExample.createCriteria().andMobileLike("%" + mobile + "%");
            users = userMapper.selectByExample(userExample);
        } else {
            userExample.createCriteria()
                    .andUsernameLike("%" + username + "%")
                    .andMobileLike("%" + mobile + "%");
            users = userMapper.selectByExample(userExample);
        }
        return users;
    }

    @Override
    public List<User> findUserByUsernameAndPassword(String username, String password) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password);
        return userMapper.selectByExample(example);
    }
}
