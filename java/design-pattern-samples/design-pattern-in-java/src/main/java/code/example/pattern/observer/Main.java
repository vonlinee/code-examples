package code.example.pattern.observer;

public class Main {
	
	public static void main(String[] args) {
		Subject subject = new SubjectImpl();
		Observer observer1 = new Observer() {
			@Override
			public void update(Object msg) {
				System.out.println("Observer-A => " + msg);
			}
		};
		
		Observer observer2 = new Observer() {
			@Override
			public void update(Object msg) {
				System.out.println("Observer-B => " + msg);
			}
		};
		subject.registerObserver(observer1);
		subject.registerObserver(observer2);
		subject.notifyObservers("Message");
	}
}