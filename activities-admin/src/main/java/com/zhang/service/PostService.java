package com.zhang.service;

import com.zhang.pojo.Post;

import java.util.List;

/**
 * 岗位信息表(Post)表服务接口
 *
 * @author Distance
 * @since 2021-03-23 09:44:56
 */
public interface PostService {


    List<Post> findAllPost();

    List<Post> findPostByCondition(Post post);

}