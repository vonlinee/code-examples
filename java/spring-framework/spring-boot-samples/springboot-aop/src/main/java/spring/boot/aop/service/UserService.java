package spring.boot.aop.service;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService, BeanNameAware {

    private String name;

    @Override
    public void login(String username, String password) {
        System.out.println("UserService::login => " + username + ", " + password);
    }

    @Override
    public void setBeanName(String name) {
        this.name = name;
    }
}
