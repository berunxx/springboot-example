package com.myluffy.springboot.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.myluffy.springboot.entity.User;
import org.springframework.stereotype.Component;

@Component
public class DubboConsumerService {

    @Reference
    UserService userService;


    public User saveUser() {
        User user = new User();
        user.setUsername("luffy");
        user.setPassword("luffy1234");
        return userService.saveUser(user);
    }
}
