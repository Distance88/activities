package com.zhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.pojo.Blog;
import com.zhang.pojo.Recommend;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (Blog)表数据库访问层
 *
 * @author Distance
 * @since 2020-11-06 10:21:20
 */
public interface BlogMapper extends BaseMapper<Blog> {


    @Update("update blog set views = #{view} where id = #{id}")
    void updateViews(Integer id, Integer view);

    @Select("select * from blog order by views limit 0,5")
    List<Blog> selectHotBlog();

}