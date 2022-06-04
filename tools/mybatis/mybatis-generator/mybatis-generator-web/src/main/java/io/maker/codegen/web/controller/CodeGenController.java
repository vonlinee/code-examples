package io.maker.codegen.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/codegen")
public class CodeGenController {

    public String generate() {
        return "";
    }
}
