package io.maker.codegen.mybatis.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/codegen", produces = MediaType.APPLICATION_JSON_VALUE)
public class CodeGenController {
}
