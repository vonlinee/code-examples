package ioc.programing;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;

import ioc.programing.bean.Model;

@Component
public class ModelObjectFactory implements ObjectFactory<Model> {

	@Override
	public Model getObject() throws BeansException {
		
		System.out.println("========================");
		return new Model();
	}
}
