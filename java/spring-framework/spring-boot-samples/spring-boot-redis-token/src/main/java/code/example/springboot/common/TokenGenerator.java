package code.example.springboot.common;

import org.springframework.stereotype.Component;

/**
 * token生成器接口
 */
@Component
public interface TokenGenerator {
	String generate(String... strings);
}
