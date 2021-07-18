package com.zhang.service;

import com.zhang.pojo.Sidebar;

import java.util.List;

/**
 * (Sidebar)表服务接口
 *
 * @author Distance
 * @since 2021-03-18 09:20:52
 */
public interface SidebarService {


    List<Sidebar> findAllSideBarByRoleId(Integer roleId);

    void updateSideBarByRole(Integer roleId,Integer sideBarIds[]);

}