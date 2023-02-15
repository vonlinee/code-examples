package utils;

import com.github.javaparser.ast.body.TypeDeclaration;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 类型信息
 */
@Data
public class TypeInfo {

    /**
     * 类型名称
     */
    public static Map<String, List<TypeInfo>> types = new HashMap<>();

    public static TypeInfo register(TypeDeclaration<?> typeDeclaration) {
        TypeInfo typeInfo = TypeInfo.of(typeDeclaration);
        List<TypeInfo> typeInfoList = types.get(typeInfo.getSimpleName());
        if (typeInfoList == null) {
            typeInfoList = new CopyOnWriteArrayList<>();
            typeInfoList.add(typeInfo);
        }
        return typeInfo;
    }

    public static List<TypeInfo> get(String simpleName) {
        return types.get(simpleName);
    }

    private String simpleName; // 简单类名
    private String path; // 所在文件的路径
    private String fullName; // 全类名
    private int level; // 是否是顶级类型

    public static TypeInfo of(TypeDeclaration<?> typeDeclaration) {
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.setSimpleName(typeDeclaration.getName().getIdentifier());
        typeInfo.setFullName(typeDeclaration.getFullyQualifiedName().orElse(""));
        return typeInfo;
    }
}
