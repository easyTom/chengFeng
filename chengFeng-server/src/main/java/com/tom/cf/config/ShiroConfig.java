package com.tom.cf.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.tom.cf.core.service.UserRightService;
import com.tom.cf.core.service.UserService;
import com.tom.cf.shiro.MyShiroRealm;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @method: shiro配置控制权限
 */
@Configuration
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroConfig {

    @Autowired
    private ShiroProperties shiroProperties;
    @Autowired
    private UserRightService nmUserRightService;
    @Autowired
    private UserService nmUserService;

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }


    @Bean
    public EhCacheManager ehCacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator(authenticator());
//        securityManager.setSubjectFactory(casSubjectFactory());
        securityManager.setCacheManager(ehCacheManager());
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    @Bean(name = "authenticator")
    public ModularRealmAuthenticator authenticator() {
        ModularRealmAuthenticator auth = new ModularRealmAuthenticator();
        auth.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return auth;
    }

    /**
     * 常用过滤器：
     * 	anon：无需认证
     * 	authc：必须认证
     * 	user：使用记住功能可以访问
     * 	perms：必须得到资源权限
     * 	role：必须获得橘色权限
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //登录
        shiroFilterFactoryBean.setLoginUrl(this.shiroProperties.getSuccessUrl());
        //首页
        shiroFilterFactoryBean.setSuccessUrl(this.shiroProperties.getSuccessUrl2());
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/static/**","anon");
        filterChainDefinitionMap.put("/tom/**","user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SingleSignOutFilter singleSignOutFilter(){
        return new SingleSignOutFilter();
    }

    @Bean
    public FilterRegistrationBean singleSignOutFilterBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(singleSignOutFilter());
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setName("singleFilter");
        return filterRegistrationBean;
    }

    //将自己的验证方式加入容器
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setNmUserRightService(nmUserRightService);
        myShiroRealm.setNmUserService(nmUserService);
        myShiroRealm.setAuthenticationCachingEnabled(true);
        myShiroRealm.setAuthenticationCacheName("authenticationCache");
        myShiroRealm.setAuthorizationCachingEnabled(true);
        myShiroRealm.setAuthorizationCacheName("authorizationCache");
        return myShiroRealm;
    }


}
