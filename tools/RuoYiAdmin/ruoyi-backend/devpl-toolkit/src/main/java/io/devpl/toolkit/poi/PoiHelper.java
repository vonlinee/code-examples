package io.devpl.toolkit.poi;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PoiHelper {

    public static XWPFDocument readWord(Path path) throws IOException {
        return new XWPFDocument(Files.newInputStream(path));
    }

    public static XWPFDocument readWord(String pathname) throws IOException {
        return new XWPFDocument(Files.newInputStream(Path.of(pathname)));
    }
}
