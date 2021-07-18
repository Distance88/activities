package com.zhang.service;

import com.zhang.pojo.Label;

import java.util.List;

/**
 * (Label)表服务接口
 *
 * @author Distance
 * @since 2021-05-11 20:02:43
 */
public interface LabelService {

    List<Label> findLabelList();

}