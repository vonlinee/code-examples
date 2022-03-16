package sample.java.collections.set;

public class E implements Comparable<E> {

	int i;
	
	public E(int i) {
		this.i = i;
	}
	
	@Override
	public int compareTo(E o) {
		return this.i - o.i;
	}
}
