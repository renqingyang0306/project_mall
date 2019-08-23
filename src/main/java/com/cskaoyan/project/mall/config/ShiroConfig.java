package com.cskaoyan.project.mall.config;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/19 10:19
 */

import com.cskaoyan.project.mall.shiro.CustomRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.HashMap;

@Configuration
public class ShiroConfig {

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(CustomRealm realm, DefaultWebSessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }
    //指明那些方法可以直接访问，那些需要认证，上下有前后顺序
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //shiroFilterFactoryBean.setLoginUrl("/admin/auth/401");
        //shiroFilterFactoryBean.setUnauthorizedUrl(
        //        map.put("/wx/home/index", "anon");"/admin/auth/403");
        HashMap<String,String> map=new HashMap<>();
        //可以直接匿名访问
        map.put("/admin/auth/login", "anon");
        map.put("/wx/home/index", "anon");
        map.put("/wx/auth/login", "anon");
        map.put("/wx/goods/**", "anon");
        map.put("/wx/search/**", "anon");
        map.put("/wx/catalog/**", "anon");
        map.put("/wx/storage/**", "anon");
        map.put("/wx/comment/**", "anon");
        map.put("/wx/topic/**", "anon");
        //需要认证
        map.put("/**", "authc");
  
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
    //声明注解权限，在Controller方法名上
    @Bean
    public AuthorizationAttributeSourceAdvisor attributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
    //处理异常信息，也就是访问访问没有授权的资源的时候
    /*@Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties mappings=new Properties();
        mappings.setProperty("org.apache.shiro.authz.AuthorizationException","/exception");
        exceptionResolver.setExceptionMappings(mappings);
        return exceptionResolver;
    }*/
    /*缓存*/
    @Bean
    public EhCacheManager cacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:shiro-cache.xml");
        return cacheManager;
    }
    //Shiro会话管理,设置登录超时时间
    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new MallSessionManager();
        sessionManager.setGlobalSessionTimeout(600000);
        sessionManager.setDeleteInvalidSessions(true);
        return sessionManager;
    }

}
