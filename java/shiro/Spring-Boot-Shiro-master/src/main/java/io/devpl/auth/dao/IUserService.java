package io.devpl.auth.dao;

public interface IUserService {

    UserInfo findByUserName(String username);
}
