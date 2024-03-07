package com.pany.service;

import com.pany.mapper.CommentMapper;
import com.pany.pojo.Comment;
import com.pany.util.SnowFlakeUtils;
import com.pany.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private ThreadLocalUtil threadLocalUtil;
    @Autowired
    private SnowFlakeUtils snowFlakeUtils;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserService userService;
    public String addComment(String articleId, String content, Integer level) {
        String userId = (String) threadLocalUtil.get("userId");
        String commentId="CM"+snowFlakeUtils.nextId();
        commentMapper.addComment(commentId,articleId,userId,content,level);
        return commentId;
    }

    public Comment findByCommentId(String commentId) {
        return commentMapper.findByCommentId(commentId);
    }

    public List<Comment> getCommentByArticleId(String articleId) {
        List<Comment> comments = commentMapper.findFirstByArticleId(articleId);
        for (Comment comment:comments) {
            comment.setSubComments(commentMapper.findSecondByCommentId(comment.getCommentId()));
            comment.setUserName(userService.getNameById(comment.getUserId()));
        }
        return comments;
    }

    public void likeComment(String commentId) {
        commentMapper.likeComment(commentId);
        commentMapper.addFavoriteComment(String.valueOf(threadLocalUtil.get("userId")),commentId);
    }

    public List<Comment> getFavoriteCommentByUserId() {
        return commentMapper.getFavoriteCommentByUserId(String.valueOf(threadLocalUtil.get("userId")));
    }
}
