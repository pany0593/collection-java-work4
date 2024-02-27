package com.pany.controller;

import com.pany.pojo.Article;
import com.pany.pojo.Result;
import com.pany.response.GetArticleResponse;
import com.pany.service.ArticleService;
import com.pany.service.UserService;
import com.pany.util.AliOssUtil;
import com.pany.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    public Result upload(String title, String desc,String content){
        if(desc==null||desc.isEmpty()) {
            return Result.error("文件为空");
        }
        String articleId=articleService.createArticleId();
        try {
            aliOssUtil.uploadArticle(articleId,content);
            articleService.createArticle(articleId,title,desc);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("上传失败");
        }
        Article article=articleService.findByArticleId(articleId);
        return Result.success(article);
    }

    @GetMapping("/get_article")
    public Result getArticle(String articleId){
        Article article = articleService.findByArticleId(articleId);
        if(article==null){
            return Result.error("文章不存在");
        }
        article.setAuthorName(articleService.getNameById(article.getAuthorId()));
        articleService.addClicks(articleId);
        String content= null;
        try {
            content = String.valueOf(aliOssUtil.downLoadArticle(articleId));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success(new GetArticleResponse(article,content));
    }

    @GetMapping("/get_list")
    public Result getList(Integer page,Integer sort){
        //?参数校验
        List<Article> articles = articleService.getList(page, sort);
        if(articles==null||articles.isEmpty()){
            return Result.error("列表为空");
        }
        return Result.success(articles);
    }

    @PostMapping("/like")
    public Result likeArticle(String articleId){
        Article article=articleService.findByArticleId(articleId);
        if(article==null){
            return Result.error("文章不存在");
        }
        int originLikes=article.getLikes();
        articleService.likeArticle(articleId);
        //?回滚
        if(articleService.findByArticleId(articleId).getLikes()-originLikes!=1){
            return Result.error("点赞失败");
        }
        return Result.success();
    }
}
