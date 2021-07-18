package com.zhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.mapper.PostMapper;
import com.zhang.pojo.Post;
import com.zhang.service.PostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 岗位信息表(Post)表服务接口
 *
 * @author Distance
 * @since 2021-03-23 09:44:56
 */
@Service
@SuppressWarnings("unchecked")
public class PostServiceImpl implements PostService {


    @Resource
    private PostMapper postMapper;

    @Override
    public List<Post> findAllPost() {
        return postMapper.selectList(null);
    }

    @Override
    public List<Post> findPostByCondition(Post post) {

        QueryWrapper<Post> wrapper = new QueryWrapper<Post>();

        wrapper.eq("code",post.getCode())
                .or()
                .eq("name",post.getName())
                .or()
                .eq("status",post.getStatus());


        return postMapper.selectList(wrapper);
    }
}