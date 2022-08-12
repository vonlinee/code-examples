package code.example.springboot.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 预重复请求切面方法
 */
@Aspect
@Component
public class PreDupRequestAspect {

    private static final Logger log = LoggerFactory.getLogger(PreDupRequestAspect.class);

    /**
     * 定义切入点规则并且是使用了PreDupRequest注解的接口, 业务开始处理前执行
     * @return
     * @throws RuntimeException 抛出自定义异常，由全局异常处理捕获
     */
    @Before("execution(public * code.example.springboot.controller.*Controller.*(..)) && @annotation(code.example.springboot.anno.DuplicatedRequest)")
    public void before() throws RuntimeException {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            // 用户请求唯一标识，获取当前sessionId
            String sessionId = attributes.getSessionId();
            // 用户请求的uri
            String uri = attributes.getRequest().getServletPath();
            // 针对用户对应接口的唯一key
            String key = sessionId + "-" + uri;
            // 将生成的key存储在redis中，便于集群处理
        } catch (Throwable e) {
            log.error("预防重复请求处理异常", e);
            throw new RuntimeException("");
        }
    }
}