package com.wasu.es.config;

import com.wasu.es.shiro.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/assets/**", "anon");
        filterChainDefinitionMap.put("/B-JUI/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/temp/**", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/image/**", "anon");
//        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/static/index.jsp", "anon");
        filterChainDefinitionMap.put("/index_websocketClient.html", "anon");
        filterChainDefinitionMap.put("/login.html", "anon");
        filterChainDefinitionMap.put("/API/**", "anon");
        filterChainDefinitionMap.put("/app/**", "anon");
        filterChainDefinitionMap.put("/websocket", "anon");
        filterChainDefinitionMap.put("/web/login", "anon");
        filterChainDefinitionMap.put("/web/autoLogin", "anon");
        filterChainDefinitionMap.put("/web/logout_shiro", "anon");
        filterChainDefinitionMap.put("/web/**", "anon");
        filterChainDefinitionMap.put("/oss", "anon");
        filterChainDefinitionMap.put("/oss/autoLogin", "anon");
        filterChainDefinitionMap.put("/oss/logout_shiro", "logout");
        filterChainDefinitionMap.put("/oss/system/getClusterStats", "anon");
        filterChainDefinitionMap.put("/oss/**", "anon");
        filterChainDefinitionMap.put("/main/**", "anon");
        filterChainDefinitionMap.put("/phantom", "anon");
        filterChainDefinitionMap.put("/logout/**", "logout");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/oss");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/myIndex.jsp");

        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/oss");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public UserRealm myShiroRealm() {
        UserRealm myShiroRealm = new UserRealm();
        return myShiroRealm;
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }
}