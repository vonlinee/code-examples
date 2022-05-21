package io.maker.codegen.mbp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.maker.base.collection.ParamMap;
import io.maker.base.rest.PageResult;
import io.maker.base.utils.Validator;
import io.maker.codegen.mbp.entity.Columns;
import io.maker.codegen.mbp.entity.Tables;
import io.maker.codegen.mbp.mapper.InformationSchemaMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "mysql-informationschema")
@RestController
@RequestMapping(value = "/mysql/meta/informationschema", produces = { MediaType.APPLICATION_JSON_VALUE })
public class InformationSchemaController {

	@Autowired
	private InformationSchemaMapper informationSchemaMapper;

	@ApiOperation(value = "修改用户", notes = "根据传入的用户信息修改用户")
	@PostMapping(value = "/queryColumns.do")
	public PageResult<Columns> queryColumns(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication,
			@RequestBody(required = false) ParamMap paramMap) {
		String tableName = Validator.notBlank(paramMap.getString("tableName"));
		ParamMap map = new ParamMap();
		map.put("tableName", tableName);
		List<Columns> list = informationSchemaMapper.selectColumns(paramMap);
		return PageResult.<Columns>builder()
					.data(list)
					.description(200, tableName)
					.build();
	}
	
	@ApiOperation(value = "修改用户", notes = "根据传入的用户信息修改用户")
	@PostMapping(value = "/queryTables.do")
	public PageResult<Tables> queryTables(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication,
			@RequestBody(required = false) ParamMap paramMap) {
		String tableName = Validator.notBlank(paramMap.getString("tableName"));
		ParamMap map = new ParamMap();
		map.put("tableName", tableName);
		List<Tables> list = informationSchemaMapper.selectTables(paramMap);
		return PageResult.<Tables>builder()
					.data(list)
					.description(200, "1")
					.build();
	}
}
