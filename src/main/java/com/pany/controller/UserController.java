package com.pany.controller;

import com.pany.pojo.Article;
import com.pany.pojo.Result;
import com.pany.pojo.User;
import com.pany.service.ArticleService;
import com.pany.service.UserService;
import com.pany.util.AliOssUtil;
import com.pany.util.RedisUtil;
import com.pany.util.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,20}$") String username,
                           @Pattern(regexp = "^(?![0-9]+$)(?!a-zA-Z]+$)[0-9A-Za-z\\W]{5,20}$") String password) {
        User user = userService.findByName(username);
        if (user != null) {
            return Result.error("用户名被占用");
        }
        userService.register(username, password);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        User user = userService.findByName(username);
        if (user == null) {
            return Result.error("用户名不存在");
        }
        if (!userService.checkPassword(user, password)) {
            return Result.error("密码错误");
        }
        return Result.success(userService.createLoginResponse(user));
    }

    @GetMapping("/get_user")
    public Result getUser(){
        User user = userService.getUserById();
        if(user==null){
            return Result.error("用户不存在");
        }else{
            return Result.success(user);
        }
    }

    @GetMapping("/get_user_article")
    public Result getArticleByUserId(){
        List<Article> articles = articleService.getArticleByUserId();
        return Result.success(articles);
    }

    @GetMapping("/get_user_likes")
    public Result getLikesByUserId(){
        List<Article> articles = articleService.getLikesByUserId();
        if(articles.isEmpty()){
            return Result.success("NULL");
        }else{
            return Result.success(articles);
        }
    }

    @PutMapping("/update_name")
    public Result updateName(@Pattern(regexp = "^\\S{5,20}$") String newName) {
        userService.updateName(newName);
        return Result.success();
    }

    @PutMapping("/update_password")
    public Result updatePassword(@Pattern(regexp = "^\\S{5,20}$") String newPassword) {
        userService.updatePassword(newPassword);
        return Result.success();
    }

    @PostMapping("/update_avatar")
    public Result updateAvatar(MultipartFile file) {
        if (file==null||file.isEmpty()) {
            return Result.error("文件为空");
        }
        String url= null;
        try {
            url = userService.uploadAvatar(Objects.requireNonNull(file.getOriginalFilename()), file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userService.updateAvatar(url);
        return Result.success(url);
    }

}
