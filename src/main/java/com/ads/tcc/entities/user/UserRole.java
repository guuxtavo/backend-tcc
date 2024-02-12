package com.ads.tcc.entities.user;

import jakarta.validation.constraints.NotBlank;

public enum UserRole {

    ADMIN("admin"),

    USER("user");

    private String role;

    UserRole(String role){
        this.role = role;
    }
    public String getRole(){
        return role;
    }


}
