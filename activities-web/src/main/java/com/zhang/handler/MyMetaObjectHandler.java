package com.zhang.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zhang.utils.TimeUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Author: Distance
 * Date: 2021/03/21/10:36
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private TimeUtils timeUtils;

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime",timeUtils.getCreateTime(),metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",timeUtils.getCreateTime(),metaObject);
    }
}
