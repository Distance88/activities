package com.zhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhang.mapper.SidebarMapper;
import com.zhang.pojo.Sidebar;
import com.zhang.service.SidebarService;
import com.zhang.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (Sidebar)表服务接口
 *
 * @author Distance
 * @since 2021-03-18 09:20:53
 */
@Service
@SuppressWarnings("unchecked")
public class SidebarServiceImpl implements SidebarService {

    @Resource
    private SidebarMapper sidebarMapper;

    @Resource
    private RedisUtil redisUtil;


    @Override
    public List<Sidebar> findAllSideBarByRoleId(Integer roleId) {


//        String key = "sideBar";
//
//        boolean hasKey = redisUtil.hasKey(key);
//
//        if(hasKey){
//            return redisUtil.lGet(key,0,-1);
//        }


        QueryWrapper<Sidebar> wrapper = new QueryWrapper<>();

        wrapper.like("role_id",roleId);

        List<Sidebar> selectList = sidebarMapper.selectList(wrapper);



        List<Sidebar> sidebarList = new ArrayList<>();

        for(int i=0;i<selectList.size();i++){

            if(selectList.get(i).getParentId() == 0){
                sidebarList.add(selectList.get(i));
            }
        }

        for(Sidebar sidebar:selectList){

            List<Sidebar> childList = getChildList(sidebar.getId(), selectList);

            sidebar.setChildren(childList);
        }


//        redisUtil.lSet(key,sidebarList);

        return sidebarList;
    }

    @Override
    public void updateSideBarByRole(Integer roleId, Integer[] sideBarIds) {

        UpdateWrapper<Sidebar> wrapper = new UpdateWrapper<>();


    }


    public List<Sidebar> getChildList(Integer id, List<Sidebar> menuList){
        List<Sidebar> childList = new ArrayList<>();


        for(com.zhang.pojo.Sidebar Sidebar:menuList){
            if(id == Sidebar.getParentId()){
                childList.add(Sidebar);
            }
        }

        //递归
        for (com.zhang.pojo.Sidebar Sidebar:childList) {
            Sidebar.setChildren(getChildList(Sidebar.getId(),menuList));
        }
        if (childList.size() == 0){
            return null;
        }
        return childList;

    }
}