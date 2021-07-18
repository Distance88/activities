package com.zhang.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.pojo.Info;
import com.zhang.restful.Response;
import com.zhang.restful.ResponseResult;
import com.zhang.service.impl.InfoServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (Info)表控制层
 *
 * @author Distance
 * @since 2020-10-15 20:32:07
 */
@RestController
@RequestMapping("info")
public class InfoController {
    /**
     * 服务对象
     */
    @Resource
    private InfoServiceImpl infoService;

    @RequestMapping("getInfoList")
    public ResponseResult<Page<Info>> getInfoList(Integer current){

        return Response.makeOKRsp(infoService.findInfoList(current));
    }

    @RequestMapping("getInfoById")
    public ResponseResult<Info> getInfoById(Integer id){


        return Response.makeOKRsp(infoService.findInfoById(id));
    }
}