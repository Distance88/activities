package com.zhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.pojo.User;
import org.apache.ibatis.annotations.Select;


/**
 * (User)表数据库访问层
 *
 * @author Distance
 * @since 2020-10-26 16:21:40
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select avatar from user where id = #{id}")
    String findAvatarById(Integer id);

}