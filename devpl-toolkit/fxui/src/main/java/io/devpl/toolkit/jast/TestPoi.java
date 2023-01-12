package io.devpl.toolkit.jast;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;

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
