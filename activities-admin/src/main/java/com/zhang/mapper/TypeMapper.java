package com.zhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.pojo.Type;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (Type)表数据库访问层
 *
 * @author Distance
 * @since 2020-11-07 09:52:29
 */
public interface TypeMapper extends BaseMapper<Type> {


    @Select("select name from type")
    List<String> selectTypeNameList();

}