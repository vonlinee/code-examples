package code.example.java.multithread.threadapi;

public class Mutex {

    private final String id;

    public Mutex(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
