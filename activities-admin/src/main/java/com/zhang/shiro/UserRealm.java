package com.zhang.shiro;

import com.zhang.pojo.User;
import com.zhang.service.UserService;
import com.zhang.utils.JwtUtils;
import com.zhang.utils.RedisUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Author: Distance
 * Date: 2021/03/17/8:22
 */
public class UserRealm extends AuthenticatingRealm {

    @Resource
    private UserService userService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private JwtUtils jwtUtils;


    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {


        String username = (String) authenticationToken.getPrincipal();

        User user = userService.findUserByUserName(username);

        if(user == null){
            throw new UnknownAccountException();
        }


        return new SimpleAuthenticationInfo(user,user.getPassword(),getName());


    }
}
