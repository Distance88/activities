package com.zhang.service;

import com.zhang.pojo.Comments;

import java.util.List;

/**
 * (Comments)表服务接口
 *
 * @author Distance
 * @since 2021-06-06 16:13:32
 */
public interface CommentsService {


    void insertComments(Comments comments);

    List<Comments> findCommentsListByBlogId(Integer blogId);
}