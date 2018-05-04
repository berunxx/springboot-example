package com.myluffy.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private StringRedisTemplate template;

    @PostMapping(value = "/setData/{key}")
    public Object setData(@PathVariable String key) throws Exception {
        ValueOperations<String, String> ops = this.template.opsForValue();
        if (!this.template.hasKey(key)) {
            ops.set(key, "luffy");
        }
//        System.out.println("Found key " + key + ", value=" + ops.get(key));
        return "success";
    }

    @GetMapping(value = "/getData/{key}")
    public Object getData(@PathVariable String key) throws Exception {
        ValueOperations<String, String> ops = this.template.opsForValue();

//        System.out.println("Found key " + key + ", value=" + ops.get(key));
        return ops.get(key);
    }

}
