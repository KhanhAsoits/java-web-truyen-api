package com.shv.app.dto;

import javax.validation.constraints.NotNull;

public class AuthorDto {

    private String id;

    @NotNull
    private String author_name;

    @NotNull
    String user_email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}

