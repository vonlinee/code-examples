package io.maker.codegen.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "应用配置元数据服务", tags = { "应用配置元数据服务" })
@RequestMapping(value = "/codegen/appmeta", produces = { MediaType.APPLICATION_JSON_VALUE })
public class AppMetadataController {

	@ApiOperation(value = "商机外呼配置查询", notes = "商机外呼配置查询")
	@RequestMapping(value = "/outboundconfigquery.do", method = RequestMethod.POST)
	public Map<String, Object> queryListOutboundConfig(
			@RequestHeader(name = "authorization", required = false) String authentication,
			@RequestBody(required = false) Map<String, Object> dataInfo) {
		return new HashMap<>();
	}
}
