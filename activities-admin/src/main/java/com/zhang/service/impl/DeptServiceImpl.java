package com.zhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.mapper.DeptMapper;
import com.zhang.pojo.Dept;
import com.zhang.service.DeptService;
import com.zhang.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门表(Dept)表服务接口
 *
 * @author Distance
 * @since 2021-05-21 13:11:31
 */
@Service
@SuppressWarnings("unchecked")
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<Dept> findDeptList() {

        String key = "deptList";

        boolean hasKey = redisUtil.hasKey(key);

        if(hasKey){
            return redisUtil.lGet(key,0,-1);
        }

        List<Dept> depts = deptMapper.selectList(null);

        List<Dept> deptList = new ArrayList<>();

        for(int i=0;i<depts.size();i++){

            if(depts.get(i).getParentId() == 0){
                deptList.add(depts.get(i));
            }
        }

        for(Dept dept:deptList){

            List<Dept> children = getChildren(dept.getId(), depts);

            dept.setChildren(children);
        }

        redisUtil.lSet(key,deptList);

        return deptList;
    }

    @Override
    public List<Dept> findDeptByCondition(Dept dept) {

        QueryWrapper<Dept> wrapper = new QueryWrapper<Dept>();

        wrapper.like("name",dept.getName())
                .or()
                .between("create_time",dept.getStartTime(),dept.getEndTime());


        List<Dept> deptList = deptMapper.selectList(wrapper);


        return deptList;
    }

    public List<Dept> getChildren(Integer id,List<Dept> depts){

        List<Dept> childList= new ArrayList<>();

        for(Dept dept:depts){

            if(id == dept.getParentId()){
                childList.add(dept);
            }
        }

        for(Dept dept:childList){

            List<Dept> children = getChildren(dept.getId(), depts);

            dept.setChildren(children);
        }

        if(childList.size() == 0){
            return null;
        }

        return childList;
    }
}