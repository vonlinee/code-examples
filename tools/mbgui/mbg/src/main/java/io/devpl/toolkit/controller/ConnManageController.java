package io.devpl.toolkit.controller;

import io.devpl.toolkit.common.Result;
import io.devpl.toolkit.common.Results;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 连接管理控制器
 */
@RestController
@RequestMapping("/api/manage/db")
public class ConnManageController {

    @PostMapping("/connect")
    public Result getAllTables() {

        return Results.of(null);
    }
}
