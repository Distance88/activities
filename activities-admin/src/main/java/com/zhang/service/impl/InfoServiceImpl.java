package com.zhang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.mapper.InfoMapper;
import com.zhang.pojo.Info;
import com.zhang.service.InfoService;
import com.zhang.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Info)表服务接口
 *
 * @author Distance
 * @since 2020-10-15 20:32:07
 */
@Service
@SuppressWarnings("unchecked")
public class InfoServiceImpl implements InfoService {


    @Resource
    private InfoMapper infoMapper;

    @Resource
    private RedisUtil redisUtil;


    @Override
    public Page<Info> findInfoList(Integer current) {

        String key = "info_current_"+current;


        boolean hasKey = redisUtil.hasKey(key);

        if(hasKey){
            return (Page<Info>) redisUtil.get(key);
        }

        Page<Info> page = new Page<>(current,5);

        IPage<Info> infoIPage = infoMapper.selectPage(page, null);

        redisUtil.set(key,infoIPage);

        return (Page<Info>) infoIPage;
    }



    @Override
    public Page<Info> findInfoByCondition(Info info, Integer current) {

        Page<Info> page = new Page<>(current,5);

        QueryWrapper<Info> wrapper = new QueryWrapper<>();

        wrapper.eq("author",info.getAuthor())
               .or()
               .eq("style",info.getStyle())
               .or()
               .between("create_time",info.getStartTime(),info.getEndTime());




        return (Page<Info>) infoMapper.selectPage(page,wrapper);
    }

    @Override
    public void deleteInfoById(Integer id) {

        redisUtil.deleteByPrex("info_current_*");

        infoMapper.deleteById(id);

    }

    @Override
    public void insertInfo(Info info) {

        redisUtil.deleteByPrex("info_current_*");

        infoMapper.insert(info);

    }
}