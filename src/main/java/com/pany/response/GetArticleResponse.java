package com.pany.response;

import com.pany.pojo.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetArticleResponse {
    private String articleId;
    private String title;
    private String authorId;
    private String authorName;
    private String desc;
    private String content;
    private String createTime;
    private Integer likes;
    private Integer clicks;

    public GetArticleResponse(Article article,String content) {
        this.articleId = article.getArticleId();
        this.title = article.getTitle();
        this.authorId = article.getAuthorId();
        this.authorName = article.getAuthorName();
        this.desc = article.getDesc();
        this.content = content;
        this.createTime = article.getCreateTime();
        this.likes = article.getLikes();
        this.clicks = article.getClicks();
    }
}
