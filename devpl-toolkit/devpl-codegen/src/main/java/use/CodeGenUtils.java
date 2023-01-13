package use;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.squareup.javapoet.*;
import io.devpl.codegen.mbpg.util.FastJsonUtils;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class CodeGenUtils {

    public static void test() {

    }

    public static void generate(File file) {
        final Map<String, Object> map = FastJsonUtils.read(file);
        final TopLevelClass clazz = new TopLevelClass("");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            final String key = entry.getKey();
            final Object value = entry.getValue();

            if (value instanceof JSONArray) {
                // 取第1个
                JSONArray array = (JSONArray) value;
                final Object obj = array.get(0);
                if (obj instanceof JSONObject) {
                    final JSONObject jsonObject = (JSONObject) obj;
                }
            }
        }
    }

    public static void json2ObjectSchema(String jsonStr, String packageName, String className) throws IOException {
        final Map<String, Object> map = FastJsonUtils.toMap(jsonStr);

        final TypeSpec.Builder builder = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            final String key = entry.getKey();
            final Object value = entry.getValue();
            Type fieldType;
            final Class<?> type = value.getClass();
            // JSON序列化不会序列化为基本类型，一般都是包装类
            if (type == Integer.class) {
                fieldType = Integer.class;
            } else if (type == String.class) {
                fieldType = String.class;
            } else {
                fieldType = String.class;
            }
            final FieldSpec field = FieldSpec
                    .builder(fieldType, key, Modifier.PUBLIC)
                    .addAnnotation(AnnotationSpec
                            .builder(ClassName.bestGuess("com.fasterxml.jackson.annotation.JsonAlias"))
                            .addMember("value", "", "")
                            .build())
                    .build();
            builder.addField(field);
        }
        final TypeSpec type = builder.build();
        final JavaFile file = JavaFile.builder(packageName, type).build();
        file.writeTo(System.out);
    }

    public static String toLowerCamelStyle(String str) {
        if (str.contains("_")) {
            return StringUtils.underlineToCamel(str);
        } else {
            return str;
        }
    }

    public static void main(String[] args) throws IOException {

    }
}
