package com.zhang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.pojo.Problem;

/**
 * (Problem)表服务接口
 *
 * @author Distance
 * @since 2020-10-15 20:40:06
 */
public interface ProblemService {

    Page<Problem> findProblemList(Integer current);
}