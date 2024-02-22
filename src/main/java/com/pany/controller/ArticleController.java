package com.pany.controller;

import com.pany.pojo.Result;
import com.pany.response.UploadArticleResponse;
import com.pany.service.UserService;
import com.pany.util.AliOssUtil;
import com.pany.util.JwtUtils;
import com.pany.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ThreadLocalUtil threadLocalUtil;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    public Result<UploadArticleResponse> upload(@RequestHeader(name="Authorization") String token,
                                                MultipartFile file)throws IOException{
        if(file.isEmpty()) {
            return Result.error("文件为空");
        }

        Map<String,Object> claims = threadLocalUtil.get();
        String username = (String) claims.get("username");

        String url;
        try {
            url = aliOssUtil.uploadFile("test.png", file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
        return Result.success(new UploadArticleResponse(url));
    }

}
