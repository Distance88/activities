package com.zhang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.pojo.Blog;

/**
 * (Blog)表服务接口
 *
 * @author Distance
 * @since 2020-11-06 10:21:20
 */
public interface BlogService {


    Page<Blog> findBlogListById(Integer id,Integer current);



    Page<Blog> findBlogByCondition(Blog blog,Integer current);

    void deleteBlogById(Integer id);

    void insertBlog(Blog blog);

    void updateBlogById(Blog blog);

    Blog findBlogById(Integer id);

}