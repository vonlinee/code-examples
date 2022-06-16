package io.maker.ext.fm.model;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class JavaClassDefinition extends TemplateDataModelBean {

	private boolean hasCopyRight;
	private String copyRight;
	private boolean hasPackage;
	private List<String> importList;
	private List<String> staticImportList;
	private List<String> fields;
	private List<String> methods;

	private boolean hasJavaDoc;
	private boolean hasStaticCodeBlock;
	private boolean hasFields;
	private boolean hasMethods;

	private String javaDoc;

	private Modifier typeModifier;
	private String javaType;

	@Override
	public String template() {
		return "class.java.ftl";
	}

	@Override
	public void validate() {

	}

	@Override
	public Map<String, Object> asMap() {
		return null;
	}
}
