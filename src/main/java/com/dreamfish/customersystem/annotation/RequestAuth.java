package com.dreamfish.customersystem.annotation;

import java.lang.annotation.*;

/**
 * 自动认证注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestAuth {

    /**
     * 是否需要
     */
    boolean required() default true;

    /**
     * 认证失败返回值
     */
    String unauthCode() default "401";

    /**
     * 认证失败返回信息
     */
    String unauthMsg() default "未认证";


    /**
     * 认证失败跳转到URL
     */
    String redirectTo() default "";
}
