package com.fujieid.jap.demo.ldap.webmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @date 2021-02-26 11:09
 * @since 1.0.0
 */
@Configuration
public class JapConfig implements WebMvcConfigurer {

    @Autowired
    private JapInterceptor japInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(japInterceptor)
                .excludePathPatterns(
                        // 首页
                        "/",
                        // 退出登录
                        "/logout",
                        // 启用/关闭单点登录
                        "/enableSso",
                        // ldap登录
                        "/ldap/**",
                        // 图片资源
                        "/img/**",
                        // icon
                        "/favicon.ico"
                )
                .addPathPatterns("/**");
    }
}
