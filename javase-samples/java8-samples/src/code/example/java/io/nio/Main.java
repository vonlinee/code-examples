package code.example.java.io.nio;

public class Main {
	public static void main(String[] args) {
		new Thread(new BioThreadServer(), "Server Thread").start();;
		
	}
}
