package com.myluffy.springboot.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.myluffy.springboot.entity.User;
import com.myluffy.springboot.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User saveUser(User user) {
        user.setId(1);
        return user;
    }
}
