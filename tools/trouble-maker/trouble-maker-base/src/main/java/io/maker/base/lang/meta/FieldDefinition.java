package io.maker.base.lang.meta;

import java.io.Serializable;

import lombok.Data;

@Data
public class FieldDefinition implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4794635022478483091L;
	
	private String name;
    private int modifiers;
    private boolean isPrimitive;
    private String belongToClass;
    
}
