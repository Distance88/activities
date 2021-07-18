package com.zhang.service;

import com.zhang.pojo.Role;

import java.util.List;

/**
 * 角色信息表(Role)表服务接口
 *
 * @author Distance
 * @since 2021-03-23 11:02:45
 */
public interface RoleService {

    List<Role> findAllRole();

    List<Role> findRoleByCondition(Role role);
}