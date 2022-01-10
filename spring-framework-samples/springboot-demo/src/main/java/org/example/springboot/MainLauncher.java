package org.example.springboot;

import java.util.Date;
import java.util.Map;

import org.example.springboot.config.DataSourceConfiguration;
import org.example.springboot.config.OptionalBeanConfiguration;
import org.example.springboot.transaction.AccountService3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//启动时将数据源自动配置、JPA自动配置及hibernateJpa自动配置排除掉，如果不排除则将按照springboot的自动设置运行程序，自己写的配置hibernate配置无法生效
//@SpringBootApplication(exclude = {
//      DataSourceAutoConfiguration.class,
//      JpaRepositoriesAutoConfiguration.class,
//      HibernateJpaAutoConfiguration.class })
@SpringBootApplication
@Import(value = { DataSourceConfiguration.class, OptionalBeanConfiguration.class })
// 开启事务管理
@EnableTransactionManagement(proxyTargetClass = true) // 启用注解事务，即可以使用@Transactional注解来控制事务等同于xml配置方式的
														// <tx:annotation-driven />
@EnableAspectJAutoProxy // 允许AspectJ自动生成代理
// @ServletComponentScan(value="")
// @ImportResource(locations= {"classpath:xmlconfig/bean.xml"})
@RestController
@RequestMapping("/main")
public class MainLauncher {

	// @Autowired
	// private AccountDaoImpl accountDaoImpl;
	//
	// @Autowired
	// private AccountService accountService;
	//
	// @Autowired
	// private AccountService2 accountService2;

	@Autowired
	private AccountService3 accountService3;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@PostMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> test(@RequestBody(required = false) Map<String, String> paramMap) {
		Date date = new Date(System.currentTimeMillis());
		System.out.println(date); // Tue Jan 04 10:38:54 CST 2022
		jdbcTemplate.update("insert into dates values('1', ?)", date);
		return paramMap;
	}

	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication application = new SpringApplication(MainLauncher.class);
		application.run(args);
		// AccountService3 service = context.getBean(AccountService3.class);
		// service.transferMoney("zs", "ls", 200D, true);
		// service.transfer("zs", "ls", 200D, true);
	}

	@PostMapping(value = "/transfer", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> transfer(@RequestBody(required = false) Map<String, String> paramMap) {
		// accountService3.transferMoney("zs", "ls", 200D, true);
		accountService3.transferMoney("zs", "ls", 200D, Boolean.valueOf(paramMap.get("flag")));
		return paramMap;
	}
}