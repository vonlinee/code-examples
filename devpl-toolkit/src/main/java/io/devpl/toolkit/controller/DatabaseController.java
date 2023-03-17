package io.devpl.toolkit.controller;

import io.devpl.toolkit.common.Result;
import io.devpl.toolkit.common.ResultGenerator;
import io.devpl.toolkit.dto.TableInfo;
import io.devpl.toolkit.dto.vo.ConnectionNameVO;
import io.devpl.toolkit.service.ConnectionConfigService;
import io.devpl.toolkit.service.DatabaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/db")
public class DatabaseController {

    @Resource
    private DatabaseService databaseService;

    @Resource
    private ConnectionConfigService connConfigService;

    /**
     * 查询数据库所有的表
     *
     * @return
     */
    @GetMapping("/tables")
    public Result getAllTables(@RequestParam("connName") String connName, @RequestParam("dbName") String dbName) {
        List<TableInfo> tables = databaseService.getTablesFromDb();
        return ResultGenerator.genSuccessResult(tables);
    }

    /**
     * 查询所有连接名称
     *
     * @return 获取所有连接名称
     */
    @GetMapping("/conn/info/names")
    public Result getAllConnectionNames() {
        final List<ConnectionNameVO> allConnectionNames = connConfigService.getAllConnectionNames();
        return ResultGenerator.genSuccessResult(allConnectionNames);
    }

    /**
     * 查询连接下的所有数据库表
     *
     * @return 获取所有连接名称
     */
    @GetMapping("/conn/dbnames")
    public Result getAllConnectionNames(@RequestParam("connectionName") String connectionName) {
        List<String> dbNames = connConfigService.getAllDbNamesByConnectionName(connectionName);
        return ResultGenerator.genSuccessResult(dbNames);
    }
}
