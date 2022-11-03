package io.devpl.auth.model;

import lombok.Data;

@Data
public class User {

    private String userId;
    private String userName;
    private String nickName;
    private String password;
    private String phone;
    private String email;
    private String sex;
    private int status;
    private AuthInformation authInfo;
}
