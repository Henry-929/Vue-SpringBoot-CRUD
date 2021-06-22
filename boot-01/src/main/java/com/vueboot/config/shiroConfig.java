package com.vueboot.config;

import com.vueboot.shiro.CustomSessionManager;
import com.vueboot.shiro.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class shiroConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;

    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     * @param securityManager
     * @return 设置安全管理器securityManager
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /*
            添加shiro的内置过滤器
            anon：无须认证就可以访问
            logout：登出后会清除用户内存
            authc：必须认证了才可以访问
            perms：拥有对某个资源的权限才能访问
            role:拥有某个角色才可以访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();

        //拦截（需将/**,authc放在最下面，否则不进入doGetAuthorizationInfo进行授权）
        filterMap.put("/", "anon");
        filterMap.put("/authError","anon");
        filterMap.put("/login", "anon");
        filterMap.put("/**", "authc");

        //设置跳转到登录页面
        shiroFilterFactoryBean.setLoginUrl("/authError?code=1");
        //设置到未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/authError?code=2");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 将自己的验证方式加入容器
     * @return 创建安全管理器securityManager对象
     */
    @Bean
    public SecurityManager securityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(cacheManager());
        return securityManager;
    }

    /**
     * 将自己的验证方式加入容器
     * @return 创建realm对象
     */
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    /**
     * 绘画管理器
     * 解决Shiro第一次重定向url携带jsessionid问题
     * @return 创建DefaultWebSessionManager类
     */
    public DefaultWebSessionManager sessionManager(){
        CustomSessionManager sessionManager = new CustomSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        //禁用url重写
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        //禁用cookie
        sessionManager.setSessionIdCookieEnabled(false);
        return sessionManager;
    }

    /**
     * cacheManager 缓存管理器 redis实现
     * @return
     */
    public RedisCacheManager cacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * redis管理器，操作redis
     * @return
     */
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host+":"+port);
        redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现,原理就是重写 AbstractSessionDAO
     * @return
     */
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }


    /**
     * 开启shiro 注解模式
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 解决在@controller注解的类方法中加入@requiresrole等shiro注解，会导致方法无法映射请求
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator app = new DefaultAdvisorAutoProxyCreator();
        app.setProxyTargetClass(true);
        return app;
    }
}
