package io.devpl.auth.dao;

import lombok.Data;

@Data
public class UserInfo {
    private String username;
    private String password;
    private String role;
    private String permission;
}
