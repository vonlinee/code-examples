package sample.java8.collections.set;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Hero implements Comparable<Hero> {

	private int i;
	private String name;
	
	public Hero(int i, String name) {
		this.i = i;
		this.name = name;
	}
	
	@Override
	public int compareTo(Hero o) {
		return this.i - o.i;
	}
}
