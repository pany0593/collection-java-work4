package com.pany.controller;

import com.pany.pojo.Comment;
import com.pany.pojo.Result;
import com.pany.service.CommentService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        List<Comment> favoriteComments = commentService.getFavoriteCommentByUserId();
        for (Comment tmpComment:favoriteComments) {
            if(tmpComment.getCommentId().equals(commentId)){
                return Result.error("已经赞过");
            }
        }
        commentService.likeComment(commentId);
        return Result.success();
    }
}
