package com.zhang.shiro;


import com.zhang.filter.JwtFilter;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Auther: Distance
 * Date: 2020/09/16/19:39
 */
@Configuration
public class ShiroConfig{


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultSecurityManager securityManager){

        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();


        Map<String,Filter> filterMap = new HashMap<>();

        filterMap.put("jwt",new JwtFilter());

        filterFactoryBean.setFilters(filterMap);

        Map<String,String> filterRuleMap = new HashMap<>();

        filterRuleMap.put("/user/login","anon");

        filterRuleMap.put("/unauthorized","anon");

        filterRuleMap.put("/**","jwt,authc");


        filterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);

        filterFactoryBean.setSecurityManager(securityManager);

        return filterFactoryBean;


    }




    @Bean
    public DefaultWebSecurityManager securityManager(){


        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        List<Realm> realms = new ArrayList<>();

        realms.add(userRealm());

        realms.add(jwtRealm());

        securityManager.setRealms(realms);

        securityManager.setAuthenticator(authenticator());

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();

        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();

        sessionStorageEvaluator.setSessionStorageEnabled(false);

        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);

        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;


    }

    @Bean
    public Authenticator authenticator() {

        MultiRealmAuthenticator authenticator = new MultiRealmAuthenticator();

        authenticator.setRealms(Arrays.asList(userRealm(),jwtRealm()));

        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());

        return authenticator;
    }


    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    @Bean
    public JwtRealm jwtRealm(){

        return new JwtRealm();
    }

    /**
     * 添加注解支持
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }


    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

}
