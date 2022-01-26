package code.example.spring.beans.factory;

import org.springframework.beans.factory.FactoryBean;

public class StudentFactoryBean implements FactoryBean<Student>{

	@Override
	public Student getObject() throws Exception {
		Student student = new Student();
		System.out.println("getObject  " + student);
		return student;
	}

	@Override
	public Class<?> getObjectType() {
		return Student.class;
	}

	@Override
	public boolean isSingleton() {
//		return true;
		return false;
	}
}
