package spring.boot.aop.service;

import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

	@Override
	public void login(String username, String password) {
		int i = 1 / 0;
		System.out.println("UserService::login => " + username + ", " + password);
	}
}
