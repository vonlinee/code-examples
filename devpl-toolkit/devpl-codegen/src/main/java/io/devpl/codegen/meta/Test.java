package io.devpl.codegen.meta;

import java.io.File;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        final String root = new File("").getAbsolutePath() + "/devpl-codegen/src/main/java/";

        String path = root + Bean.class.getName().replace(".", "/") + ".java";

        List<FieldMetaData> list = JavaParserUtils.parse(new File(path), new ASTFieldParser());

        System.out.println(list);
    }
}
