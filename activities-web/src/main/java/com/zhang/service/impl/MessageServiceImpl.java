package com.zhang.service.impl;

import com.zhang.filter.WordFilter;
import com.zhang.mapper.MessageMapper;
import com.zhang.pojo.Message;
import com.zhang.service.MessageService;
import com.zhang.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * (Message)表服务接口
 *
 * @author Distance
 * @since 2020-10-20 10:39:17
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private RedisUtil redisUtil;

    private static String key = "messageList";

    @Override
    public List<Message> findMessageList() {


        boolean hasKey = redisUtil.hasKey(key);

        if(hasKey){
            return redisUtil.lGet(key,0,-1);
        }

        List<Message> messages = messageMapper.selectList(null);

        List<Message> messageList = new LinkedList<>();

        for(int i=0;i<messages.size();i++){
            if(messages.get(i).getPid() == 0){
                messageList.add(messages.get(i));
            }
        }
        
        for(Message message:messageList){
            List<Message> childrenList = getChildren(message.getId(), messages);
            message.setChildrenList(childrenList);
        }

        redisUtil.lSet(key,messageList);
        return messageList;
    }

    @Override
    public void addMessage(Message message) {

        message.setContent(WordFilter.doFilter(message.getContent()));

        redisUtil.del(key);
        messageMapper.insert(message);
    }

    public List<Message> getChildren(Integer id, List<Message> messages){

        List<Message> childList= new ArrayList<>();

        for(Message message:messages){

            if(id == message.getPid()){
                childList.add(message);
            }
        }

        for(Message message:childList){

            List<Message> children = getChildren(message.getId(), messages);

            message.setChildrenList(children);
        }

        if(childList.size() == 0){
            return null;
        }

        return childList;
    }



}