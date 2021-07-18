package com.zhang.service;

import com.zhang.pojo.Menu;

import java.util.List;

/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author Distance
 * @since 2021-03-22 15:46:06
 */
public interface MenuService {

    List<Menu> findAllMenu();

}