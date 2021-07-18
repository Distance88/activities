package com.zhang.service.impl;

import com.zhang.mapper.LabelMapper;
import com.zhang.pojo.Label;
import com.zhang.service.LabelService;
import com.zhang.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Label)表服务接口
 *
 * @author Distance
 * @since 2021-05-11 20:02:43
 */
@Service
@SuppressWarnings("unchecked")
public class LabelServiceImpl implements LabelService {

    @Resource
    private LabelMapper labelMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<Label> findLabelList() {


        String key = "labelList";

        boolean hasKey = redisUtil.hasKey(key);

        if(hasKey){
            return redisUtil.lGet(key,0,-1);
        }

        List<Label> labelList = labelMapper.selectList(null);

        redisUtil.lSet(key,labelList);

        return labelList;
    }
}