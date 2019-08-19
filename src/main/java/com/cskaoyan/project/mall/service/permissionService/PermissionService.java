package com.cskaoyan.project.mall.service.permissionService;

import java.util.Set;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/17 21:29
 */
public interface PermissionService {
    public Set<String> queryByRoleIds(int[] roleIds);
    public Set<String> queryByRoleId(int roleId);
}
