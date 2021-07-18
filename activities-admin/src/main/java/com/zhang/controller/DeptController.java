package com.zhang.controller;

import com.zhang.pojo.Dept;
import com.zhang.restful.Response;
import com.zhang.restful.ResponseResult;
import com.zhang.service.DeptService;
import com.zhang.service.impl.DeptServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门表(Dept)表控制层
 *
 * @author Distance
 * @since 2021-05-21 13:11:32
 */
@RestController
@RequestMapping("dept")
public class DeptController {
    /**
     * 服务对象
     */
    @Resource
    private DeptServiceImpl deptService;

    @RequestMapping("getDeptList")
    public ResponseResult<List<Dept>> getDeptList(){

        List<Dept> deptList = deptService.findDeptList();


        return Response.makeOKRsp(deptList);
    }


    @RequestMapping("findDeptByCondition")
    public ResponseResult<List<Dept>> findDeptByCondition(Dept dept){

        return Response.makeOKRsp(deptService.findDeptByCondition(dept));
    }


}