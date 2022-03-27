package code.example.activemq;

import javax.jms.JMSException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws JMSException {

        File dir = new File("C:\\Users\\vonline\\Pictures\\Wallpaper");
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            int index = files[i].getName().lastIndexOf(".");
            String newFileName = files[i].getName().substring(index);
            File newFile = new File(dir.getAbsolutePath() + File.separator + i + newFileName);
            boolean b = files[i].renameTo(newFile);
            if (b) {
                System.out.println("===========");
            }
        }
    }
}
