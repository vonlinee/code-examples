package io.devpl.toolkit.jast;

import io.devpl.codegen.mbpg.jdbc.dialect.mysql.InfoSchemaColumn;
import io.devpl.codegen.mbpg.jdbc.dialect.mysql.InfoSchemaTable;
import io.devpl.toolkit.fxui.utils.DBUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyle;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyles;
import org.springframework.jdbc.core.DataClassRowMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Test {

    public static final String path = "C:\\Users\\Von\\Desktop\\";

    public static void main(String[] args) throws IOException, SQLException {

        final XWPFDocument doc = new XWPFDocument(new FileInputStream(new File(path, "表字段定义模板.docx")));

        String url = "jdbc:mysql://localhost:3306/lgdb_campus_intelligent_portrait?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
        Properties props = new Properties();
        props.setProperty("user", "root"); //$NON-NLS-1$
        props.setProperty("password", "123456"); //$NON-NLS-1$
        Connection connection = DBUtils.getConnection(url, props);

        final Statement statement = connection.createStatement();

        final ResultSet rs = statement.executeQuery("SELECT * FROM information_schema.`TABLES` where TABLE_SCHEMA = 'lgdb_campus_intelligent_portrait'");
        final DataClassRowMapper<InfoSchemaTable> rowMapper = new DataClassRowMapper<>(InfoSchemaTable.class);
        List<InfoSchemaTable> tmdList = new ArrayList<>();
        int rowIndex = 0;
        while (rs.next()) {
            tmdList.add(rowMapper.mapRow(rs, rowIndex++));
        }

        // 新建文档

        final DataClassRowMapper<InfoSchemaColumn> rowMapperColumn = new DataClassRowMapper<>(InfoSchemaColumn.class);
        for (InfoSchemaTable infoSchemaTable : tmdList) {
            String tabelName = infoSchemaTable.getTableName();
            final String tableComment = infoSchemaTable.getTableComment();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM information_schema.`COLUMNS` where TABLE_NAME = '" + tabelName + "'");
            List<InfoSchemaColumn> columnList = new ArrayList<>();
            int rowIndex1 = 0;
            while (resultSet.next()) {
                columnList.add(rowMapperColumn.mapRow(resultSet, rowIndex1++));
            }

            final List<XWPFParagraph> paragraphs = doc.getParagraphs();
            int size = paragraphs.size();
            for (int i = 0; i < size; i++) {
                final XWPFParagraph xwpfParagraph = paragraphs.get(i);
                final XWPFParagraph newParagraph = doc.createParagraph();

                final List<XWPFRun> runs = xwpfParagraph.getRuns();
                for (int i1 = 0; i1 < runs.size(); i1++) {
                    final XWPFRun run = newParagraph.createRun();

                }
            }


            // final XWPFTable table = doc.createTable();
            // final List<XWPFTableRow> rows = table.getRows();
            // if (rows.size() == 1) {
            //     final XWPFTableRow row = rows.get(0);
            //     // 第一列
            //     final InfoSchemaColumn firstColumn = columnList.get(0);
            //     row.getCell(0).setText(firstColumn.getColumnName());
            // }
        }

        doc.write(new FileOutputStream("C:\\Users\\Von\\Desktop\\2.doc"));
    }

    /**
     * 查找文档样式值
     * @param document  文档类
     * @param styleName 样式名称
     * @return 样式值
     * @throws IOException
     * @throws XmlException
     */
    public static String getStyleValue(XWPFDocument document, String styleName) throws IOException, XmlException {
        if (styleName == null || styleName.length() == 0) {
            return null;
        }
        CTStyles styles = document.getStyle();
        CTStyle[] styleArray = styles.getStyleArray();
        for (CTStyle style : styleArray) {
            if (style.getName().getVal().equals(styleName)) {
                return style.getStyleId();
            }
        }
        return null;
    }
}
