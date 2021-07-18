package com.zhang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.pojo.Blog;

import java.util.List;

/**
 * (Blog)表服务接口
 *
 * @author Distance
 * @since 2020-11-06 10:21:20
 */
public interface BlogService {


    Page<Blog> findBlogList(Integer current);

    Blog findBlogById(Integer id);

    void updateViewById(Integer id, Integer view);

    Page<Blog> findBlogListByTypeName(Integer current,String typeName);

    List<Blog> findHotBlog();

    Page<Blog> findBlogListByLabelName(Integer current,String labelName);

    Page<Blog> findBlogListByUserId(Integer current,Integer userId);
}