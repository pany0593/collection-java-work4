package com.pany.mapper;

import com.pany.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{username}")
    User findByName(String username);

    @Insert("insert into user (userid, username, password) values (#{userid}, #{username}, #{password})")
    void add(User user);
}