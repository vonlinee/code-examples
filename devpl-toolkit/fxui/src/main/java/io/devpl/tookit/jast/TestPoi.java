package io.devpl.tookit.jast;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class TestPoi {

    public static void main(String[] args) throws IOException {

        XWPFDocument word = new XWPFDocument(Files.newInputStream(Utils.getDesktopDirectoryPath("1.docx")));
        List<XWPFParagraph> paragraphs = word.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            String text = paragraph.getText();

            System.out.println(text);
        }
    }
}
