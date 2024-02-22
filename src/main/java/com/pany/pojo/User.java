package com.pany.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userid;
    private String username;
    @JsonIgnore
    private String password;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}