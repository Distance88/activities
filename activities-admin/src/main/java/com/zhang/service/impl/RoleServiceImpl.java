package com.zhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.mapper.RoleMapper;
import com.zhang.pojo.Role;
import com.zhang.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色信息表(Role)表服务接口
 *
 * @author Distance
 * @since 2021-03-23 11:02:45
 */
@Service
@SuppressWarnings("unchecked")
public class RoleServiceImpl implements RoleService {


    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAllRole() {
        return roleMapper.selectList(null);
    }

    @Override
    public List<Role> findRoleByCondition(Role role) {

        QueryWrapper<Role> wrapper = new QueryWrapper<Role>();

         wrapper.eq("name",role.getName())
                .or()
                .eq("permkey",role.getPermkey())
                .or()
                .eq("status",role.getStatus());


        return roleMapper.selectList(wrapper);
    }
}