package com.pany.controller;

import com.pany.pojo.Article;
import com.pany.pojo.Result;
import com.pany.response.GetArticleListResponse;
import com.pany.response.GetArticleResponse;
import com.pany.service.ArticleService;
import com.pany.util.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private AliOssUtil aliOssUtil;
    private Integer pagesPerPage=4;

    @PostMapping("/upload")
    public Result upload(String title, String desc, String content) {
        if (desc == null || desc.isEmpty()) {
            return Result.error("文件为空");
        }
        String articleId = articleService.createArticleId();
        try {
            aliOssUtil.uploadArticle(articleId, content);
            articleService.createArticle(articleId, title, desc);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("上传失败");
        }
        Article article = articleService.findByArticleId(articleId);
        article.setAuthorName(articleService.getNameById(article.getAuthorId()));
        return Result.success(article);
    }

    @GetMapping("/get_article")
    public Result getArticle(String articleId) {
        Article article = articleService.findByArticleId(articleId);
        if (article == null) {
            return Result.error("文章不存在");
        }
        article.setAuthorName(articleService.getNameById(article.getAuthorId()));
        articleService.addClicks(articleId);
        String content = null;
        try {
            content = String.valueOf(aliOssUtil.downLoadArticle(articleId));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success(new GetArticleResponse(article, content));
    }

    @GetMapping("/get_list")
    public Result getList(Integer page, Integer sort) {
        if (page < 1) {
            return Result.error("页数错误");
        }
        List<Article> articles = articleService.getList(page, sort,pagesPerPage);
        if (articles == null || articles.isEmpty()) {
            return Result.error("列表为空");
        }
        int articleNum=articleService.getArticleNum();
        return Result.success(new GetArticleListResponse(articles, (int) Math.ceil((double) articleNum /pagesPerPage)));
    }

    @PostMapping("/like")
    public Result likeArticle(String articleId) {
        Article article = articleService.findByArticleId(articleId);
        if (article == null) {
            return Result.error("文章不存在");
        }
        List<Article> likesArticle = articleService.getLikesByUserId();
        for (Article tmpArticle : likesArticle) {
            if (tmpArticle.getArticleId().equals(articleId)) {
                return Result.error("已经赞过");
            }
        }
        articleService.likeArticle(articleId);
        return Result.success();
    }
}
