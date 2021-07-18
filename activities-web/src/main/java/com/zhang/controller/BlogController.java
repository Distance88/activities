package com.zhang.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.pojo.Blog;
import com.zhang.restful.Response;
import com.zhang.restful.ResponseResult;
import com.zhang.service.impl.BlogServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Blog)表控制层
 *
 * @author Distance
 * @since 2020-11-06 10:21:20
 */
@RestController
@RequestMapping("blog")
public class BlogController {
    /**
     * 服务对象
     */
    @Resource
    private BlogServiceImpl blogService;

    @RequestMapping("getBlogList")
    public ResponseResult<Page<Blog>> getBlogList(Integer current){

        return Response.makeOKRsp(blogService.findBlogList(current));
    }

    @RequestMapping("getBlogById")
    public ResponseResult<Blog> getBlogById(Integer id){



        return Response.makeOKRsp(blogService.findBlogById(id));
    }

    @RequestMapping("getBlogListByType")
    public ResponseResult<Page<Blog>> getBlogListByType(Integer current,String typeName){

        return Response.makeOKRsp(blogService.findBlogListByTypeName(current,typeName));
    }

    @RequestMapping("getBlogListByLabel")
    public ResponseResult<Page<Blog>> getBlogListByLabel(Integer current,String labelName){

        return Response.makeOKRsp(blogService.findBlogListByLabelName(current,labelName));
    }

    @RequestMapping("getHotBlog")
    public ResponseResult<List<Blog>> getHotBlog(){
        return Response.makeOKRsp(blogService.findHotBlog());
    }

    @RequestMapping("getBlogListByUserId")
    public ResponseResult<Page<Blog>> getBlogListByUserId(Integer current,Integer userId){

        return Response.makeOKRsp(blogService.findBlogListByUserId(current,userId));
    }
}