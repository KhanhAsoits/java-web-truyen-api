package com.shv.app.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

public class UserDto {
    public UserDto() {
    }

    private long id;

    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;

    private int state = 1;

    public int isState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    private String role_name;

    public int getState() {
        return state;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
