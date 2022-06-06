package io.maker.codegen.mbp.util;

import javax.lang.model.element.Modifier;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

public class JavaPoet {

	public static void main(String[] args) {

		Builder builder = TypeSpec.classBuilder(ClassName.get("io.maker.codegen", "HelloController"))
				.addModifiers(Modifier.PUBLIC);
		
		AnnotationSpec requestMappingAnnotation = AnnotationSpec.builder(RequestMapping.class)
				.addMember("value", "/ly/sac/indexpage/")
				.addMember("produces", MediaType.APPLICATION_JSON_VALUE)
				.build();
		
		builder.addAnnotation(requestMappingAnnotation);
		
		AnnotationSpec restControllerAnnotation = AnnotationSpec.builder(RestController.class).build();
		builder.addAnnotation(restControllerAnnotation);

		TypeSpec typeSpec = builder.build();

		System.out.println(typeSpec);
	}
}
