package com.cskaoyan.project.mall.service.permissionService;

import com.cskaoyan.project.mall.domain.Permission;
import com.cskaoyan.project.mall.domain.PermissionExample;
import com.cskaoyan.project.mall.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/17 21:31
 */
@Service
public class PermissionServiceImpl implements  PermissionService {
    @Autowired
    PermissionMapper permissionMapper;
    @Override
    public Set<String> queryByRoleIds(int[] roleIds) {
        Set<String> permissions = new HashSet<String>();
        if(roleIds.length == 0){
            return permissions;
        }
        // int[] 转 List<Integer>
       List<Integer> list1 = Arrays.stream(roleIds).boxed().collect(Collectors.toList());
        // 1.使用Arrays.stream将int[]转换成IntStream。
        // 2.使用IntStream中的boxed()装箱。将IntStream转换成Stream<Integer>。
        // 3.使用Stream的collect()，将Stream<T>转换成List<T>，因此正是List<Integer>。
        PermissionExample example = new PermissionExample();

        example.or().andRoleIdIn(list1).andDeletedEqualTo(false);
        List<Permission> permissionList = permissionMapper.selectByExample(example);

        for(Permission permission : permissionList){
            permissions.add(permission.getPermission());
        }

        return permissions;
    }

    @Override
    public Set<String> queryByRoleId(int roleId) {
        Set<String> permissions = new HashSet<String>();

        PermissionExample example = new PermissionExample();
        example.or().andRoleIdEqualTo(roleId).andDeletedEqualTo(false);
        List<Permission> permissionList = permissionMapper.selectByExample(example);

        for(Permission permission : permissionList){
            permissions.add(permission.getPermission());
        }

        return permissions;
    }

    @Override
    public int insertSelective(Permission record) {
        return permissionMapper.insertSelective(record);
    }

    @Override
    public List<Permission> selectByExample(PermissionExample example) {
        return permissionMapper.selectByExample(example);
    }

    @Override
    public boolean checkSuperPermission(Integer roleId) {
        if(roleId == null){
            return false;
        }
       PermissionExample example = new PermissionExample();
        example.or().andRoleIdEqualTo(roleId).andPermissionEqualTo("*").andDeletedEqualTo(false);
        return permissionMapper.countByExample(example) != 0;
    }

    @Override
    public int deleteByExample(int roleId) {
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andRoleIdEqualTo(roleId);
        return permissionMapper.deleteByExample(permissionExample);
    }
}
