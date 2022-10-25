package sample.java.io.stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;

public class TestStream {

    public static void main(String[] args) throws FileNotFoundException {
    	InputStream is = new FileInputStream(new File(""));
    	Reader reader = new FileReader(new File(""));
    }
}
