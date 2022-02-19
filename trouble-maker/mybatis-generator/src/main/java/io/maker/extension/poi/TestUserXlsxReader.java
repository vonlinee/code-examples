package io.maker.extension.poi;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestUserXlsxReader {

    public void testRead() throws InvalidFormatException, IOException {
        File file = new File("D:/1.xlsx");
        XlsxReader reader = new XlsxReader();
        List<User> users = reader.read(file);
        System.out.println(users);
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        TestUserXlsxReader test = new TestUserXlsxReader();
        test.testRead();
    }

    public void testWrite() throws InvalidFormatException, IOException {
        File file = new File("D:/1.xlsx");
        if (file.exists()) {
            boolean delete = file.delete();
            if (delete) {
                System.out.println("删除成功");
            }
        }
        ExcelWriter writer = null;
        User user1 = new User("admin", "admin", "Administrator");
        User user2 = new User("user1", "user1", "Sally");
        User user3 = new User("user2", "zhangsan", "张三");

    }

    public void testBatchWrite() throws InvalidFormatException, IOException {
        File file = new File("H:/testxlsxbatch.xlsx");
        ExcelWriter writer = null;
        User user1 = new User("admin", "admin", "Administrator");
        User user2 = new User("user1", "user1", "Sally");
        User user3 = new User("user2", "zhangsan", "张三");
        List<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

    }
}
