package com.pany.service;

import com.pany.mapper.UserMapper;
import com.pany.pojo.Result;
import com.pany.pojo.User;
import com.pany.response.LoginResponse;
import com.pany.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.lang.Object;
import java.util.Objects;

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
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private ThreadLocalUtil threadLocalUtil;
    @Value("${default.avatarUrl}")
    private String defaultAvatarUrl;

    public User findByName(String username) {
        return userMapper.findByName(username);
    }

    public String getNameById(String userId){
        return userMapper.findById(userId).getUsername();
    }

    public void register(String username, String password) {
        String md5Password = md5Utils.getMd5(password);
        String userId = String.valueOf(snowFlakeUtils.nextId());
        userMapper.add(new User(userId, username, md5Password,defaultAvatarUrl));
    }

    public boolean checkPassword(User user, String password) {
        return md5Utils.getMd5(password).equals(user.getPassword());
    }

    public LoginResponse createLoginResponse(User loginUser) {
        Map<String, Object> claims=new HashMap<>();
        claims.put("userId",loginUser.getUserId());
        claims.put("username",loginUser.getUsername());
        String token= jwtUtils.genToken(claims);
        return new LoginResponse(loginUser.getAvatarUrl(),token);
    }

    public void updateName(String newName) {
        userMapper.updateName((String)threadLocalUtil.get("userId"),newName);
    }

    public void updatePassword(String newPassword) {
        String md5Password = md5Utils.getMd5(newPassword);
        userMapper.updatePassword((String)threadLocalUtil.get("userId"),md5Password);
    }

    public void updateAvatar(String url) {
        userMapper.updateAvatar((String)threadLocalUtil.get("userId"),url);
    }

    public String uploadAvatar(String objectName, InputStream inputStream){
        String url;
        try {
            url = aliOssUtil.uploadAvatar(objectName,inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return url;
    }

    public User getUserById() {
        String userId = String.valueOf(threadLocalUtil.get("userId"));
        return userMapper.findById(userId);
    }

    public String getUserAvatar(String userId){
        return userMapper.getUserAvatar(userId);
    }
}
