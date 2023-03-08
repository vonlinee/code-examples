package sample.java8.multithread;

public class ThisEscape {
    public ThisEscape(EventSource source) {
        source.registerListener(new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
                System.out.println(this);
            }
        });
    }

    void doSomething(Event e) {
    }


    interface EventSource {
        void registerListener(EventListener e);
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {
    }

    public static void main(String[] args) {
        new ThisEscape(e -> e.onEvent(new Event() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        }));
    }
}