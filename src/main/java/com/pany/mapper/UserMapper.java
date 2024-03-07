package com.pany.mapper;

import com.pany.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select userId,username,password,avatarUrl from user where username=#{username}")
    User findByName(String username);

    @Select("select userId,username,password,avatarUrl from user where userId=#{userId}")
    User findById(String userId);

    @Insert("insert into user (userId, username, password, avatarUrl) values (#{userId}, #{username}, #{password}, #{avatarUrl})")
    void add(User user);

    @Update("update user set username = #{newName} where userId = #{userId}")
    void updateName(String userId, String newName);

    @Update("update user set password = #{newPassword} where userId = #{userId}")
    void updatePassword(String userId, String newPassword);

    @Update("update user set avatarUrl = #{url} where userId = #{userId}")
    void updateAvatar(String userId, String url);

    @Select("select avatarUrl from user where userId = #{userId}")
    String getUserAvatar(String userId);
}