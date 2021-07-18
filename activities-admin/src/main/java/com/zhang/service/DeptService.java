package com.zhang.service;

import com.zhang.pojo.Dept;

import java.util.List;

/**
 * 部门表(Dept)表服务接口
 *
 * @author Distance
 * @since 2021-05-21 13:11:31
 */
public interface DeptService {

    List<Dept> findDeptList();

    List<Dept> findDeptByCondition(Dept dept);
}