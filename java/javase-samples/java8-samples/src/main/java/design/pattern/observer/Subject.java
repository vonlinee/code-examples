package design.pattern.observer;

public interface Subject {
    void registerObserver(Observer observer);//注册定义
    void notifyObservers(Object msg);//发送通知
}