package com.pany.mapper;

import com.pany.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("insert into article (articleId,title,authorId,`desc`,createTime)" +
            " values (#{articleId},#{title},#{authorId},#{desc},now())")
    void addArticle(String articleId, String title,String authorId, String desc);

    @Select("select articleId,title,authorId,`desc`,createTime,likes,clicks from article where articleId = #{articleId}")
    Article findByArticleId(String articleId);

    @Select("select articleId,title,authorId,`desc`,createTime,likes,clicks from article order by createTime desc limit #{offset}, #{pagesPerPage}")
    List<Article> getListByTime(Integer offset,Integer pagesPerPage);

    @Select("select articleId,title,authorId,`desc`,createTime,likes,clicks from article order by clicks desc limit #{offset}, #{pagesPerPage}")
    List<Article> getListByClick(Integer offset,Integer pagesPerPage);
    @Update("update article set likes = likes + 1 where articleId = #{articleId}")
    void likeArticle(String articleId);

    @Insert("insert into favorite (userId, articleId) values (#{userId}, #{articleId});")
    void addFavorite(String userId, String articleId);

    @Update("update article set clicks = clicks + 1 where articleId = #{articleId}")
    void addClicks(String articleId);

    @Select("select articleId,title,authorId,`desc`,createTime,likes,clicks from article where authorId = #{userId}")
    List<Article> getArticleByUserId(String userId);

    @Select("select count(articleId) FROM article")
    int getArticleNum();
}
