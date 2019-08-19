package com.cskaoyan.project.mall.service.adminService;

import com.cskaoyan.project.mall.domain.Admin;
import com.cskaoyan.project.mall.domain.AdminExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/16 11:48
 */
public interface AdminService {

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);
    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    List<Admin> findAllList(int page,int limit,String username);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    List<Admin> findAdminByUsername(String username);
}
