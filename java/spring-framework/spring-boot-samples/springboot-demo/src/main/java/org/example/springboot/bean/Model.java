package org.example.springboot.bean;

import org.springframework.context.Lifecycle;

public class Model implements Lifecycle {
	public Model() {
		System.out.println("Model() this = " + this);
	}

	@Override
	public void start() {
		System.out.println();
	}

	@Override
	public void stop() {
		
	}

	@Override
	public boolean isRunning() {
		return false;
	}
}
