package com.dreamfish.customersystem.config;

import com.dreamfish.customersystem.interceptor.RequestAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {

    @Bean
    public RequestAuthInterceptor getRequestAuthInterceptor() {
        return new RequestAuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getRequestAuthInterceptor());
        addInterceptor.addPathPatterns("/**");
    }
}