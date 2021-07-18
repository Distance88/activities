package com.zhang.controller;

import com.zhang.pojo.Type;
import com.zhang.restful.Response;
import com.zhang.restful.ResponseResult;
import com.zhang.service.impl.TypeServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Type)表控制层
 *
 * @author Distance
 * @since 2020-11-07 09:52:29
 */
@RestController
@RequestMapping("type")
public class TypeController {
    /**
     * 服务对象
     */
    @Resource
    private TypeServiceImpl typeService;

    @RequestMapping("getTypeList")
    public ResponseResult<List<Type>> getTypeList(){

        return Response.makeOKRsp(typeService.findTypeList());
    }


}