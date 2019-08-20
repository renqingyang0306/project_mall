package com.cskaoyan.project.mall.service.adminService;

import com.cskaoyan.project.mall.domain.Admin;
import com.cskaoyan.project.mall.domain.AdminExample;
import com.cskaoyan.project.mall.mapper.AdminMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/16 11:49
 */
@Service
public class AdminServiceImpl implements  AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Override
    public int deleteByExample(AdminExample example) {
        return adminMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Admin record) {
        return adminMapper.insert(record);
    }

    @Override
    public int insertSelective(Admin record) {

        record.setAddTime(new Date());
        record.setUpdateTime(new Date());
        return adminMapper.insertSelective(record);
    }

    @Override
    public List<Admin> selectByExample(AdminExample example) {
        return adminMapper.selectByExample(example);
    }

    @Override
    public List<Admin> findAllList(int page, int limit,String  username) {
        PageHelper.startPage(page,limit);
        AdminExample adminExample=new AdminExample();

        List<Admin> listAdmin=null;
        //模糊查询
        if (username!=null && !("").equals(username)){
            adminExample.createCriteria().andUsernameLike("%"+username+"%");
            listAdmin=adminMapper.selectByExample(adminExample);
        }else {
            //正常查询
            listAdmin=adminMapper.selectByExample(adminExample);
        }
        return listAdmin;

    }

    @Override
    public Admin selectByPrimaryKey(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Admin record) {
        return adminMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Admin record) {
        return adminMapper.updateByPrimaryKey(record);
    }
}
