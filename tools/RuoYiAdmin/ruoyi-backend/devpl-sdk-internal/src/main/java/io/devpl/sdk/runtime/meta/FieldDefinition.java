package io.devpl.sdk.runtime.meta;

import lombok.Data;

import java.io.Serializable;

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
