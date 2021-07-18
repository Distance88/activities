package com.zhang.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.pojo.Problem;
import com.zhang.restful.Response;
import com.zhang.restful.ResponseResult;
import com.zhang.service.impl.ProblemServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (Problem)表控制层
 *
 * @author Distance
 * @since 2020-10-15 20:40:06
 */
@RestController
@RequestMapping("problem")
public class ProblemController {
    /**
     * 服务对象
     */
    @Resource
    private ProblemServiceImpl problemService;

    @RequestMapping("getProblemList")
    public ResponseResult<Page<Problem>> getProblemList(Integer current){

        return Response.makeOKRsp(problemService.findProblemList(current));
    }
}