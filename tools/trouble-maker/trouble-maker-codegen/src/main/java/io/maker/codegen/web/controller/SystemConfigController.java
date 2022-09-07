package io.maker.codegen.web.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "配置管理服务", tags = "配置管理服务")
@RestController
@RequestMapping(value = "/codegen/system/config/", produces = { MediaType.APPLICATION_JSON_VALUE })
public class SystemConfigController {

}
