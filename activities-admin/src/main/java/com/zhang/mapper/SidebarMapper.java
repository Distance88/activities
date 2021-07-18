package com.zhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.pojo.Sidebar;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Sidebar)表数据库访问层
 *
 * @author Distance
 * @since 2021-03-18 09:20:52
 */
@Repository
public interface SidebarMapper extends BaseMapper<Sidebar> {

    @Select("select id,path,name,title,icon,parentid from sidebar")
    List<Sidebar> selAllSideBar();
}