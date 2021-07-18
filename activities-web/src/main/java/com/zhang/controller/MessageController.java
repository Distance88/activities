package com.zhang.controller;

import com.zhang.pojo.Message;
import com.zhang.restful.Response;
import com.zhang.restful.ResponseResult;
import com.zhang.service.impl.MessageServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Message)表控制层
 *
 * @author Distance
 * @since 2020-10-20 10:39:17
 */
@RestController
@RequestMapping("message")
public class MessageController {
    /**
     * 服务对象
     */
    @Resource
    private MessageServiceImpl messageService;

    @RequestMapping("getMessageList")
    public ResponseResult<List<Message>> getMessageList(){

        return Response.makeOKRsp(messageService.findMessageList());
    }

    @RequestMapping("addMessage")
    public ResponseResult addMessage(Message message){
        messageService.addMessage(message);
        return Response.makeOKRsp("留言成功！");
    }

}