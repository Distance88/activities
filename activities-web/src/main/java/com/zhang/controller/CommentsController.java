package com.zhang.controller;

import com.zhang.pojo.Comments;
import com.zhang.restful.Response;
import com.zhang.restful.ResponseResult;
import com.zhang.service.CommentsService;
import com.zhang.service.impl.CommentsServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Comments)表控制层
 *
 * @author Distance
 * @since 2021-06-06 16:13:32
 */
@RestController
@RequestMapping("comments")
public class CommentsController {
    /**
     * 服务对象
     */
    @Resource
    private CommentsServiceImpl commentsService;


    @RequestMapping("addComments")
    public ResponseResult addComments(Comments comments){

        commentsService.insertComments(comments);

        return Response.makeOKRsp();
    }

}