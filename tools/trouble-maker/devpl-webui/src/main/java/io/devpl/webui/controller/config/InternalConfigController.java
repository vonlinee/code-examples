package io.devpl.webui.controller.config;

import io.maker.base.collection.ParamMap;
import io.maker.base.rest.ListResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/config", produces = MediaType.APPLICATION_JSON_VALUE)
public class InternalConfigController {

    @PostMapping("/listAll.do")
    public ListResult<?> listAll(@RequestBody ParamMap paramMap, @RequestHeader("Authorization") String token) {
        return ListResult.builder()
                .data(new ArrayList<>())
                .description(200, "正常")
                .build();
    }
}
