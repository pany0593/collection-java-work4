package com.pany.util;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class Md5Utils {
    public String getMd5(String password)
    {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
