package use;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import io.devpl.codegen.mbpg.util.FastJsonUtils;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class Utils {

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

    public static void json2ObjectSchema() throws IOException {
        final Map<String, Object> map = FastJsonUtils.toMap("    {\n" + "      \"ActName\": \"集结\",\n" + "      \"ActCover\": \"~/App_Data/ActivityFile/1670415194089495.jpg\",\n" + "      \"ActTypeID\": \"bd8d1775-9fb8-43b2-89f2-463f55155e8d\",\n" + "      \"ActTypeName\": \"无敌\",\n" + "      \"ActState\": 2,\n" + "      \"InitiatorID\": \"admincxx\",\n" + "      \"InitiatorName\": \"陈显溪超管\",\n" + "      \"ActPlace\": \"302\",\n" + "      \"StartTime\": \"2022-12-08T20:13:00\",\n" + "      \"EndTime\": \"2022-12-22T20:13:00\",\n" + "      \"CreateTime\": \"2022-12-07T20:14:05.797\",\n" + "      \"Cmt_Ct\": 0,\n" + "      \"Gain_Ct\": 0,\n" + "      \"Prtp_Ct\": 8\n" + "    }");

        final TypeSpec.Builder builder = TypeSpec.classBuilder("A").addModifiers(Modifier.PUBLIC);

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

            builder.addField(FieldSpec.builder(fieldType, key, Modifier.PUBLIC).build());
        }
        final TypeSpec type = builder.build();

        final JavaFile file = JavaFile.builder("sample", type).build();

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
        // CreateTime
        // json2ObjectSchema();
        System.out.println(StringUtils.underlineToCamel("Prtp_Ct"));
    }
}
