package io.maker.codegen.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.maker.base.rest.OptResult;
import io.maker.codegen.web.service.external.IFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "配置管理服务", tags = "配置管理服务")
@RestController
@RequestMapping(value = "/codegen/system/config/", produces = { MediaType.APPLICATION_JSON_VALUE })
public class SystemConfigController {

	@Autowired
	IFeignClient feignClient;
	
	@ApiOperation(value = "全部配置", notes = "全部配置")
	@PostMapping(value = "/queryAll.do")
	public OptResult<Map<String, Object>> queryAll(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication,
			@RequestBody(required = false) Map<String, Object> dateInfo) {
		return OptResult.<Map<String, Object>>builder()
				.description(200, "操作成功")
				.build();
	}
}
