package sample;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Utils {

    public static File searchStgFile(String name) {
        try {
            try (Stream<Path> pathStream = Files.walk(Paths.get(""))) {
                return pathStream.filter(path -> path.endsWith(name + ".stg")).findFirst().map(Path::toFile).orElse(null);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
