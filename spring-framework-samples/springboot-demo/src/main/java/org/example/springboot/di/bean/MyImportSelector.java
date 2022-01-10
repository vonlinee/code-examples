package org.example.springboot.di.bean;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

//public class MyImportSelector implements ImportSelector {
	public class MyImportSelector  {
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[] { "org.example.springboot.di.bean.Student" };
	}
}