import io.maker.base.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Test {


    public static void main(String[] args) throws IOException {
        ArrayList<String> objects = new ArrayList<>();


        for (int i = 0; i < 49; i++) {
            byte[] bytes = new byte[1024 * 1024]; // 1M
            Arrays.fill(bytes, (byte) i);
            objects.add(new String(bytes));
        }
        File file = new File("D:/Temp/1.txt");
        FileUtils.writeLines(file, objects);



    }
}
