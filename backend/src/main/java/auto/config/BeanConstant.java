package auto.config;

public class BeanConstant {

    /**
     * 生成文件路径
     **/
    public static String JPA_CLASS_PATH = "src/main/supervisvionplatform/";

    /**
     * ftl模板所在目录
     **/
    public static String JPA_TEMPLATE_PATH = "src/main/supervisvionplatform/auto/freemaker/templates/";

    /**
     * 字段是否带下划线
     */
    public static Boolean JPA_FIELD_UNDERLINE = true;

    public static String JPA_IMPORTS_BEAN =
            "import supervisvionplatform.io.Serializable;\n" +
            "import supervisvionplatform.lang.*;\n" +
            "import supervisvionplatform.util.*;\n" +
            "import io.swagger.annotations.ApiParam;\n" +
            "import lombok.AllArgsConstructor;\n" +
            "import lombok.Data;\n" +
            "import lombok.NoArgsConstructor;\n" +
            "import lombok.ToString;\n" +
            "import lombok.experimental.Accessors;\n" +
            "import supervisvionplatform.math.BigDecimal;";

    public static String JPA_IMPORTS_SERVICE =
            "import com.github.pagehelper.PageHelper;\n" +
            "import com.github.pagehelper.PageInfo;\n" +
            "import supervisvionplatform.lang.*;\n" +
            "import supervisvionplatform.util.*;";

    public static String JPA_IMPORTS_SERVICE_IMPL =
            "import com.github.pagehelper.PageHelper;\n" +
            "import com.github.pagehelper.PageInfo;\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import lombok.extern.slf4j.Slf4j;\n" +
            "import org.springframework.stereotype.Service;\n" +
            "import javax.servlet.http.HttpServletRequest;\n" +
            "import org.springframework.transaction.annotation.Transactional;\n" +
            "import org.springframework.transaction.interceptor.TransactionAspectSupport;\n" +
            "import supervisvionplatform.lang.*;\n" +
            "import supervisvionplatform.util.*;";

    public static String JPA_IMPORTS_CONTROLLER =
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "import io.swagger.annotations.ApiParam;\n" +
            "import org.springframework.web.bind.annotation.*;\n" +
            "import OneRequestBody;";
}
