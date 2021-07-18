package com.zhang.service.impl;

import com.zhang.mapper.TypeMapper;
import com.zhang.pojo.Type;
import com.zhang.service.TypeService;
import com.zhang.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Type)表服务接口
 *
 * @author Distance
 * @since 2020-11-07 09:52:29
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Resource
    private TypeMapper typeMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<Type> findTypeList() {

        String key = "typeList";

        boolean hasKey = redisUtil.hasKey(key);

        if(hasKey){
            return redisUtil.lGet(key,0,-1);
        }

        List<Type> typeList = typeMapper.selectList(null);

        redisUtil.lSet(key,typeList);

        return typeList;
    }
}