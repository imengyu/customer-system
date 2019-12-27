package com.dreamfish.customersystem.utils.auth;


import com.dreamfish.customersystem.entity.User;
import com.dreamfish.customersystem.exception.InvalidArgumentException;
import com.dreamfish.customersystem.services.AuthService;
import com.dreamfish.customersystem.utils.StringUtils;
import com.dreamfish.customersystem.utils.request.CookieUtils;
import com.dreamfish.customersystem.utils.request.IpUtil;
import com.dreamfish.customersystem.utils.request.RequestUtils;
import com.dreamfish.customersystem.utils.response.AuthCode;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class PublicAuth {

    public static int authForToken(HttpServletRequest request, Cookie clientToken) {
        return authForToken(request, clientToken.getValue());
    }
    /**
     * 认证Token
     * @param clientToken 颁发给用户的名为 AUTH_TOKEN_NAME 的 认证Token Cookie
     * @return 返回 AuthCode 作为认证状态
     */
    public static int authForToken(HttpServletRequest request, String clientToken) {
        if(clientToken==null)
            return AuthCode.FAIL_BAD_TOKEN;
        String token = clientToken.replace(' ', '+');
        Integer tokenCheckResult;
        String[] tokenOrgData;

        try {
            tokenCheckResult = TokenAuthUtils.checkToken(token, TokenAuthUtils.TOKEN_DEFAULT_KEY);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            return AuthCode.UNKNOW;
        }

        if(tokenCheckResult.intValue() == TokenAuthUtils.TOKEN_CHECK_BAD_TOKEN) {
            return AuthCode.FAIL_BAD_TOKEN;
        }
        else if(tokenCheckResult.intValue() == TokenAuthUtils.TOKEN_CHECK_EXPIRED){
            return AuthCode.FAIL_EXPIRED;
        }


        try {
            tokenOrgData = TokenAuthUtils.decodeToken(token, TokenAuthUtils.TOKEN_DEFAULT_KEY);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            return AuthCode.UNKNOW;
        }

        if(tokenOrgData.length >= 3) {
            String tokenData = tokenOrgData[2];
            String[] tokenOrgCustomData = tokenData.split("#");
            if (tokenOrgCustomData.length >= 4) {
                if (!tokenOrgCustomData[0].equals(IpUtil.getIpAddr(request))) return AuthCode.FAIL_BAD_IP;
                return AuthCode.SUCCESS;
            }
        }

        return AuthCode.FAIL_BAD_TOKEN;
    }

    /**
     * 认证当前请求用户是否登录
     * @param request 当前请求
     * @return 返回 AuthCode 作为认证状态
     */
    public static int authCheck(HttpServletRequest request){

        Cookie cookie = CookieUtils.findCookieByName(request.getCookies(), AuthService.AUTH_TOKEN_NAME);
        if(cookie!=null) return authForToken(request, cookie);
        else if(!StringUtils.isBlank(request.getParameter("token")))
            return PublicAuth.authForToken(request, RequestUtils.decoderURLString(request.getParameter("token")));
        else return AuthCode.FAIL_NOT_LOGIN;
    }

    /**
     * 获取当前已认证请求的用户ID
     * @param request 当前请求
     * @return 返回 AuthCode 作为认证状态
     */
    public static Integer authGetUseId(HttpServletRequest request){
        String token = "";
        Cookie clientToken = CookieUtils.findCookieByName(request.getCookies(), AuthService.AUTH_TOKEN_NAME);
        if(clientToken!=null)
            token = clientToken.getValue();
        else if(!StringUtils.isBlank(request.getParameter("token")))
            token = RequestUtils.decoderURLString(request.getParameter("token")).replace(' ', '+');

        Integer tokenCheckResult;
        String[] tokenOrgData;

        try {
            tokenCheckResult = TokenAuthUtils.checkToken(token, TokenAuthUtils.TOKEN_DEFAULT_KEY);
        } catch (InvalidArgumentException e) {
            return AuthCode.FAIL_BAD_TOKEN;
        }

        if(tokenCheckResult.intValue() == TokenAuthUtils.TOKEN_CHECK_BAD_TOKEN) {
            return AuthCode.FAIL_BAD_TOKEN;
        }
        else if(tokenCheckResult.intValue() == TokenAuthUtils.TOKEN_CHECK_EXPIRED){
            return AuthCode.FAIL_EXPIRED;
        }


        try {
            tokenOrgData = TokenAuthUtils.decodeToken(token, TokenAuthUtils.TOKEN_DEFAULT_KEY);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            return AuthCode.UNKNOW;
        }

        if(tokenOrgData.length >= 3) {
            String tokenData = tokenOrgData[2];
            String[] tokenOrgCustomData = tokenData.split("#");
            if (tokenOrgCustomData.length >= 2) {
                if (StringUtils.isInteger(tokenOrgCustomData[1]))
                    return Integer.parseInt(tokenOrgCustomData[1]);
                else return AuthCode.UNKNOW;
            }
        }

        return AuthCode.UNKNOW;
    }
}
