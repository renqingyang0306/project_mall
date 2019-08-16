package com.cskaoyan.project.mall.service.roleService;

import com.cskaoyan.project.mall.domain.Role;
import com.cskaoyan.project.mall.domain.RoleExample;
import com.cskaoyan.project.mall.utils.RoleBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/16 14:17
 */
public interface RoleService {
    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<RoleBean> finaAllList();
}
