package sample.spring.ioc.lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Model {
	@Autowired
	public Student student;
}
