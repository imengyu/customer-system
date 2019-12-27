package com.dreamfish.customersystem.web;

import com.dreamfish.customersystem.entity.User;
import com.dreamfish.customersystem.services.AuthService;
import com.dreamfish.customersystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService = null;

    @Autowired
    private HttpServletRequest request = null;
    @Autowired
    private HttpServletResponse response = null;

    //开始认证 登录
    @ResponseBody
    @PostMapping(value = "", name = "开始认证")
    public Result authEntry(@RequestBody @NonNull User user) {
        return authService.authDoLogin(user, request, response);
    }
    //检测认证状态
    @ResponseBody
    @GetMapping(value = "", name = "检测认证状态")
    public Result authTest() {
        return authService.authDoTest(request);
    }
    //结束认证 退出
    @ResponseBody
    @GetMapping(value = "/end", name = "结束认证")
    public Result authEnd(@RequestParam(value = "redirect_uri", required = false) String redirect_uri) throws IOException {
        return authService.authDoLogout(request, response, redirect_uri);
    }
}
