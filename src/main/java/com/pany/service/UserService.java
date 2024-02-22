package com.pany.service;

import com.pany.mapper.UserMapper;
import com.pany.pojo.User;
import com.pany.response.LoginResponse;
import com.pany.util.JwtUtils;
import com.pany.util.Md5Utils;
import com.pany.util.SnowFlakeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.lang.Object;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SnowFlakeUtils snowFlakeUtils;
    @Autowired
    private Md5Utils md5Utils;
    @Autowired
    private JwtUtils jwtUtils;

    public User findByName(String username) {
        return userMapper.findByName(username);
    }

    public void register(String username, String password) {
        String md5Password = md5Utils.getMd5(password);
        String userid = String.valueOf(snowFlakeUtils.nextId());
        userMapper.add(new User(userid, username, md5Password));
    }

    public boolean checkPassword(User user, String password) {
        return md5Utils.getMd5(password).equals(user.getPassword());
    }

    public LoginResponse createLoginResponse(User loginUser) {
        Map<String, Object> claims=new HashMap<>();
        claims.put("userid",loginUser.getUserid());
        claims.put("username",loginUser.getUsername());
        String token= jwtUtils.genToken(claims);
        return new LoginResponse("*",token);
    }
}
