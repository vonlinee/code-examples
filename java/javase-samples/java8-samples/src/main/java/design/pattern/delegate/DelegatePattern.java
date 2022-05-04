package design.pattern.delegate;

public class DelegatePattern {
	public static void main(String[] args) {
		new Boss().command("架构", new Leader());
	}
}
