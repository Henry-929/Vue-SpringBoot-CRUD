package com.vueboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrosConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*") //允许跨域的域名，可以用*表示允许任何域名使用
                .allowedMethods("*") //允许任何方法（post、get等）
                .allowCredentials(true) //带上cookie信息
                .allowedHeaders("*") //允许任何请求头
                .maxAge(3600); //maxAge(3600)表明在3600秒内
    }
}
