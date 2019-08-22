package com.cskaoyan.project.mall.service.permissionService;

import com.cskaoyan.project.mall.domain.SystemPermission;
import com.cskaoyan.project.mall.domain.SystemPermissionExample;
import com.cskaoyan.project.mall.mapper.SystemPermissionMapper;
import com.cskaoyan.project.mall.vo.PermVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/22 10:25
 */
@Component
public class SystemPermissionServiceImpl implements SystemPermissionService{

    @Autowired
    SystemPermissionMapper systemPermissionMapper;
    @Override
    public List<SystemPermission> selectByExample(SystemPermissionExample example) {
        return systemPermissionMapper.selectByExample(example);
    }

    @Override
    public SystemPermission selectByPrimaryKey(String id) {
        return systemPermissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PermVo> findAllSystemPermisssion() {
        SystemPermissionExample example=new SystemPermissionExample();
        example.createCriteria().andPidEqualTo("0");
        List<SystemPermission> parentList = systemPermissionMapper.selectByExample(example);
        List<PermVo> resultList=new ArrayList<>();
        //遍历一级标题
        for (SystemPermission systemPermission : parentList) {
            PermVo permVo=new PermVo();
            permVo.setId(systemPermission.getId());
            permVo.setLabel(systemPermission.getLabel());
            //查询二级标题list
            SystemPermissionExample secondExample=new SystemPermissionExample();
            secondExample.createCriteria().andPidEqualTo(systemPermission.getId());
            List<SystemPermission> secondList = systemPermissionMapper.selectByExample(secondExample);

            List<PermVo> second=new ArrayList<>();
            for (SystemPermission secondPermission : secondList) {
                PermVo permVo2=new PermVo();
                permVo2.setId(secondPermission.getId());
                permVo2.setLabel(secondPermission.getLabel());
                //查询三级级标题list
                SystemPermissionExample thirdExample=new SystemPermissionExample();
                thirdExample.createCriteria().andPidEqualTo(secondPermission.getId());
                List<SystemPermission> thirdList = systemPermissionMapper.selectByExample(thirdExample);

                List<PermVo> third=new ArrayList<>();
                for (SystemPermission thirdPermission : thirdList) {
                    PermVo permVo3=new PermVo();
                    permVo3.setId(thirdPermission.getId());
                    permVo3.setLabel(thirdPermission.getLabel());
                    permVo3.setApi(thirdPermission.getApi());
                    third.add(permVo3);
                }
                //想把三级标题list给二级children属性
                permVo2.setChildren(third);
                second.add(permVo2);
            }
            //想把二级标题list给一级children属性
            permVo.setChildren(second);
            resultList.add(permVo);
        }
        return resultList;
    }
}
