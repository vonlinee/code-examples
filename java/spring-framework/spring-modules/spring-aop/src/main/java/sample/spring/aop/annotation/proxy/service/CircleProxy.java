package sample.spring.aop.annotation.proxy.service;

import sample.spring.aop.annotation.proxy.aspect.LoggingAspect;
import sample.spring.aop.annotation.proxy.model.Circle;

public class CircleProxy extends Circle {
	public void draw() {
		// Before advice
		LoggingAspect aspect = new LoggingAspect();
		aspect.beforeDrawing();
		super.draw(); // Code before this will be treated as before advice. And code after this will
						// be treated as after advice.
		// After advice
		aspect.wish();
	}
}
