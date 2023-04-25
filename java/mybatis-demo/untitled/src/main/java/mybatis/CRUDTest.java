package mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CRUDTest {

    public static void main(String[] args) throws IOException {
        // 1.指定MyBatis主配置文件位置
        String resource = "mybatis-config.xml";
        // 2.加载配置文件
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);

        Configuration configuration = factory.getConfiguration();

        MappedStatement ms = configuration.getMappedStatement("mybatis.mapper.DepartmentMapper.selectList");

        System.out.println(ms);


        SqlSource sqlSource = ms.getSqlSource();

        HashMap<String, Object> map = new HashMap<>();
        map.put("name1", "111");
        map.put("list", Arrays.asList(1, 2, 3));

        if (sqlSource instanceof DynamicSqlSource) {
            DynamicSqlSource dss = (DynamicSqlSource) sqlSource;

            SqlNode rootNode = (SqlNode) ReflectionUtils.getFieldValue(dss, "rootSqlNode");

            // boolean result = rootNode.apply(visitor);

            final ArrayList<String> list = new ArrayList<>();
            search(rootNode, list);

            System.out.println(list);
        }

        if (sqlSource instanceof DynamicSqlSource) {
            DynamicSqlSource dss = (DynamicSqlSource) sqlSource;

        }

        final Object value = Ognl.getValue("", map, null);


        // System.out.println(boundSql.getSql());
    }

    @SuppressWarnings("unchecked")
    public static void search(SqlNode parent, List<String> vars) {
        if (parent instanceof MixedSqlNode) {
            MixedSqlNode msn = (MixedSqlNode) parent;
            List<SqlNode> contents = (List<SqlNode>) ReflectionUtils.getFieldValue(msn, "contents");
            for (SqlNode content : contents) {
                search(content, vars);
            }
        } else if (parent instanceof StaticTextSqlNode) {
            StaticTextSqlNode stsn = (StaticTextSqlNode) parent;
            String sqlText = (String) ReflectionUtils.getFieldValue(stsn, "text");
            if (!sqlText.isEmpty()) {
                find(sqlText, vars);
            }
        } else if (parent instanceof TextSqlNode) {
            TextSqlNode tsn = (TextSqlNode) parent;
            String sqlText = (String) ReflectionUtils.getFieldValue(tsn, "text");
            if (!sqlText.isEmpty()) {
                find(sqlText, vars);
            }
        } else if (parent instanceof ForEachSqlNode) {
            ForEachSqlNode fesn = (ForEachSqlNode) parent;
            String expression = (String) ReflectionUtils.getFieldValue(fesn, "collectionExpression");
            vars.add(expression);
            SqlNode contents = (SqlNode) ReflectionUtils.getFieldValue(fesn, "contents");
            search(contents, vars);
        } else if (parent instanceof IfSqlNode) {
            IfSqlNode ifsn = (IfSqlNode) parent;
            // test 条件
            String testCondition = (String) ReflectionUtils.getFieldValue(ifsn, "test");
            // 解析条件表达式中使用的表达式变量  Ognl表达式
            SqlNode content = (SqlNode) ReflectionUtils.getFieldValue(ifsn, "contents");
            search(content, vars);
        } else if (parent instanceof WhereSqlNode) {
            WhereSqlNode wsn = (WhereSqlNode) parent;
            SqlNode contents = (SqlNode) ReflectionUtils.getFieldValue(wsn, "contents");
            search(contents, vars);
        } else if (parent instanceof SetSqlNode) {
            SetSqlNode ssn = (SetSqlNode) parent;
        } else if (parent instanceof ChooseSqlNode) {
            ChooseSqlNode csn = (ChooseSqlNode) parent;
        }
    }

    /**
     * 寻找$引用的变量
     * @param content
     * @param vars
     * @return
     */
    private static void find(String content, List<String> vars) {
        content = content.trim().replace("\n", "");
        if (content.isEmpty()) {
            return;
        }
        final char[] chars = content.toCharArray();
        int fromIndex, endIndex = 0;
        for (int i = 0; i < chars.length; i++) {
            // MyBatis要求 $和{之间没有空格才有效
            // 且不能嵌套
            // Mapper文件语法正确的情况下，一轮遍历即可，不会回头
            if (chars[i] == '$' && chars[i + 1] == '{') {
                // 找到}
                fromIndex = i + 2;
                endIndex = fromIndex + 1;
                while (chars[endIndex] != '}') {
                    if (chars[endIndex] == ' ') {
                        fromIndex++;
                    }
                    endIndex++;
                }
                final char[] chars1 = Arrays.copyOfRange(chars, fromIndex, endIndex);
                vars.add(String.valueOf(chars1));
                i = endIndex + 1;
            }
        }
    }
}