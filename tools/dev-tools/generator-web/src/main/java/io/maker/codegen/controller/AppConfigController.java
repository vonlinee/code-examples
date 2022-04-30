package io.maker.codegen.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@Api(value = "配置管理服务", tags = { "配置管理服务" })
@RequestMapping(value = "/codegen/appconfig", produces = { MediaType.APPLICATION_JSON_VALUE })
public class AppConfigController {

	
	
	
	
	
}
