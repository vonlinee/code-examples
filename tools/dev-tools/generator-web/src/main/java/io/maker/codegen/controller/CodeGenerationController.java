package io.maker.codegen.controller;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import io.maker.codegen.entity.ClassInfo;
import io.maker.codegen.entity.ParamInfo;
import io.maker.codegen.entity.ReturnT;
import io.maker.codegen.service.GeneratorService;
import io.maker.codegen.utils.MapUtil;
import io.maker.codegen.utils.TableParseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "代码生成服务", tags = { "代码生成服务" })
@RequestMapping(value = "/codegen", produces = { MediaType.APPLICATION_JSON_VALUE })
public class CodeGenerationController {

	private static final Logger log = LoggerFactory.getLogger(CodeGenerationController.class);

	@Autowired
	private GeneratorService generatorService;

	@PostMapping("/mybatis")
	public String mybatis() {
		return "";
	}

	@RequestMapping("/template/all")
	@ResponseBody
	public ReturnT getAllTemplates() throws Exception {
		String templates = generatorService.getTemplateConfig();
		return ReturnT.ok().put("templates", templates);
	}

	@PostMapping("/generate.do")
	@ApiOperation(value = "生成", notes = "生成")
	public ReturnT generateCode(
			@RequestHeader(name = "authorization", required = false) String authentication,
			@RequestBody(required = false) Map<String, Object> paramMap) throws Exception {
		log.info(JSON.toJSONString(paramMap));

		ParamInfo paramInfo = new ParamInfo();

		// 1.Parse Table Structure 表结构解析
		ClassInfo classInfo = null;
		String dataType = MapUtil.getString(paramMap, "dataType");
		if ("sql".equals(dataType) || dataType == null) {
			classInfo = TableParseUtil.processTableIntoClassInfo(paramInfo);
		} else if ("json".equals(dataType)) {
			// JSON模式：parse field from json string
			classInfo = TableParseUtil.processJsonToClassInfo(paramInfo);
			// INSERT SQL模式：parse field from insert sql
		} else if ("insert-sql".equals(dataType)) {
			classInfo = TableParseUtil.processInsertSqlToClassInfo(paramInfo);
			// 正则表达式模式（非完善版本）：parse sql by regex
		} else if ("sql-regex".equals(dataType)) {
			classInfo = TableParseUtil.processTableToClassInfoByRegex(paramInfo);
			// 默认模式：default parse sql by java
		}

		// 2.Set the params 设置表格参数

		paramInfo.getOptions().put("classInfo", classInfo);
		paramInfo.getOptions().put("tableName",
				classInfo == null ? System.currentTimeMillis() : classInfo.getTableName());

		// log the generated table and filed size记录解析了什么表，有多少个字段
		// log.info("generated table :{} , size
		// :{}",classInfo.getTableName(),(classInfo.getFieldList() == null ? "" :
		// classInfo.getFieldList().size()));

		// 3.generate the code by freemarker templates with parameters .
		// Freemarker根据参数和模板生成代码
		Map<String, String> result = generatorService.getResultByParams(paramInfo.getOptions());
		log.info("result {}", result);
		log.info("table:{} - time:{} ", MapUtil.getString(result, "tableName"), new Date());
		return ReturnT.ok().put("outputJson", result);
	}
}
