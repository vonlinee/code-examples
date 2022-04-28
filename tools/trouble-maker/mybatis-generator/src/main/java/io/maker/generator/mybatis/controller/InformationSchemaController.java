package io.maker.generator.mybatis.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.maker.base.collection.ParamMap;
import io.maker.base.collection.ValueMap;
import io.maker.base.rest.PageResult;
import io.maker.base.utils.Validator;
import io.maker.generator.mybatis.entity.Columns;
import io.maker.generator.mybatis.mapper.InformationSchemaMapper;

@RestController
@RequestMapping(value = "/mysql/meta/informationschema", produces = { MediaType.APPLICATION_JSON_VALUE })
public class InformationSchemaController {

	@Autowired
	InformationSchemaMapper informationSchemaMapper;

	@PostMapping(value = "/queryColumns.do")
	public PageResult<Columns> sacBasisLogSaveInfo(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication,
			@RequestBody(required = false) ParamMap paramMap) {
		
		// List<Columns> list = informationSchemaMapper.selectColumns(paramMap);
		String tableName = Validator.notBlank(paramMap.getString("tableName"));
		
		List<Columns> list = informationSchemaMapper.selectColumns(paramMap);
		
		return PageResult.<Columns>builder()
					.data(list)
					.build();
	}
}
