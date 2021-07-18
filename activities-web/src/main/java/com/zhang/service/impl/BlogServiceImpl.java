package com.zhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.mapper.BlogMapper;
import com.zhang.mapper.CommentsMapper;
import com.zhang.mapper.UserMapper;
import com.zhang.pojo.Blog;
import com.zhang.pojo.Comments;
import com.zhang.service.BlogService;
import com.zhang.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * (Blog)表服务接口
 *
 * @author Distance
 * @since 2020-11-06 10:21:20
 */
@Service
public class BlogServiceImpl implements BlogService {


    @Resource
    private BlogMapper blogMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserMapper userMapper;

    @Resource
    private CommentsMapper commentsMapper;


    @Override
    public Page<Blog> findBlogList(Integer current) {

        String key = "blog_current_"+current;

        Page<Blog> page = new Page<>(current,5);

        boolean hasKey = redisUtil.hasKey(key);

        if(hasKey){


            return (Page<Blog>) redisUtil.get(key);
        }

        List<Blog> blogList = blogMapper.selectPage(page, null).getRecords();

        blogList.forEach(blog -> {
            String keyCount = "blog_view_count_"+blog.getId();

            if(redisUtil.hasKey(keyCount)){
                blog.setViews((Integer) redisUtil.get(keyCount));
                updateViewById(blog.getId(),(Integer) redisUtil.get(keyCount));
            }else{
                redisUtil.set(keyCount,0);
            }

            blog.setPhoto(userMapper.findAvatarById(blog.getUserId()));

        });

        page.setRecords(blogList);

        redisUtil.set(key,page,3);

        return page;
    }




    @Override
    public Blog findBlogById(Integer id) {
        String key = "blog_"+id;
        boolean hasKey = redisUtil.hasKey(key);
        String keyCount = "blog_view_count_"+id;
        redisUtil.incrBy(keyCount,1);
        if(hasKey){
            Blog blog = (Blog) redisUtil.get(key);
            blog.setViews((Integer) redisUtil.get(keyCount));
            return blog;
        }
        Blog blog = blogMapper.selectById(id);
        blog.setPhoto(userMapper.findAvatarById(blog.getUserId()));
        blog.setViews((Integer) redisUtil.get(keyCount));

        QueryWrapper<Comments> wrapper = new QueryWrapper<>();

        wrapper.eq("blog_id",id);

        List<Comments> commentsAll = commentsMapper.selectList(wrapper);

        List<Comments> commentsList = new LinkedList<>();

        for(int i=0;i<commentsAll.size();i++){

            if(commentsAll.get(i).getPid() == 0){
                commentsList.add(commentsAll.get(i));
            }
        }

        for(Comments comments:commentsList){

            List<Comments> children = getChildren(comments.getId(), commentsAll);
            comments.setChildrenList(children);
        }

        blog.setCommentsList(commentsList);

        redisUtil.set(key,blog);
        return blog;
    }

    public List<Comments> getChildren(Integer id, List<Comments> commentsAll){

        List<Comments> childList= new ArrayList<>();

        for(Comments comments:commentsAll){

            if(id == comments.getPid()){
                childList.add(comments);
            }
        }

        for(Comments comments:childList){

            List<Comments> children = getChildren(comments.getId(), commentsAll);

            comments.setChildrenList(children);
        }

        if(childList.size() == 0){
            return null;
        }

        return childList;
    }

    @Override
    public void updateViewById(Integer id,Integer view) {


        blogMapper.updateViews(id,view);
    }

    @Override
    public Page<Blog> findBlogListByTypeName(Integer current, String typeName) {

        String key = "blog_list_type_"+typeName+"_current_"+current;

        boolean hasKey = redisUtil.hasKey(key);

        if(hasKey){

            return (Page<Blog>) redisUtil.get(key);
        }

        Page<Blog> page = new Page<>(current,5);

        QueryWrapper<Blog> wrapper = new QueryWrapper<>();

        wrapper.eq("type",typeName);

        IPage<Blog> blogIPage = blogMapper.selectPage(page, wrapper);

        redisUtil.set(key,blogIPage);

        return (Page<Blog>) blogIPage;
    }

    @Override
    public List<Blog> findHotBlog() {

        String key = "hotBlog";

        boolean hasKey = redisUtil.hasKey(key);

        if (hasKey){
            return (List<Blog>) redisUtil.get(key);
        }

        List<Blog> blogList = blogMapper.selectHotBlog();

        redisUtil.set(key,blogList,48);

        return blogList;
    }

    @Override
    public Page<Blog> findBlogListByLabelName(Integer current, String labelName) {

        String key = "blog_list_label_"+labelName+"_current_"+current;

        boolean hasKey = redisUtil.hasKey(key);

        if(hasKey){

            return (Page<Blog>) redisUtil.get(key);

        }

        QueryWrapper<Blog> wrapper = new QueryWrapper<>();

        wrapper.like("label",labelName);

        Page<Blog> page = new Page<>(current,5);

        IPage<Blog> blogIPage = blogMapper.selectPage(page, wrapper);

        redisUtil.set(key,blogIPage);

        return (Page<Blog>) blogIPage;


    }

    @Override
    public Page<Blog> findBlogListByUserId(Integer current, Integer userId) {


        String key = "blog_list_user_"+userId+"_current_"+current;

        boolean hasKey = redisUtil.hasKey(key);

        if(hasKey){

            return (Page<Blog>) redisUtil.get(key);

        }

        QueryWrapper<Blog> wrapper = new QueryWrapper<>();

        wrapper.eq("user_id",userId);

        Page<Blog> page = new Page<>(current,5);

        List<Blog> blogList = blogMapper.selectPage(page, wrapper).getRecords();

        blogList.forEach(blog ->{

            blog.setPhoto(userMapper.findAvatarById(blog.getUserId()));
        });

        page.setRecords(blogList);
        redisUtil.set(key,page);

        return page;


    }


}