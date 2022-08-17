package io.devpl.webui.controller.codegen;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 前端控制器
 * @author: xu zhihao
 * @create: 2019-06-14 10:36
 */
@RestController
public class IndexController {

    @GetMapping("")
    public String index() {
        return "index.html";
    }

}
