package com.dreamfish.customersystem.services;


import com.dreamfish.customersystem.entity.User;
import com.dreamfish.customersystem.utils.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    int AUTH_TOKEN_DEFAULT_EXPIRE_TIME = 60*60; //60 Min
    String AUTH_TOKEN_NAME = "CMAuthToken";
    String AUTH_KEY = "VJjEp43MDcLBVYcmBHRtFLEoq3xfDJR6";

    Result authDoLogin(User user, HttpServletRequest request, HttpServletResponse response);
    Result authDoTest(HttpServletRequest request);
    Result authDoLogout(HttpServletRequest request, HttpServletResponse response, String redirect_uri);

}
