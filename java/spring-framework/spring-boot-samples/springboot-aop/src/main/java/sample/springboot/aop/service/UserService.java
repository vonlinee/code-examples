package sample.springboot.aop.service;

import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

	@Override
	public void login(String username, String password) {
		System.out.println("UserService::login => " + username + ", " + password);
	}
}
