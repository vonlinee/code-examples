package io.maker.codegen.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.maker.codegen.utils.ValueUtil;
import io.swagger.annotations.Api;

@RestController
@Api(value = "导航页", tags = { "导航页" })
@RequestMapping(value = "/codegen", produces = { MediaType.APPLICATION_JSON_VALUE })
public class NavigationController {

	private static final Logger log = LoggerFactory.getLogger(NavigationController.class);

	@Autowired
	private ValueUtil valueUtil;

	@GetMapping("/")
	public ModelAndView defaultPage() {
		return new ModelAndView("index").addObject("value", valueUtil);
	}

	@GetMapping("/index.html")
	public ModelAndView indexPage() {
		return new ModelAndView("index").addObject("value", valueUtil);
	}

	@GetMapping("/main")
	public ModelAndView mainPage() {
		return new ModelAndView("main").addObject("value", valueUtil);
	}

}
