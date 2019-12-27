package com.dreamfish.customersystem.services.impl;


import com.dreamfish.customersystem.entity.User;
import com.dreamfish.customersystem.exception.InvalidArgumentException;
import com.dreamfish.customersystem.mapper.UserMapper;
import com.dreamfish.customersystem.services.AuthService;
import com.dreamfish.customersystem.utils.Result;
import com.dreamfish.customersystem.utils.ResultCodeEnum;
import com.dreamfish.customersystem.utils.StringUtils;
import com.dreamfish.customersystem.utils.auth.PublicAuth;
import com.dreamfish.customersystem.utils.auth.TokenAuthUtils;
import com.dreamfish.customersystem.utils.encryption.AESUtils;
import com.dreamfish.customersystem.utils.request.CookieUtils;
import com.dreamfish.customersystem.utils.request.IpUtil;
import com.dreamfish.customersystem.utils.response.AuthCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper = null;

    @Override
    public Result authDoLogin(User user, HttpServletRequest request, HttpServletResponse response) {

        String userCode = user.getCode();
        String userPass = user.getPassword();

        if(StringUtils.isBlank(userCode) || StringUtils.isBlank(userPass))
            return Result.failure(ResultCodeEnum.PARAM_ERROR);

        List<User> users = userMapper.findByUserCode(userCode);
        if(users.size() == 0)
            return Result.failure(ResultCodeEnum.NOT_FOUNT);

        User userReal = users.get(0);

        if(!userReal.getPassword().equals(AESUtils.encrypt(userPass + "$" + userCode, AUTH_KEY)))
            return Result.failure(ResultCodeEnum.PASS_ERROR);

        String token = "";

        try {
            String ip = IpUtil.getIpAddr(request);
            token = TokenAuthUtils.genToken(AUTH_TOKEN_DEFAULT_EXPIRE_TIME, ip + "#" + userReal.getId() + "#0#0");
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

        CookieUtils.setCookie(response, AUTH_TOKEN_NAME, token);

        Map<String, Object> result = new HashMap<>();
        result.put("userId", userReal.getId());
        return Result.success(result);
    }

    @Override
    public Result authDoTest(HttpServletRequest request) {
        if(testAuth(request)) {
            Map<String, Object> result = new HashMap<>();
            result.put("userId", PublicAuth.authGetUseId(request));
            return Result.success(result);
        }
        return Result.failure(ResultCodeEnum.FAILED_AUTH);
    }

    @Override
    public Result authDoLogout(HttpServletRequest request, HttpServletResponse response, String redirect_uri) {

        if(!testAuth(request))
            return Result.failure(ResultCodeEnum.FAILED_AUTH);
        CookieUtils.setCookie(response, AuthService.AUTH_TOKEN_NAME, "", 0);
        if(!StringUtils.isBlank(redirect_uri)) {
            try {
                response.sendRedirect(redirect_uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Result.success();
    }

    private boolean testAuth(HttpServletRequest request) {
        return PublicAuth.authCheck(request) >= AuthCode.SUCCESS;
    }
}
