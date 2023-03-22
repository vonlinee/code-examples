package io.devpl.toolkit.controller;

import io.devpl.toolkit.utils.ProjectPathResolver;
import io.devpl.toolkit.common.Result;
import io.devpl.toolkit.common.Results;
import io.devpl.toolkit.service.AutoCompleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/ac")
public class AutoCompleteController {

    @Autowired
    private AutoCompleteService autoCompleteService;

    @Autowired
    private ProjectPathResolver projectPathResolver;

    @GetMapping("/mapperxml")
    public Result getAllMapperXmlNames(String mapperLocationPrefix, String searchKey) {
        Set<String> hits = autoCompleteService.searchXmlMapperName(mapperLocationPrefix, searchKey);
        return Results.of(hits);
    }

}
