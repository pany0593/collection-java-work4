package com.pany.service;

import com.pany.mapper.ArticleMapper;
import com.pany.mapper.FavoriteMapper;
import com.pany.pojo.Article;
import com.pany.pojo.Favorite;
import com.pany.util.SnowFlakeUtils;
import com.pany.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArticleService {
    @Autowired
    private SnowFlakeUtils snowFlakeUtils;
    @Autowired
    private ThreadLocalUtil threadLocalUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private FavoriteMapper favoriteMapper;
    //?
    public String createArticleId() {
        return "AR"+ snowFlakeUtils.nextId();
    }
    public void createArticle(String articleId, String title, String desc) {
        String authorId= (String) threadLocalUtil.get("userId");
        articleMapper.addArticle(articleId,title,authorId,desc);
    }
    public Article findByArticleId(String articleId){
        Article article=articleMapper.findByArticleId(articleId);
        return article;
    }

    public List<Article> getList(Integer page, Integer sort,Integer pagesPerPage) {
        List<Article> articles;
        if(sort==1){
            articles=articleMapper.getListByTime((page - 1) * pagesPerPage, pagesPerPage);
        }else{
            articles=articleMapper.getListByClick((page - 1) * pagesPerPage, pagesPerPage);
        }
        for (Article article:articles) {
            article.setAuthorName(userService.getNameById(article.getAuthorId()));
        }
        return articles;
    }

    public void likeArticle(String articleId) {
        articleMapper.likeArticle(articleId);
        articleMapper.addFavorite(String.valueOf(threadLocalUtil.get("userId")),articleId);
    }

    public void addClicks(String articleId) {
        articleMapper.addClicks(articleId);
    }

    public List<Article> getArticleByUserId() {
        String userId= String.valueOf(threadLocalUtil.get("userId"));
        return articleMapper.getArticleByUserId(userId);
    }

    public List<Article> getLikesByUserId() {
        String userId= String.valueOf(threadLocalUtil.get("userId"));
        List<Favorite> favorites = favoriteMapper.getLikesByUserId(userId);
        List<Article> articles = new ArrayList<>();
        for (Favorite favorite: favorites) {
            articles.add(articleMapper.findByArticleId(favorite.getArticleId()));
        }
        return articles;
    }

    public String getNameById(String authorId) {
        return userService.getNameById(authorId);
    }

    public int getArticleNum() {
        return articleMapper.getArticleNum();
    }
}
