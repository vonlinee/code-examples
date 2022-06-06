package thirdlib.javapoet;

import java.util.List;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class JavaPoetUtils {

	public static TypeSpec newType(List<FieldSpec> fields, List<MethodSpec> methods, List<AnnotationSpec> annotations) {
		TypeSpec.Builder builder = TypeSpec.classBuilder("");
		fields.forEach(field -> builder.addField(field));
		methods.forEach(method -> builder.addMethod(method));
		annotations.forEach(annotation -> builder.addAnnotation(annotation));
		return builder.build();
	}
}
