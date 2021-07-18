package com.zhang.controller;

import com.zhang.pojo.Role;
import com.zhang.restful.Response;
import com.zhang.restful.ResponseResult;
import com.zhang.service.impl.RoleServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色信息表(Role)表控制层
 *
 * @author Distance
 * @since 2021-03-23 11:02:45
 */
@RestController
@RequestMapping("role")
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private RoleServiceImpl roleService;


    @RequestMapping("findRoleList")
    public ResponseResult<List<Role>> findRoleList(){

        return Response.makeOKRsp(roleService.findAllRole());
    }


    @RequestMapping("findRoleByCondition")
    public ResponseResult<List<Role>> findRoleByCondition(Role role){

        return Response.makeOKRsp(roleService.findRoleByCondition(role));
    }


}