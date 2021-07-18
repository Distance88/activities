package com.zhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.mapper.BlogMapper;
import com.zhang.mapper.LabelMapper;
import com.zhang.mapper.TypeMapper;
import com.zhang.pojo.Blog;
import com.zhang.pojo.Label;
import com.zhang.pojo.Type;
import com.zhang.service.BlogService;
import com.zhang.utils.CommonmarkUtil;
import com.zhang.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * (Blog)表服务接口
 *
 * @author Distance
 * @since 2020-11-06 10:21:20
 */
@Service
@SuppressWarnings("unchecked")
public class BlogServiceImpl implements BlogService {


    @Resource
    private BlogMapper blogMapper;

    @Resource
    private TypeMapper typeMapper;

    @Resource
    private LabelMapper labelMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private CommonmarkUtil commonmarkUtil;


    @Override
    public Page<Blog> findBlogListById(Integer user_id,Integer current) {

        String key = "blog_user_id_"+user_id+"_current_"+current;

        Page<Blog> page = new Page<>(current,5);

        boolean hasKey = redisUtil.hasKey(key);

        if(hasKey){


            return (Page<Blog>) redisUtil.get(key);
        }




        if(user_id == 1){
            IPage<Blog> blogIPage = blogMapper.selectPage(page, null);

            redisUtil.set(key,blogIPage,3);

            return (Page<Blog>) blogIPage;
        }
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();

        wrapper.eq("user_id",user_id);

        IPage<Blog> blogIPage = blogMapper.selectPage(page, wrapper);


        redisUtil.set(key,blogIPage,3);

        return (Page<Blog>) blogIPage;
    }

    @Override
    public Page<Blog> findBlogByCondition(Blog blog,Integer current) {

        QueryWrapper<Blog> wrapper = new QueryWrapper<>();

        wrapper.eq("type",blog.getType())
               .or()
               .eq("style",blog.getStyle())
               .or()
               .between("create_time",blog.getStartTime(),blog.getEndTime());


        Page<Blog> page = new Page<>(current,5);



        return (Page<Blog>) blogMapper.selectPage(page,wrapper);
    }

    @Override
    public void deleteBlogById(Integer id) {

        redisUtil.deleteByPrex("blog_current_*");
        redisUtil.deleteByPrex("blog_user_id_"+id+"_current_*");

        blogMapper.deleteById(id);
    }

    @Override
    public void insertBlog(Blog blog) {

        List<String> labelNameList = Arrays.asList(blog.getLabel().split(","));

        String labelName = "";


        for(String name : labelNameList){

            Label label = new Label();

            Label findLabel = labelMapper.selectOne(new QueryWrapper<Label>().eq("name", name));

            if(findLabel == null){
                label.setName(name);
                labelMapper.insert(label);
                redisUtil.del("labelList");
            }else{
                UpdateWrapper<Label> wrapper = new UpdateWrapper<>();

                wrapper.set("count",findLabel.getCount() + 1).eq("id",findLabel.getId());

                labelMapper.update(findLabel,wrapper);
            }

            labelName+=name+",";

        }



        Type findType = typeMapper.selectOne(new QueryWrapper<Type>().eq("name", blog.getType()));

        if(findType  == null){
            Type type = new Type();
            type.setName(blog.getType());
            typeMapper.insert(type);
            redisUtil.del("typeList");
        }else{
            UpdateWrapper<Type> wrapper = new UpdateWrapper<>();

            wrapper.set("count",findType.getCount() + 1).eq("id",findType.getId());

            typeMapper.update(findType,wrapper);
        }

        blog.setContent(commonmarkUtil.transferMarkDownToHtml(blog.getContent()));

        blog.setLabel(labelName.substring(0, labelName.length() - 1));

        blogMapper.insert(blog);

        redisUtil.deleteByPrex("blog_current_*");

    }

    @Override
    public void updateBlogById(Blog blog) {

        redisUtil.del("blog_"+blog.getId());
        redisUtil.deleteByPrex("blog_current_*");
        redisUtil.deleteByPrex("blog_user_id_"+blog.getUserId()+"_current_*");

        UpdateWrapper<Blog> wrapper = new UpdateWrapper<>();

        wrapper.set("title",blog.getTitle())
               .set("digest",blog.getDigest())
               .set("content",commonmarkUtil.transferMarkDownToHtml(blog.getContent()))
               .set("charts",blog.getCharts())
               .set("type",blog.getType())
               .set("label",blog.getLabel())
               .set("style",blog.getStyle())
               .set("md_content",blog.getMdContent())
               .eq("id",blog.getId());


        blogMapper.update(blog,wrapper);

    }

    @Override
    public Blog findBlogById(Integer id) {
        String key = "blog_"+id;
        boolean hasKey = redisUtil.hasKey(key);
        if(hasKey){
            return (Blog) redisUtil.get(key);
        }
        Blog blog = blogMapper.selectById(id);
        redisUtil.set(key,blog);
        return blog;
    }


}