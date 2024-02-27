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

    @Insert("insert into user_article_mer (userId,articleId) values (#{authorId},#{articleId})")
    void addMerge(String authorId, String articleId);

    @Select("select * from article where articleId = #{articleId}")
    Article findByArticleId(String articleId);

    @Select("select * from article order by #{sort} desc limit #{offset}, #{pagesPerPage}")
    List<Article> getList(Integer offset, String sort,Integer pagesPerPage);

    @Update("update article set likes = likes + 1 where articleId = #{articleId}")
    void likeArticle(String articleId);

    @Insert("insert into favorite (userId, articleId) values (#{userId}, #{articleId});")
    void addFavorite(String userId, String articleId);

    @Update("update article set clicks = clicks + 1 where articleId = #{articleId}")
    void addClicks(String articleId);

    @Select("select * from article where authorId = #{userId}")
    List<Article> getArticleByUserId(String userId);

}
