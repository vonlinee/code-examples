package sample.spring.boot.token;

import sample.spring.boot.token.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenAuthApplicationTests {

	@Autowired
	UserMapper userMapper;

	@Test
	public void contextLoads() {
		Map<String, Object> map = new HashMap<>();
		map.put("userName", "cong");
		Map<String, Object> user = userMapper.getUser(map);
//		User user = userMapper.getUser(1);
		System.out.println(user);
	}
}

