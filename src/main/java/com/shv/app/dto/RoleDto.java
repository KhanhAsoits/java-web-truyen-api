package com.shv.app.dto;

import javax.validation.constraints.NotNull;

public class RoleDto {
    public RoleDto(String id, String role_name) {
        this.id = id;
        this.role_name = role_name;
    }
    public RoleDto(){}

    public RoleDto(String role_name) {
        this.role_name = role_name;
    }

    private String id;

    @NotNull
    private String role_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
