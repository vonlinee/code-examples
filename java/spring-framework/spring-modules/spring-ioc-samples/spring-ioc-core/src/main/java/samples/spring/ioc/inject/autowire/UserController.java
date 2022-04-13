package samples.spring.ioc.inject.autowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

	private UserService userService;
	
	private UserService userService1;
	
	@Autowired
	private UserService userService2;
	
	// 构造器上
	@Autowired(required=false)
	public UserController(UserService service) {
		this.userService = service;
	}
	
	// 方法上
	@Autowired
	public void setService(UserService service) {
		this.userService1 = service;
	}
	
	public void destroyService(@Autowired UserService service) {
		this.userService2 = null;
	}
}
