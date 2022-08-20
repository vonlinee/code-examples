package org.example.springboot.serialize;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TestSerialization {
    public static void main(String[] args) throws IOException {
        serialization();
    }

    public static void serialization() throws IOException {
        FileOutputStream fos = new FileOutputStream("D:\\temp.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        User user = new User();
        user.setAge(18);
        user.setName("sandy");
        oos.writeObject(user);
        oos.flush();
        oos.close();
    }
}
