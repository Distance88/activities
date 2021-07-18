package com.zhang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.pojo.Menu;
import org.springframework.stereotype.Repository;

/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author Distance
 * @since 2021-03-22 15:46:06
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

}