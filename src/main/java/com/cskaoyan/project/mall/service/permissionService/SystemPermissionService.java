package com.cskaoyan.project.mall.service.permissionService;

import com.cskaoyan.project.mall.domain.SystemPermission;
import com.cskaoyan.project.mall.domain.SystemPermissionExample;
import com.cskaoyan.project.mall.vo.PermVo;

import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/22 10:25
 */
public interface SystemPermissionService {
    List<SystemPermission> selectByExample(SystemPermissionExample example);

    SystemPermission selectByPrimaryKey(String id);

    List<PermVo> findAllSystemPermisssion();
}
