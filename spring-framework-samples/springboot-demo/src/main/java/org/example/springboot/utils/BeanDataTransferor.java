package org.example.springboot.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.example.springboot.entity.Model;
import org.example.springboot.entity.Student;

/**
 * 
 * @author line
 * @param <A>
 * @param <B>
 */
public abstract class BeanDataTransferor<K, V> {

	protected final Map<String, Object> map = new HashMap<>();
	
    public abstract V to(Map<String, Object> data);

    public abstract Map<String, Object> from(K from);
    
    public static <K, T> T reflectionTransfer(K from, Class<T> to) {
    	Class<? extends Object> fromClass = from.getClass();
    	System.out.println("transfer " + fromClass.getTypeName() + " > " + to.getTypeName());
    	Field[] fromClassfields = fromClass.getDeclaredFields();
    	Field[] toClassfields = to.getDeclaredFields();
    	Field.setAccessible(fromClassfields, true);
    	Field.setAccessible(toClassfields, true);
    	T toObj = null;
    	try {
			toObj = to.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
    	for(Field fromField : fromClassfields) {
        	for(Field toField : toClassfields) {
        		if (fromField.getName().equals(toField.getName())) {
        			Object fromValue = null;
					try {
						toField.set(toObj, fromValue = fromField.get(from));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
						continue; //next field
					}
					System.out.println(fromField.getName() + "(" + fromValue + ") > " + toField.getName());
				}
        	}
    	}
		return toObj;
    }
    
    public static void main(String[] args) {
		Model model = new Model();
		model.setId(200);
		model.setName("孙允珠");
		
		Student student = reflectionTransfer(model, Student.class);
		
		System.out.println(student);
	}
}
