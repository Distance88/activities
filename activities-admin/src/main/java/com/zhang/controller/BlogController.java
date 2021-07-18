package com.zhang.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.pojo.Blog;
import com.zhang.restful.Response;
import com.zhang.restful.ResponseResult;
import com.zhang.service.impl.BlogServiceImpl;
import com.zhang.utils.OssUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

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

    private static String urlPrefix = "https://ngyst.oss-cn-hangzhou.aliyuncs.com/";

    @Resource
    private BlogServiceImpl blogService;


    @RequestMapping("getBlogList")
    public ResponseResult<Page<Blog>> getBlogList(@Param("user_id")Integer user_id,@Param("current")Integer current){

        return Response.makeOKRsp(blogService.findBlogListById(user_id,current));
    }


    @RequestMapping("getBlogByCondition")
    public ResponseResult<Page<Blog>> getBlogListByCondition(Blog blog,Integer current){


        return Response.makeOKRsp(blogService.findBlogByCondition(blog,current));
    }

    @RequestMapping("delBlogById")
    public ResponseResult<Page<Blog>> delBlogById(Integer id,Integer current){

        blogService.deleteBlogById(id);

        return Response.makeOKRsp();
    }

    @RequestMapping("addBlog")
    public ResponseResult addBlog(Blog blog){

        blogService.insertBlog(blog);

        return Response.makeOKRsp("添加成功");
    }

    @RequestMapping("uploadImage")
    public ResponseResult<String> uploadImage(MultipartFile multipartFile){


        OSS ossClient = OssUtil.getOssClient();

        Calendar calendar = Calendar.getInstance();


        String imagePath = calendar.get(Calendar.YEAR) +
                           "/" + StringUtils.leftPad(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, '0')+
                           "/" + StringUtils.leftPad(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2, '0')+
                           "/" + UUID.randomUUID().toString() + "." +  multipartFile.getOriginalFilename().split("\\.")[1];

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest("ngyst",imagePath,new ByteArrayInputStream(multipartFile.getBytes()));
            ossClient.putObject(putObjectRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            ossClient.shutdown();
        }

        return Response.makeOKRsp(urlPrefix+imagePath);
    }

    @RequestMapping("deleteImage")
    public ResponseResult deleteImage(String fileName){


        OSS ossClient = OssUtil.getOssClient();

        ossClient.deleteObject("ngyst",fileName.substring(43));


        ossClient.shutdown();

        return Response.makeOKRsp();
    }

    @RequestMapping("getBlogById")
    public ResponseResult<Blog> getBlogById(Integer id){


        return Response.makeOKRsp(blogService.findBlogById(id));
    }

    @RequestMapping("updateBlogById")
    public ResponseResult updateBlogById(Blog blog){


        blogService.updateBlogById(blog);

        return Response.makeOKRsp();
    }



}