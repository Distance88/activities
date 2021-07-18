package com.zhang.shiro;

import com.zhang.utils.JwtUtils;
import com.zhang.utils.RedisUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * Author: Distance
 * Date: 2021/04/29/21:13
 */
public class JwtRealm extends AuthorizingRealm {



    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {


        String access_token = (String) authenticationToken.getPrincipal();
        String refresh_token = (String) authenticationToken.getCredentials();

        String username = JwtUtils.getUsername(access_token);

        boolean hasKey = redisUtil.hasKey(username);


        if(hasKey){

            String redis_token = (String) redisUtil.get(username);



            if(!access_token.equals(redis_token)){
                if(!JwtUtils.isTokenExpired(redis_token)){
                    throw new AuthenticationException("您已在其他地方登陆！");
                }else{
                    redisUtil.set(username,access_token);
                }
            }

        }else {
            redisUtil.set(username,access_token);
        }


        if(!JwtUtils.verify(access_token,username,"123456")){


            if(!JwtUtils.verify(refresh_token,username,"123456"))
                throw new AuthenticationException();

            redisUtil.set(username,access_token);
        }


        return new SimpleAuthenticationInfo(access_token,refresh_token,getName());
    }
}
