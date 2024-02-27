package com.pany.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubComment {
    private String subCommentId;
    private String commentId;
    private String userId;
    private String content;
    private String createTime;
    private Integer likes;
}
