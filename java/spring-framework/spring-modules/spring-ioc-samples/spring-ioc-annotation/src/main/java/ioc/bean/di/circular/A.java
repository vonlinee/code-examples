package ioc.bean.di.circular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @since created on 2022年7月30日
 */
@Component
public class A {
	@Autowired 
	private B b;
}
