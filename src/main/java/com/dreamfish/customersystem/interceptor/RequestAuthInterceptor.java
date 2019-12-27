package com.dreamfish.customersystem.interceptor;

import com.dreamfish.customersystem.annotation.RequestAuth;
import com.dreamfish.customersystem.utils.Result;
import com.dreamfish.customersystem.utils.StringUtils;
import com.dreamfish.customersystem.utils.auth.PublicAuth;
import com.dreamfish.customersystem.utils.response.AuthCode;
import com.dreamfish.customersystem.utils.response.ResponseUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 自动验证注解拦截器
 */
public class RequestAuthInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!(handler instanceof HandlerMethod))
            return true;

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //First auth user and user level
        RequestAuth requestAuth = method.getAnnotation(RequestAuth.class);
        if (requestAuth != null && requestAuth.required()) {
            int authCode = PublicAuth.authCheck(request);
            if(authCode < AuthCode.SUCCESS){
                if(StringUtils.isBlank(requestAuth.redirectTo())) {
                    Result result = Result.failure(requestAuth.unauthCode(), requestAuth.unauthMsg(), String.valueOf(authCode));
                    response.setStatus(Integer.parseInt(requestAuth.unauthCode()));
                    ResponseUtils.responseOutWithJson(response, result);
                }else{
                    response.sendRedirect(requestAuth.redirectTo() + "?error=" +
                            (authCode == AuthCode.FAIL_EXPIRED ? "SessionOut" : "RequestLogin") + "&redirect_url=" + request.getRequestURI());
                }
                return false;
            }
        }


        return true;
    }

}
