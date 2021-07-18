package com.zhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.pojo.Post;
import org.springframework.stereotype.Repository;

/**
 * 岗位信息表(Post)表数据库访问层
 *
 * @author Distance
 * @since 2021-03-23 09:44:56
 */
@Repository
public interface PostMapper extends BaseMapper<Post> {

}