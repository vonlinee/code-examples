package io.maker.codegen.mbp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Predicate;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.base.Converter;

import io.maker.base.utils.CaseFormat;
import io.maker.codegen.mbp.config.po.TableField;
import io.maker.codegen.mbp.config.po.TableInfo;
import io.maker.generator.db.meta.schema.ColumnInfoSchema;
import io.maker.generator.db.meta.schema.TableInfoSchema;

/**
 * 生成XML中sql语句的工具类
 */
public class MapperUtils {

	public static final Predicate<String> columnNameFilter = columnName -> {
		List<String> ignoreColumnNames = new ArrayList<>();
		ignoreColumnNames.add("COLUMN1");
		ignoreColumnNames.add("COLUMN2");
		ignoreColumnNames.add("COLUMN3");
		ignoreColumnNames.add("COLUMN4");
		return ignoreColumnNames.contains(columnName);
	};

	public static void tableField(TableInfo tableInfo, String tableAlias) {
		List<TableField> fields = tableInfo.getFields();
		StringBuilder sb = new StringBuilder("INSERT INTO ");
		sb.append(tableInfo.getName()).append(" (\n");

		StringBuilder insertValues = new StringBuilder();
		for (TableField field : fields) {
			String comment = field.getComment();
			String columnName = tableAlias + "." + field.getColumnName();
			sb.append("\t").append(columnName).append(", /*").append(comment).append("*/\n");

			// 特殊值
			String columnCamelName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, field.getColumnName());
			insertValues.append("\t#{param.").append(columnCamelName).append("},\n");
		}

		sb.append("VALUES (\n").append(insertValues).append(")");

