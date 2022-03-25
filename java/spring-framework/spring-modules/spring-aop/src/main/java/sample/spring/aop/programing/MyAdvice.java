package sample.spring.aop.programing;

import java.lang.reflect.Type;

import org.aspectj.lang.reflect.Advice;
import org.aspectj.lang.reflect.AdviceKind;
import org.aspectj.lang.reflect.AjType;
import org.aspectj.lang.reflect.PointcutExpression;

public class MyAdvice implements Advice {

	@Override
	public AjType<?> getDeclaringType() {
		return null;
	}

	@Override
	public AdviceKind getKind() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public AjType<?>[] getParameterTypes() {
		return null;
	}

	@Override
	public Type[] getGenericParameterTypes() {
		return null;
	}

	@Override
	public AjType<?>[] getExceptionTypes() {
		return null;
	}

	@Override
	public PointcutExpression getPointcutExpression() {
		return null;
	}
}
