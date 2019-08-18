package com.cskaoyan.project.mall.service.userService;

import com.cskaoyan.project.mall.domain.User;
import com.cskaoyan.project.mall.domain.UserExample;

import java.util.List;

public interface UserService {
    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> findAllUser(int page,int limit,String username,String mobile);
}
