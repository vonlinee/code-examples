package org.example.java8.collections;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class Hero implements Serializable, Comparable<Hero> {

	private static final long serialVersionUID = 1L;
	
	private static final int LENGTH = 5;
	public static final AtomicInteger HERO_COUNT = new AtomicInteger(1);

	private String id;
	private String name;

	public Hero() {
		super();
		String count = String.valueOf(HERO_COUNT.get());
		StringBuilder sb = new StringBuilder("H");
		for (int i = 0; i < LENGTH - count.length(); i++) {
			sb.append("0");
		}
		this.id = sb.append(HERO_COUNT.getAndIncrement()).toString();
	}
	
	public Hero(String name) {
		super();
		String count = String.valueOf(HERO_COUNT);
		StringBuilder sb = new StringBuilder("H");
		for (int i = 0; i < LENGTH - count.length(); i++) {
			sb.append("0");
		}
		this.id = sb.append(HERO_COUNT.getAndIncrement()).toString();
		this.name = name;
	}

	public Hero(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "[" + id + " " + name + "]";
	}

	@Override
	public int compareTo(Hero o) {
		return o.id.compareTo(this.id);
	}
}
