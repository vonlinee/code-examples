package thirdlib.javapoet;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeSpec;

/**
 * https://blog.csdn.net/IO_Field/article/details/89355941
 */
public class JavaPoetTest {
	
	@Test
	public void testAnnotation() {
		// 使用`get`方法依据指定的注解的设置属性。
		Annotation annoDate = Documented.class.getAnnotation(Target.class);
		AnnotationSpec dateSpec = AnnotationSpec.get(annoDate);

		// 使用`AnnotationSpec.builder()`设置注解的属性
		ClassName annoAuthor = ClassName.get("com.teaphy.poet.annotations", "Author");
		CodeBlock typeBlock = CodeBlock.builder()
			.add("$S", "B") // 这里不能调用addStatement,因为addStatement会添加分号，与语法不符
			.build();
		AnnotationSpec authorSpec = AnnotationSpec.builder(annoAuthor)
			.addMember("name", "$S", "tea") // addMember设置注解属性
			.addMember("type", typeBlock)
			.build();
		//
		ClassName annoAuthors = ClassName.get("com.teaphy.poet.annotations", "Authors");
		AnnotationSpec authorsSpec = AnnotationSpec.builder(annoAuthors)
			.addMember("value", "$L", authorSpec) // addMember设置注解属性
			.addMember("value", "$L", AnnotationSpec.builder(annoAuthor)
				.addMember("name", "$S", "teaA") // addMember设置注解属性
				.addMember("type", "$S", "C")
				.build())
			.build();

		TypeSpec swimBuilder = TypeSpec.interfaceBuilder("ISwim")
			.addAnnotation(dateSpec)
			.addAnnotation(authorSpec)
			.addAnnotation(authorsSpec)
			.build();
		System.out.println(swimBuilder);
	}
}