		System.out.println(sb);
	}

	/**
	 * 查询标签
	 * 
	 * @param tableInfo
	 * @return
	 */
	public static String selectTag(TableInfo tableInfo) {
		TableInfoSchema tableMetaData = convert(tableInfo);
		List<ColumnInfoSchema> tableColumnMetaDataList = convertFields(tableInfo);

		String tableName = tableMetaData.getTableName();

		StringJoiner selectColumns = new StringJoiner("\n\t\t", "\t\t", "");
		for (ColumnInfoSchema columnInfoSchema : tableColumnMetaDataList) {
			String columnName = columnInfoSchema.getColumnName();
			String columnCamelName = upperUnderscoreTolowerCame.convert(columnName);

			String comment = columnInfoSchema.getColumnComment();
			if (!StringUtils.isEmpty(comment)) {
				selectColumns.add(columnName + ", /*" + comment + "*/");
			} else {
				selectColumns.add(columnName + ",");
			}
		}

		// 去掉末尾的逗号
		String string = selectColumns.toString();
		int lastIndexOf = string.lastIndexOf(",");
		string = string.substring(0, lastIndexOf);

		String selectContent = "\tSELECT\n" + string + " \n\tFROM " + tableName + "\n\tWHERE 1 = 1";

		return selectContent;
	}

	/**
	 * 新增标签
	 * 
	 * @param tableInfo
	 * @return
	 */
	public static String insertTag(TableInfo tableInfo) {
		TableInfoSchema tableMetaData = convert(tableInfo);
		List<ColumnInfoSchema> tableColumnMetaDataList = convertFields(tableInfo);
		String tableName = tableMetaData.getTableName();

		StringJoiner selectColumns = new StringJoiner("\n\t\t", "\t\t", "");
		for (ColumnInfoSchema columnInfoSchema : tableColumnMetaDataList) {
			String columnName = columnInfoSchema.getColumnName();
			String columnCamelName = upperUnderscoreTolowerCame.convert(columnName);

			String comment = columnInfoSchema.getColumnComment();
			if (!StringUtils.isEmpty(comment)) {
				selectColumns.add(columnName + ", /*" + comment + "*/");
			} else {
				selectColumns.add(columnName + ",");
			}
		}
		// 去掉末尾的逗号
		String string = selectColumns.toString();
		int lastIndexOf = string.lastIndexOf(",");
		string = string.substring(0, lastIndexOf);
		String selectContent = "\tINSERT INTO " + tableName + " (\n" + string + ") \n\tVALUES \n" + chooseValueInserted(tableColumnMetaDataList);
		return selectContent;
	}

	/**
	 * 新增标签
	 * 
	 * @param tableInfo
	 * @return
	 */
	public static String updateTag(TableInfo tableInfo) {
		TableInfoSchema tableMetaData = convert(tableInfo);
		List<ColumnInfoSchema> tableColumnMetaDataList = convertFields(tableInfo);
		String tableName = tableMetaData.getTableName();
		StringJoiner updateColumns = new StringJoiner("\n\t\t", "\t\t", "");
		for (ColumnInfoSchema columnInfoSchema : tableColumnMetaDataList) {
			String columnName = columnInfoSchema.getColumnName();
			String columnCamelName = upperUnderscoreTolowerCame.convert(columnName);
			updateColumns.add(tableName + " = #{param." + columnCamelName + "}");
		}
		// 去掉末尾的逗号
		String string = updateColumns.toString();
		int lastIndexOf = string.lastIndexOf(",");
		if (lastIndexOf > 0) {
			string = string.substring(0, lastIndexOf);
		}
		String selectContent = "\tUPDATE " + tableName + " SET\n" + string;
		return selectContent;
	}
	
	
	/**
	 * 根据实际情况变化
	 * 
	 * @param column
	 * @param insertColumnAndValues
	 */
	private static String chooseValueInserted(List<ColumnInfoSchema> columns) {
		List<String> list = new ArrayList<>();
		// 保证顺序
		for (ColumnInfoSchema columnInfoSchema : columns) {
			String columnName = columnInfoSchema.getColumnName();
			String columnCamelName = upperUnderscoreTolowerCame.convert(columnName);
			String comment = columnInfoSchema.getColumnComment();
			//主键
			if (comment != null) {
				if (comment.contains("ID") || comment.contains("主键")) {
					list.add("uuid()");
					continue;
				}
			}
			if ("UPDATE_CONTROL_ID".equals(columnName)) {
				list.add("uuid()");
				continue;
			}
			if ("IS_ENABLE".equals(columnName)) {
				list.add("'1'");
			} else if (columnName.contains("DATE")) {
				list.add("now()");
			} else {
				list.add("#{param." + columnCamelName + "}");
			}
		}
		
		return "\t\t(" + String.join(",\n\t\t", list) + ")";
	}

	/**
	 * 适配层，将mybatis-plus生成器的表信息转为TableInfoSchema 以后要移除
	 * 
	 * @param tableInfo
	 * @return
	 */
	public static TableInfoSchema convert(TableInfo tableInfo) {
		TableInfoSchema tableInfoSchema = new TableInfoSchema();
		// 暂时只需要这几个字段
		tableInfoSchema.setTableName(tableInfo.getName());
		tableInfoSchema.setTableComment(tableInfo.getComment());
		return tableInfoSchema;
	}

	/**
	 * 适配层，将mybatis-plus生成器的表信息转为TableInfoSchema 以后要移除
	 * 
	 * @param tableInfo
	 * @return
	 */
	public static List<ColumnInfoSchema> convertFields(TableInfo tableInfo) {
		List<ColumnInfoSchema> columnInfoSchemaList = new ArrayList<>();
		for (TableField tableField : tableInfo.getFields()) {
			ColumnInfoSchema columnInfoSchema = new ColumnInfoSchema();
			columnInfoSchema.setColumnName(tableField.getName());
			columnInfoSchema.setTableName(tableInfo.getName());
			columnInfoSchemaList.add(columnInfoSchema);
		}
		return columnInfoSchemaList;
	}

	/**
	 * 大下划线转小驼峰
	 */
	private static final Converter<String, String> upperUnderscoreTolowerCame = CaseFormat.UPPER_UNDERSCORE
			.converterTo(CaseFormat.LOWER_CAMEL);

	/**
	 * 小驼峰转大下划线
	 */
	private static final Converter<String, String> lowerCameToUpperUnderscore = CaseFormat.LOWER_CAMEL
			.converterTo(CaseFormat.UPPER_UNDERSCORE);

}
