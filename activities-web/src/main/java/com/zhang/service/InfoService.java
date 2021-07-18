package com.zhang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.pojo.Info;

/**
 * (Info)表服务接口
 *
 * @author Distance
 * @since 2020-10-15 20:32:06
 */
public interface InfoService {

    Page<Info> findInfoList(Integer current);

    Info findInfoById(Integer id);
}

