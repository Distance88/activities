package com.zhang.service.impl;

import com.zhang.filter.WordFilter;
import com.zhang.mapper.CommentsMapper;
import com.zhang.pojo.Comments;
import com.zhang.service.CommentsService;
import com.zhang.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Comments)表服务接口
 *
 * @author Distance
 * @since 2021-06-06 16:13:32
 */
@Service
public class CommentsServiceImpl implements CommentsService {

    @Resource
    private CommentsMapper commentsMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void insertComments(Comments comments) {

        comments.setContent(WordFilter.doFilter(comments.getContent()));

        redisUtil.del("blog_"+comments.getBlogId());
        commentsMapper.insert(comments);
    }

    @Override
    public List<Comments> findCommentsListByBlogId(Integer blogId) {
        return null;
    }
}