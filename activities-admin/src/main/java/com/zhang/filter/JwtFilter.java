package com.zhang.filter;

import com.zhang.shiro.JwtToken;
import com.zhang.utils.JwtUtils;
import com.zhang.utils.RedisUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * Author: Distance
 * Date: 2021/03/17/9:29
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private Logger log = LoggerFactory.getLogger(this.getClass());



    /**
     * 前置处理
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {


        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return true;
        }
        return super.preHandle(request, response);
    }

    /**
     * 后置处理
     */
    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) {

        this.fillCorsHeader(WebUtils.toHttp(request), WebUtils.toHttp(response));
    }

    /**
     * 过滤器拦截请求的入口方法
     * 返回 true 则允许访问
     * 返回false 则禁止访问，会进入 onAccessDenied()
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 原用来判断是否是登录请求，在本例中不会拦截登录请求，用来检测Header中是否包含 JWT token 字段


        if (this.isLoginRequest(request, response))
            return false;
        boolean allowed = false;
        try {
            // 检测Header里的 JWT token内容是否正确，尝试使用 token进行登录
            allowed = executeLogin(request, response);
        } catch (IllegalStateException e) { // not found any token
            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }


        return allowed || super.isPermissive(mappedValue);
    }

    /**
     * 检测Header中是否包含 JWT token 字段
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {


        return ((HttpServletRequest) request).getHeader("access_token") == null;
    }

    /**
     * 身份验证,检查 JWT token 是否合法
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {

        AuthenticationToken token = createToken(request, response);


        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken "
                    + "must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        }
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);
            return onLoginSuccess(token,subject,request,response);
        } catch (AuthenticationException e) {

            return false;
        }
    }

    /**
     * 从 Header 里提取 JWT token
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String access_token = httpServletRequest.getHeader("access_token");
        String refresh_token = httpServletRequest.getHeader("refresh_token");
        JwtToken token = new JwtToken(access_token,refresh_token);
        return token;
    }

    /**
     * isAccessAllowed()方法返回false，会进入该方法，表示拒绝访问
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {



        HttpServletResponse httpResponse = WebUtils.toHttp(servletResponse);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=UTF-8");
        httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        //重定向到自己设置的Token验证不通过时的请求
        httpResponse.sendRedirect("/unauthorized");

        fillCorsHeader(WebUtils.toHttp(servletRequest), httpResponse);
        return false;
    }

    /**
     * Shiro 利用 JWT token 登录成功，会进入该方法
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {

        String access_token = (String) token.getPrincipal();

        String refresh_token = (String) token.getCredentials();

        String username = JwtUtils.getUsername(access_token);

        if(!JwtUtils.verify(access_token,username,"123456")){

            if(JwtUtils.verify(refresh_token,username,"123456")) {
                String newAccess_token = JwtUtils.sign(username, "123456",10* 1000);
                String newRefresh_token = JwtUtils.sign(username, "123456",2 * 20 * 1000);

                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                httpServletResponse.addHeader("Access-Control-Expose-Headers","refresh_token");
                httpServletResponse.addHeader("Access-Control-Expose-Headers","access_token");

                httpServletResponse.setHeader("access_token",newAccess_token);
                httpServletResponse.setHeader("refresh_token",newRefresh_token);

            }


        }


        return true;
    }


    /**
     * 添加跨域支持
     */
    protected void fillCorsHeader(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {


        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD");
        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                httpServletRequest.getHeader("Access-Control-Request-Headers"));

    }

}
