package com.zhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.pojo.Role;
import org.springframework.stereotype.Repository;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author Distance
 * @since 2021-03-23 11:02:45
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

}