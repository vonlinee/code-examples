package io.maker.ext.fm.model;

import java.util.Map;

/**
 * 
 * @author vonline
 *
 */
public abstract class TemplateDataModelBean {

	public abstract String template();
	
	public abstract void validate() throws TemplateModelBeanException;
	
	public abstract Map<String, Object> asMap();
}
