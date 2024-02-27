package com.pany.mapper;

import com.pany.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment (commentId , fatherId , userId , content , createTime , level) " +
            "values (#{commentId} , #{fatherId} , #{userId} , #{content} , now() , #{level})")
    void addComment(String commentId, String fatherId, String userId, String content, Integer level);

    @Select("select * from comment where commentId = #{commentId} ")
    Comment findByCommentId(String commentId);

    @Select("select * from comment where fatherId = #{fatherId} and level = 1")
    List<Comment> findFirstByArticleId(String fatherId);

    @Select("select * from comment where fatherId = #{commentId} and level = 2")
    List<Comment> findSecondByCommentId(String commentId);

    @Update("update comment set likes = likes + 1 where commentId = #{commentId}")
    void likeComment(String commentId);
}