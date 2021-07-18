package com.zhang.service.impl;

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
public class InfoServiceImpl implements InfoService {


    @Resource
    private InfoMapper infoMapper;

    @Resource
    private RedisUtil redisUtil;


    @Override
    public Page<Info> findInfoList(Integer current) {

        Page<Info> page = new Page<>(current,5);


        return (Page<Info>) infoMapper.selectPage(page,null);
    }

    @Override
    public Info findInfoById(Integer id) {

        String key = "info_"+id;

        boolean hasKey = redisUtil.hasKey(key);

        if(hasKey){
            return (Info) redisUtil.get(key);
        }

        Info info = infoMapper.selectById(id);

        redisUtil.set(key,info);


        return info;
    }
}