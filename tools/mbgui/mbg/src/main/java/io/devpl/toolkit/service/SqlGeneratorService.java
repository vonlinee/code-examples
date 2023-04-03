package io.devpl.toolkit.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.ITypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.TypeConverts;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.devpl.toolkit.common.BusinessException;
import io.devpl.toolkit.dto.*;
import io.devpl.toolkit.mbp.BeetlTemplateEngine;
import io.devpl.toolkit.sqlparser.ConditionExpression;
import io.devpl.toolkit.sqlparser.DynamicParamSqlEnhancer;
import io.devpl.toolkit.utils.FileUtils;
import io.devpl.toolkit.utils.PathUtils;
import io.devpl.toolkit.utils.ProjectPathResolver;
import io.devpl.toolkit.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static io.devpl.toolkit.dto.Constant.DOT_JAVA;
import static io.devpl.toolkit.dto.Constant.DOT_XML;

/**
 * SQL代码生成
 */
@Slf4j
@Service
public class SqlGeneratorService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private final BeetlTemplateEngine beetlTemplateEngine = new BeetlTemplateEngine("");

    private final ProjectPathResolver projectPathResolver = new ProjectPathResolver("com.example");
    @Resource
    private MapperXmlParser mapperXmlParser;
    @Resource
    private JavaClassParser javaClassParser;

    private final DynamicParamSqlEnhancer dynamicParamSqlEnhancer = new DynamicParamSqlEnhancer(DbType.MYSQL);

    private final List<String> rangeOperators = Lists.newArrayList("BETWEEN", "<", "<=", ">", ">=");

    public void genMapperMethod(String sql, GenConfigDTO config) throws Exception {
        if (config.isAutoCreatedResultDto()) {
            genDtoFileFromSQL(sql, config);
        }
        String namespace = genMapperElementsFromSql(sql, config);
        // 在Dao中插入与Mapper节点对应的方法
        if (config.getEnableCreateDaoMethod()) {
            if ("bean".equals(config.getDaoMethodParamType())) {
                genParamDtoFromSql(sql, config.getDaoMethodParamDto(), config
                        .isEnableLombok());
            }
            addDaoMethod(namespace, sql, config);
        }
    }

    public void genDtoFileFromSQL(String sql, GenConfigDTO config) throws Exception {
        sql = dynamicParamSqlEnhancer.clearIllegalStatements(sql);
        SqlRowSet rowSet;
        try {
            rowSet = jdbcTemplate.queryForRowSet(sql);
        } catch (Exception e) {
            log.error("执行SQL发生错误", e);
            throw new BusinessException("执行SQL发生错误：" + e.getMessage());
        }
        SqlRowSetMetaData metaData = rowSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<DtoFieldInfo> fields = Lists.newArrayList();
        GlobalConfig.Builder gb = new GlobalConfig.Builder();
        GlobalConfig gc = gb.dateType(DateType.ONLY_DATE).build();
        for (int i = 1; i <= columnCount; i++) {
            DtoFieldInfo resultField = new DtoFieldInfo();
            resultField.setColumnName(metaData.getColumnLabel(i));
            // 将数据库类型转换为java类型
            String colType = metaData.getColumnTypeName(i);

            DbType dbType = DbType.MYSQL;
            // 默认 MYSQL
            ITypeConvert typeConvert = TypeConverts.getTypeConvert(dbType);
            if (null == typeConvert) {
                typeConvert = MySqlTypeConvert.INSTANCE;
            }
            IColumnType columnType = typeConvert.processTypeConvert(gc, metaData.getColumnTypeName(i));
            resultField.setShortJavaType(columnType.getType());
            if (!Strings.isNullOrEmpty(columnType.getQualifiedTypeName())) {
                config.getImportPackages().add(columnType.getQualifiedTypeName());
            }
            resultField.setPropertyName(StringUtils.toCamelCase(resultField.getColumnName()));
            fields.add(resultField);
        }
        config.setFields(fields);
        config.setCreateDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
        if (!Strings.isNullOrEmpty(config.getMapperLocation())) {
            config.setComment(config.getMapperLocation() + "的查询结果集，该代码由mybatis-plus-generator-ui自动生成");
        } else {
            config.setComment("该代码由mybatis-plus-generator-ui自动生成");
        }
        genDtoFromConfig(config);
    }


    public static void main(String[] args) throws DocumentException, IOException {
        SqlGeneratorService ser = new SqlGeneratorService();

        String sql = "SELECT \n" +
                "    *\n" +
                "FROM\n" +
                "    t_order t\n" +
                "        LEFT JOIN\n" +
                "    t_order_good t1 ON t.id = t1.order_id\n" +
                "WHERE\n" +
                "    t.order_code = '#{orderCode}'\n" +
                "        AND t.city LIKE '#{city}'\n" +
                "        AND t.customer_id IN '#{customerIds}'\n" +
                "        AND t.creator = '#{creator}'\n" +
                "        AND t.confirm_time BETWEEN '#{startTime}' AND '#{endTime}'";

        GenConfigDTO genConfigDTO = new GenConfigDTO();
        ser.genMapperElementsFromSql(sql, genConfigDTO);
    }

    public String genMapperElementsFromSql(String sql, GenConfigDTO config) throws IOException, DocumentException {
        List<MapperElement> elements = new ArrayList<>();
        // 如果DTO是自动生成的，那么同时也生成结果映射集
        if (config.getResultMap() != null) {
            elements.add(createResultMapElement(config));
        }
        elements.add(createMapperMethodElement(sql, config));
        String mapperPath = projectPathResolver.convertPackageToPath(config.getMapperPackage()) + DOT_XML;
        String namespace = mapperXmlParser.addElementInMapper(mapperPath, elements.toArray(new MapperElement[]{}));
        log.info("ResultMap和Mapper方法已生成，输出位置为:" + mapperPath);
        return namespace;
    }

    /**
     * 生成ResultMap元素
     *
     * @param config
     * @return
     */
    public MapperElement createResultMapElement(GenConfigDTO config) {
        Map<String, Object> tplParams = new HashMap<>();
        tplParams.put("config", config);
        String resultMapStr = beetlTemplateEngine.write2String(tplParams, "classpath:codetpls/resultMap.btl");
        return MapperElement.builder()
                .id(config.getDtoName() + "Map")
                .comment(config.getMapperElementId())
                .content(resultMapStr)
                .location(ElementPosition.FIRST)
                .build();
    }

    public MapperElement createMapperMethodElement(String sql, GenConfigDTO config) {
        Map<String, Object> tplParams = Maps.newHashMap();
        // String dbType = dataSourceConfig.getDbType().getDb();
        tplParams.put("config", config);
        tplParams.put("elementType", "select");
        if (config.isEnableParseDynamicParams()) {
            sql = dynamicParamSqlEnhancer.enhanceDynamicConditions(sql);
        }
        tplParams.put("sql", sql);
        String methodEleStr = beetlTemplateEngine.write2String(tplParams, "classpath:codetpls/mapperMethods.btl");
        return MapperElement.builder()
                .id(config.getMapperElementId())
                .comment("Author:" + config.getAuthor() + "，Date:" + DateUtil.format(new Date(), "yyyy-MM-dd") + ",由mybatis-plus-generator-ui自动生成")
                .content(methodEleStr)
                .location(ElementPosition.LAST)
                .build();
    }

    public void genParamDtoFromSql(String sql, String paramDtoRef, boolean enableLombok) throws Exception {
        List<ConditionExpression> conditionExprs = dynamicParamSqlEnhancer.parseSqlDynamicConditions(sql);
        if (conditionExprs.isEmpty()) {
            log.info("未检测到SQL的动态参数，忽略参数DTO的生成");
            return;
        }
        List<DtoFieldInfo> fields = parseParamFieldsFromSql(sql);
        GenConfigDTO config = new GenConfigDTO();
        config.setEnableLombok(enableLombok);
        for (DtoFieldInfo fi : fields) {
            config.getImportPackages().addAll(fi.getImportJavaTypes());
        }
        config.setFullPackage(paramDtoRef);
        config.setFields(fields);
        config.setCreateDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
        if (!Strings.isNullOrEmpty(config.getMapperLocation())) {
            config.setComment(config.getMapperLocation() + "的查询参数Bean，该代码由mybatis-plus-generator-ui自动生成");
        } else {
            config.setComment("该代码由mybatis-plus-generator-ui自动生成");
        }
        genDtoFromConfig(config);
    }

    /**
     * 根据相关配置生成DAO中的查询方法
     *
     * @param daoClassRef DAO的引用位置
     * @param sql         查询SQL语句
     * @param config      配置参数
     */
    public void addDaoMethod(String daoClassRef, String sql, GenConfigDTO config) throws Exception {
        Set<String> imports = Sets.newHashSet();
        List<DtoFieldInfo> methodParams = Lists.newArrayList();

        String returnType = "";
        if (!Strings.isNullOrEmpty(config.getFullPackage())) {
            imports.add("java.util.List");
            imports.add(config.getFullPackage());
            returnType = "List<" + PathUtils.getShortNameFromFullRef(config.getFullPackage()) + ">";
        } else {
            imports.add("java.util.List");
            imports.add("java.util.Map");
            returnType = "List<Map<String,Object>>";
        }
        // 如果启用分页查询，则修改相关的参数和返回值
        if (config.isEnablePageQuery()) {
            imports.add("com.baomidou.mybatisplus.extension.plugins.pagination.Page");
            returnType = returnType.replaceFirst("List", "Page");
            DtoFieldInfo param = new DtoFieldInfo();
            param.setShortJavaType(PathUtils.getShortNameFromFullRef(returnType));
            param.setPropertyName("pageParam");
            methodParams.add(param);
        }
        List<DtoFieldInfo> sqlConditions = parseParamFieldsFromSql(sql);
        if (!sqlConditions.isEmpty()) {
            if ("map".equals(config.getDaoMethodParamType())) {
                DtoFieldInfo param = new DtoFieldInfo();
                param.setShortJavaType("Map<String,Object>");
                param.setPropertyName("params");
                param.addImportJavaType("java.util.Map");
                methodParams.add(param);
            } else if ("bean".equals(config.getDaoMethodParamType())) {
                DtoFieldInfo param = new DtoFieldInfo();
                param.setShortJavaType(PathUtils.getShortNameFromFullRef(config.getDaoMethodParamDto()));
                param.setPropertyName("params");
                param.addImportJavaType(config.getDaoMethodParamDto());
                methodParams.add(param);
            } else {
                for (DtoFieldInfo fieldInfo : sqlConditions) {
                    NodeList<AnnotationExpr> annotations = new NodeList<>();
                    AnnotationExpr paramAnno = new SingleMemberAnnotationExpr(new Name("Param"), new StringLiteralExpr(fieldInfo.getPropertyName()));
                    annotations.add(paramAnno);
                    fieldInfo.setAnnotations(annotations);
                    fieldInfo.addImportJavaType("org.apache.ibatis.annotations.Param");
                }
                methodParams = sqlConditions;
            }
        }
        for (DtoFieldInfo feild : methodParams) {
            if (feild.getImportJavaTypes() != null) {
                imports.addAll(feild.getImportJavaTypes());
            }
        }
        JavaClassMethodInfo methodInfo = JavaClassMethodInfo.builder()
                .classRef(daoClassRef)
                .methodName(config.getMapperMethod())
                .returnType(returnType)
                .importJavaTypes(imports)
                .params(methodParams)
                .build();
        javaClassParser.addMethod2Interface(methodInfo);
    }

    private List<DtoFieldInfo> parseParamFieldsFromSql(String sql) {
        List<ConditionExpression> conditionExprs = dynamicParamSqlEnhancer.parseSqlDynamicConditions(sql);
        if (conditionExprs.isEmpty()) {
            return Collections.emptyList();
        }
        List<DtoFieldInfo> fields = new ArrayList<>();
        for (ConditionExpression expr : conditionExprs) {
            for (String paramName : expr.getParamNames()) {
                DtoFieldInfo field = new DtoFieldInfo();
                field.setPropertyName(PathUtils.getShortNameFromFullRef(paramName));
                boolean isDate = paramName.toLowerCase().endsWith("date") || paramName.toLowerCase().endsWith("time");
                if (expr.getOperator().equalsIgnoreCase("IN")) {
                    DbColumnType cType = DbColumnType.STRING;
                    if (isDate) {
                        cType = getRightDateType(DateType.ONLY_DATE);
                        field.addImportJavaType(cType.getQualifiedTypeName());
                    }
                    field.setShortJavaType("List<" + cType.getType() + ">");
                    field.addImportJavaType("java.util.List");
                } else if (rangeOperators.contains(expr.getOperator().toUpperCase())) {
                    DbColumnType cType = DbColumnType.LONG;
                    if (isDate) {
                        cType = getRightDateType(DateType.ONLY_DATE);
                        field.addImportJavaType(cType.getQualifiedTypeName());
                    }
                    field.setShortJavaType(cType.getType());
                } else {
                    field.setShortJavaType("String");
                }
                fields.add(field);
            }
        }
        return fields;
    }

    private void genDtoFromConfig(GenConfigDTO config) throws Exception {
        Map<String, Object> tplParams = Maps.newHashMap();
        tplParams.put("config", config);
        String outputPath = projectPathResolver.convertPackageToPath(config.getFullPackage()) + DOT_JAVA;
        FileUtils.createIfNotExistsQuitely(outputPath);
        beetlTemplateEngine.merge(tplParams, "classpath:codetpls/dto.btl", new File(outputPath));
        log.info("DTO已成功生成，输出位置为:" + outputPath);
    }

    private DbColumnType getRightDateType(DateType dateType) {
        switch (dateType) {
            case ONLY_DATE:
                return DbColumnType.DATE;
            case SQL_PACK:
                return DbColumnType.DATE_SQL;
            case TIME_PACK:
                return DbColumnType.LOCAL_DATE;
            default:
                return null;
        }
    }
}
