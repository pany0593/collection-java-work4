package com.pany.controller;

import com.pany.pojo.Result;
import com.pany.pojo.User;
import com.pany.service.UserService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,20}$") String username,
                           @Pattern(regexp = "^\\S{5,20}$") String password){
        User user=userService.findByName(username);
        if(user!=null){
            return Result.error("用户名被占用");
        }
        userService.register(username,password);
        return Result.success();
    }
    @PostMapping("/login")
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        User user=userService.findByName(username);
        if(user==null){
            return Result.error("用户名不存在");
        }
        if(!userService.checkPassword(user,password)){
            return Result.error("密码错误");
        }
        return Result.success(userService.createLoginResponse(user));
    }

}
