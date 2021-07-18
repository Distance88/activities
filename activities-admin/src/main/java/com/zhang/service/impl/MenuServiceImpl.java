package com.zhang.service.impl;

import com.zhang.mapper.MenuMapper;
import com.zhang.pojo.Menu;
import com.zhang.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author Distance
 * @since 2021-03-22 15:46:07
 */
@Service
@SuppressWarnings("unchecked")
public class MenuServiceImpl implements MenuService {


    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findAllMenu() {

        List<Menu> menus = menuMapper.selectList(null);

        List<Menu> menuList = new ArrayList<Menu>();

        for(int i=0;i<menus.size();i++){

            if(menus.get(i).getParentId() == 0){
                menuList.add(menus.get(i));
            }
        }

        for(Menu menu:menuList){

            menu.setChildren(getChildList(menu.getId(), menus));
        }


        return menuList;
    }

    private List<Menu> getChildList(Integer id, List<Menu> menus) {

        List<Menu> childList = new ArrayList<Menu>();

        for(Menu menu:menus){
            if(menu.getParentId() == id){
                childList.add(menu);
            }
        }

        for(Menu menu:childList){

            menu.setChildren(getChildList(menu.getId(),menus));
        }

        if(childList.size() == 0){
            return null;
        }

        return childList;
    }
}