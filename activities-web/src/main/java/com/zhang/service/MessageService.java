package com.zhang.service;

import com.zhang.pojo.Message;

import java.util.List;

/**
 * (Message)表服务接口
 *
 * @author Distance
 * @since 2020-10-20 10:39:17
 */
public interface MessageService {

    List<Message> findMessageList();

    void addMessage(Message message);

}