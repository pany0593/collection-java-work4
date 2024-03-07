package com.pany.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${jwt.ValidityPeriod}")
    private int timeOut;

    public void set(String key,String value){
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(key,value,timeOut, TimeUnit.SECONDS);
    }
    public String get(String key){
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        return operations.get(key);
    }
}
