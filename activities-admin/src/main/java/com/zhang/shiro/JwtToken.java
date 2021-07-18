package com.zhang.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created with IntelliJ IDEA.
 * Author: Distance
 * Date: 2021/03/02/9:07
 */
public class JwtToken implements AuthenticationToken {

    private String access_token;

    private String refresh_token;

    public JwtToken(String access_token,String refresh_token) {

        this.access_token = access_token;

        this.refresh_token = refresh_token;
    }

    @Override
    public String getPrincipal() {
        return access_token;
    }

    @Override
    public Object getCredentials() {
        return refresh_token;
    }


}
