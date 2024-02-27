package com.pany.controller;

import com.pany.pojo.Article;
import com.pany.pojo.Comment;
import com.pany.pojo.Result;
import com.pany.service.CommentService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public Result addComment(String fatherId,
                             @Pattern(regexp = "^\\S{1,99}$")String content,
                             Integer level){
        String commentId = commentService.addComment(fatherId, content, level);
        Comment comment=commentService.findByCommentId(commentId);
        return Result.success(comment);
    }

    @GetMapping
    private Result getArticleComment(String articleId){
        List<Comment> comments = commentService.getCommentByArticleId(articleId);
        return Result.success(comments);
    }

    @PostMapping("/like")
    private Result likeComment(String commentId){
        Comment comment=commentService.findByCommentId(commentId);
        if(comment==null){
            return Result.error("文章不存在");
        }
        int originLikes=comment.getLikes();
        commentService.likeComment(commentId);
        //?回滚
        if(commentService.findByCommentId(commentId).getLikes()-originLikes!=1){
            return Result.error("点赞失败");
        }
        return Result.success();
    }
}
