package com.zhang.controller;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Author: Distance
 * Date: 2021/05/16/14:02
 */
@RestController
public class Unauthorized {

    @RequestMapping("/unauthorized")
    public JSONObject unauthorized(){

        JSONObject object = new JSONObject();
        object.put("code",401);
        object.put("msg","没有权限");
        return object;
    }

}