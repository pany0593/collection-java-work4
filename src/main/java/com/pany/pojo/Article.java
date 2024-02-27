package com.pany.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private String articleId;
    private String title;
    private String authorId;
    private String authorName;
    private String desc;
    private String createTime;
    private Integer likes;
    private Integer clicks;
}
