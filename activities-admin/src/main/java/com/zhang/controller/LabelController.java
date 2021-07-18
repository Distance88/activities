package com.zhang.controller;

import com.zhang.pojo.Label;
import com.zhang.restful.Response;
import com.zhang.restful.ResponseResult;
import com.zhang.service.impl.LabelServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Label)表控制层
 *
 * @author Distance
 * @since 2021-05-11 20:02:43
 */
@RestController
@RequestMapping("label")
public class LabelController {
    /**
     * 服务对象
     */
    @Resource
    private LabelServiceImpl labelService;

    @RequestMapping("getLabelList")
    public ResponseResult<List<Label>> getLabelList(){

        return Response.makeOKRsp(labelService.findLabelList());
    }
}