package sample;

public class User {
    public int id; // template can directly access via u.id
    private String name; // template can't access this

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean isManager() {
        return true;
    } // u.manager

    public boolean hasParkingSpot() {
        return true;
    } // u.parkingSpot

    public String getName() {
        return name;
    } // u.name

    public String toString() {
        return id + ":" + name;
    } // u
}