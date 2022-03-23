package code.exmaple.spring.cycdepend.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Model {
	@Autowired
	public Student student;
}
