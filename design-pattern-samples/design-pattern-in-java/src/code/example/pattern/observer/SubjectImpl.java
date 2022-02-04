package code.example.pattern.observer;

import java.util.ArrayList;
import java.util.List;

public class SubjectImpl implements Subject {
	
	//事件发布者需要持有事件订阅者的信息
	private List<Observer> observers = new ArrayList<>();// 观察者集合

	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void notifyObservers(Object msg) {
		// 通知订阅者
		for (Observer observer : observers) {
			observer.update(msg);
		}
	}
}