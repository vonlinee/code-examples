package mybatis;

import mybatis.entity.Department;
import mybatis.mapper.DepartmentMapper;
import mybatis.tree.TreeNode;
import ognl.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.scripting.xmltags.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class MyBatisUtils {

    public static ParseResult parseXml(String xml) {
        // 直接获取XML中的节点
        XPathParser xPathParser = new XPathParser(xml, false, null, new IgnoreDTDEntityResolver());
        XNode selectNode = xPathParser.evalNode("select");
        Configuration configuration = new Configuration();
        MyXmlStatemtnBuilder statementParser = new MyXmlStatemtnBuilder(configuration, selectNode);
        // 解析结果会放到 Configuration里
        statementParser.parseStatementNode();
        Collection<MappedStatement> mappedStatements = configuration.getMappedStatements();
        // MyBatis会存在重复的两个 MappedStatement，但是ID不一样
        // StrictMap的put方法
        List<MappedStatement> list = mappedStatements.stream().distinct().collect(Collectors.toList());

        MappedStatement mappedStatement = list.get(0);
        Set<String> ognlVar = getOgnlVar(mappedStatement);
        return new ParseResult(tree(ognlVar), mappedStatement);
    }

    public static TreeNode<String> tree(Set<String> ognlVar) {
        TreeNode<String> forest = new TreeNode<>("root");
        TreeNode<String> current = forest;
        for (String expression : ognlVar) {
            TreeNode<String> root = current;
            for (String data : expression.split("\\.")) {
                current = current.addChild(data);
            }
            current = root;
        }
        return forest;
    }

    public static void main(String[] args) throws IOException, OgnlException {
        test3();
    }

    public static void test3() {
        // 1.指定MyBatis主配置文件位置
        String resource = "mybatis-config.xml";
        // 2.加载配置文件
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 解析XML
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        Configuration configuration = factory.getConfiguration();

        SqlSession sqlSession = factory.openSession();

        DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);

        Map<String, Object> map = new HashMap<>();
        map.put("deptId", "12");
        map.put("condition", "12");
        List<Department> list = mapper.selectList(map);


        System.out.println(list);
    }

    public static ParseResult test2() {
        return parseXml("  <select id=\"listCloudServiceStatus\" resultType=\"com.lancoo.cloudresource.domain.vo.CloudServiceStatusVO\">\n" +
                "    SELECT * FROM (\n" +
                "    SELECT resource_base.*,\n" +
                "    acc.provider, acc.account,\n" +
                "    DATEDIFF(resource_base.expire_time, now()) as left_expire_days\n" +
                "    FROM resource_base\n" +
                "    LEFT JOIN account_info AS acc ON resource_base.account_id = acc.id\n" +
                "    LEFT JOIN project_product_resource ppr ON resource_base.id = ppr.resource_id\n" +
                "    LEFT JOIN product prod ON prod.id = ppr.product_id\n" +
                "    LEFT JOIN project p ON p.id = ppr.project_id\n" +
                "    WHERE 1 = 1\n" +
                "    <if test=\"param.resType != null and param.resType != ''\">\n" +
                "      AND resource_base.table_name = #{param.resType}\n" +
                "    </if>\n" +
                "    <if test=\"param.resBelongType != null\">\n" +
                "      AND resource_base.belong_type = #{param.resBelongType}\n" +
                "    </if>\n" +
                "    <if test=\"param.provider != null and param.provider != ''\">\n" +
                "      AND INSTR(acc.provider, #{param.provider}) <![CDATA[>]]> 0\n" +
                "    </if>\n" +
                "    <if test=\"param.productDutyPerson != null and param.productDutyPerson != ''\">\n" +
                "      AND INSTR(prod.owner_name, #{param.productDutyPerson}) <![CDATA[>]]> 0\n" +
                "    </if>\n" +
                "    <if test=\"param.expireTimeStart != null\">\n" +
                "      AND resource_base.expire_time <![CDATA[>=]]> #{param.expireTimeStart}\n" +
                "    </if>\n" +
                "    <if test=\"param.expireTimeEnd != null\">\n" +
                "      AND resource_base.expire_time <![CDATA[<=]]> #{param.expireTimeEnd}\n" +
                "    </if>\n" +
                "    <if test=\"param.contractUsername != null and param.contractUsername != ''\">\n" +
                "      AND INSTR(resource_base.contract_username, #{param.contractUsername}) <![CDATA[>]]> 0\n" +
                "    </if>\n" +
                "    <if test=\"param.contractSerialNumber != null and param.contractSerialNumber != ''\">\n" +
                "      AND INSTR(resource_base.contract_serial_number, #{param.contractSerialNumber}) <![CDATA[>]]> 0\n" +
                "    </if>\n" +
                "    <if test=\"param.startTimeStart != null\">\n" +
                "      AND resource_base.start_time <![CDATA[>=]]> #{param.startTimeStart}\n" +
                "    </if>\n" +
                "    <if test=\"param.startTimeEnd != null\">\n" +
                "      AND resource_base.start_time <![CDATA[<=]]> #{param.startTimeEnd}\n" +
                "    </if>\n" +
                "    <if test=\"param.resName != null and param.resName != ''\">\n" +
                "      AND INSTR(resource_base.resource_name, #{param.resName}) <![CDATA[>]]> 0\n" +
                "    </if>\n" +
                "    ) A\n" +
                "    <where>\n" +
                "      <if test=\"param.leftExpireDays != null\">\n" +
                "        AND A.left_expire_days <![CDATA[<=]]> #{param.leftExpireDays}\n" +
                "      </if>\n" +
                "    </where>\n" +
                "    ORDER BY\n" +
                "    <choose>\n" +
                "      <when test=\"param.startTimeOrder != null and param.startTimeOrder == 'asc'\">\n" +
                "        A.start_time ASC,\n" +
                "      </when>\n" +
                "      <otherwise>A.start_time DESC,</otherwise>\n" +
                "    </choose>\n" +
                "    <choose>\n" +
                "      <when test=\"param.expireTimeOrder != null and param.expireTimeOrder == 'asc'\">\n" +
                "        A.expire_time ASC\n" +
                "      </when>\n" +
                "      <otherwise>A.expire_time DESC</otherwise>\n" +
                "    </choose>\n" +
                "  </select>");
    }

    public static void test1() {
        // 1.指定MyBatis主配置文件位置
        String resource = "mybatis-config.xml";
        // 2.加载配置文件
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 解析XML
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        Configuration configuration = factory.getConfiguration();
        // 如果ResultType或者ParameterType中的涉及到类名不是实际的类名会报错
        // MyBatis会先通过Class.forName查找当前JVM有没有该类，没有的化会报错：ClassNotFound
        MappedStatement ms = configuration.getMappedStatement("mybatis.mapper.DepartmentMapper.listCloudServiceStatus");
        SqlSource sqlSource = ms.getSqlSource();
        if (sqlSource instanceof DynamicSqlSource) {
            DynamicSqlSource dss = (DynamicSqlSource) sqlSource;
            SqlNode rootNode = (SqlNode) ReflectionUtils.getValue(dss, "rootSqlNode");
            HashSet<String> result = new HashSet<>();
            searchExpressions(rootNode, result);
        }
        Collection<MappedStatement> mappedStatements = configuration.getMappedStatements();
        System.out.println(mappedStatements);
    }

    public static Set<String> getOgnlVar(MappedStatement mappedStatement) {
        SqlSource sqlSource = mappedStatement.getSqlSource();
        HashSet<String> result = new HashSet<>();
        if (sqlSource instanceof DynamicSqlSource) {
            DynamicSqlSource dss = (DynamicSqlSource) sqlSource;
            SqlNode rootNode = (SqlNode) ReflectionUtils.getValue(dss, "rootSqlNode");
            searchExpressions(rootNode, result);
        }
        return result;
    }

    /**
     * 查找MyBatis的MappedStatement中所有出现的变量引用，只能出现文本中出现的变量
     * @param parent      根节点
     * @param expressions 存放结果，未去重
     */
    @SuppressWarnings("unchecked")
    public static void searchExpressions(SqlNode parent, Set<String> expressions) {
        if (parent instanceof MixedSqlNode) {
            MixedSqlNode msn = (MixedSqlNode) parent;
            List<SqlNode> contents = (List<SqlNode>) ReflectionUtils.getValue(msn, "contents");
            if (contents != null) {
                for (SqlNode content : contents) {
                    searchExpressions(content, expressions);
                }
            }
        } else if (parent instanceof StaticTextSqlNode) {
            StaticTextSqlNode stsn = (StaticTextSqlNode) parent;
            String sqlText = (String) ReflectionUtils.getValue(stsn, "text");
            if (sqlText != null && !sqlText.isEmpty()) {
                find(sqlText, expressions);
            }
        } else if (parent instanceof TextSqlNode) {
            TextSqlNode tsn = (TextSqlNode) parent;
            String sqlText = (String) ReflectionUtils.getValue(tsn, "text");
            if (sqlText != null && !sqlText.isEmpty()) {
                find(sqlText, expressions);
            }
        } else if (parent instanceof ForEachSqlNode) {
            ForEachSqlNode fesn = (ForEachSqlNode) parent;
            String expression = (String) ReflectionUtils.getValue(fesn, "collectionExpression");
            expressions.add(expression);
            SqlNode contents = (SqlNode) ReflectionUtils.getValue(fesn, "contents");
            searchExpressions(contents, expressions);
        } else if (parent instanceof IfSqlNode) {
            // IfSqlNode会导致解析到的表达式重复
            IfSqlNode ifsn = (IfSqlNode) parent;
            // test 条件
            String testCondition = (String) ReflectionUtils.getValue(ifsn, "test");
            parseIfExpression(testCondition, expressions);
            // 解析条件表达式中使用的表达式变量  Ognl表达式
            SqlNode content = (SqlNode) ReflectionUtils.getValue(ifsn, "contents");
            searchExpressions(content, expressions);
        } else if (parent instanceof WhereSqlNode) {
            WhereSqlNode wsn = (WhereSqlNode) parent;
            SqlNode contents = (SqlNode) ReflectionUtils.getValue(wsn, "contents");
            searchExpressions(contents, expressions);
        } else if (parent instanceof SetSqlNode) {
            SetSqlNode ssn = (SetSqlNode) parent;
            SqlNode contents = (SqlNode) ReflectionUtils.getValue(ssn, "contents");
            searchExpressions(contents, expressions);
        } else if (parent instanceof ChooseSqlNode) {
            ChooseSqlNode csn = (ChooseSqlNode) parent;
            List<SqlNode> ifSqlNodes = (List<SqlNode>) ReflectionUtils.getValue(csn, "ifSqlNodes");
            if (ifSqlNodes != null) {
                SqlNode defaultSqlNode = (SqlNode) ReflectionUtils.getValue(csn, "defaultSqlNode");
                if (defaultSqlNode != null) {
                    ifSqlNodes.add(defaultSqlNode);
                }
                for (SqlNode sqlNode : ifSqlNodes) {
                    searchExpressions(sqlNode, expressions);
                }
            }
        }
    }

    private static void parseIfExpression(String testCondition, Set<String> expressions) {
        try {
            Object node = Ognl.parseExpression(testCondition);
            ExpressionNode expressionNode = (ExpressionNode) node;
            searchOgnlExpressionNode(expressionNode, expressions);
        } catch (OgnlException e) {
            // ignore
        }
    }

    private static void searchOgnlExpressionNode(SimpleNode expressionNode, Set<String> results) {
        if (expressionNode instanceof ExpressionNode) {
            // 比较
            if (expressionNode instanceof ASTNotEq) {
                ASTNotEq notEq = (ASTNotEq) expressionNode;
                searchChildren(notEq, results);
            } else if (expressionNode instanceof ASTAnd) {
                ASTAnd andNode = (ASTAnd) expressionNode;
                searchChildren(andNode, results);
            } else if (expressionNode instanceof ASTEq) {
                ASTEq eqNode = (ASTEq) expressionNode;
                searchChildren(eqNode, results);
            }
        } else if (expressionNode instanceof ASTChain) {
            ASTChain chainNode = (ASTChain) expressionNode;
            results.add(chainNode.toString());
        }
    }

    private static void searchChildren(SimpleNode parent, Set<String> results) {
        int childrenCount = parent.jjtGetNumChildren();
        for (int i = 0; i < childrenCount; i++) {
            Node node = parent.jjtGetChild(i);
            searchOgnlExpressionNode((SimpleNode) node, results);
        }
    }

    /**
     * 递归寻找$引用的表达式，对应的SqlNode是 TextSqlNode
     * @param content     文本，包含${xxx}或者#{xxx}
     * @param expressions 存放结果的容器
     */
    private static void find(String content, Set<String> expressions) {
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
            if ((chars[i] == '$' || chars[i] == '#') && chars[i + 1] == '{') {
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
                expressions.add(String.valueOf(chars1));
                i = endIndex + 1;
            }
        }
    }
}