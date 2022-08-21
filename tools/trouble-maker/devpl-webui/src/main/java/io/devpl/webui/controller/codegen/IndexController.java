package io.devpl.webui.controller.codegen;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/index", produces = MediaType.APPLICATION_JSON_VALUE)
public class IndexController {

    @GetMapping("/index")
    public ModelAndView index(ModelAndView mv) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }
}
