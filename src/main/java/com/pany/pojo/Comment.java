package com.pany.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.PipedReader;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private String commentId;
    @JsonIgnore
    private String fatherId;
    private String userId;
    private String content;
    private String createTime;
    private Integer likes;
    private Integer level;
    private String avatarUrl;
    private List<Comment> subComments;


}
