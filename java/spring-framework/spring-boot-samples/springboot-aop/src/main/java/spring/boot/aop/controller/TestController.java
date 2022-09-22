package spring.boot.aop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.aop.service.ILogService;
import spring.boot.aop.service.IUserService;
import spring.boot.aop.service.UserCheckService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/user")
public class TestController {

    @Resource
    IUserService userService; // 使用了代理，因此注入的是代理类

    @Resource
    UserCheckService userCheckService;

    @Resource
    ILogService logService;

    // 如果没有为userServcice的方法添加切点，那么不会为UserService生成代理

    //	http://localhost:8080/user/login
    @GetMapping("/login")
    public void login() throws NoSuchMethodException {
        userService.login("zs", "123");
    }

    /**
     * {@link org.springframework.aop.framework.CglibAopProxy}
     */
    public void test() {

    }

    // http://localhost:8080/user/point?flag=true
    // http://localhost:8080/user/point/true
    // Service未实现接口
    @GetMapping("/point/{flag}")
    public void pointcut(@PathVariable(name = "flag") boolean flag, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            userCheckService.checkUserInfo("zs", "123", flag);
        } catch (Exception exception) {
            StackTraceElement[] stackTrace = exception.getStackTrace();

            HttpServletMapping httpServletMapping = request.getHttpServletMapping();

            PrintWriter writer = response.getWriter();
            writer.append(httpServletMapping.getMatchValue());
            for (StackTraceElement stackTraceElement : stackTrace) {
                writer.append("\tat => ").append(String.valueOf(stackTraceElement)).append("\n");
            }
        }
    }
}
