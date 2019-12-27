package com.dreamfish.customersystem.services.impl;

import com.dreamfish.customersystem.entity.User;
import com.dreamfish.customersystem.mapper.UserMapper;
import com.dreamfish.customersystem.services.UserService;
import com.dreamfish.customersystem.utils.Result;
import com.dreamfish.customersystem.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result getUserById(Integer id) {
        User user = userMapper.getUserById(id);
        if(user != null) return Result.success(user);
        else return Result.failure(ResultCodeEnum.NOT_FOUNT);
    }
}
