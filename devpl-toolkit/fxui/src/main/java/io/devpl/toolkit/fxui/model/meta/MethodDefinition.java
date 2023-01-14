package io.devpl.toolkit.fxui.model.meta;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class MethodDefinition implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1996162482198245048L;
	
	private int modifiers;
    private String methodName;
    private Map<String, String> declaredAnnotations;
    
    public void addDeclaredAnnotation(String name, String annotationName) {
    	declaredAnnotations.put(name, annotationName);
    }
}
