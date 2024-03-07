package com.pany.mapper;

import com.pany.pojo.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface FavoriteMapper {

    @Select("select userId,articleId from favorite where userId = #{userId}")
    List<Favorite> getLikesByUserId(String userId);
}
