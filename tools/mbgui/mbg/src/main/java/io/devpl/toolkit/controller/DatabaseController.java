package io.devpl.toolkit.controller;

import io.devpl.toolkit.common.Result;
import io.devpl.toolkit.common.ResultGenerator;
import io.devpl.toolkit.dto.TableInfo;
import io.devpl.toolkit.dto.vo.ConnectionNameVO;
import io.devpl.toolkit.entity.ConnectionConfig;
import io.devpl.toolkit.service.ConnectionConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/db")
public class DatabaseController {

    @Resource
    private ConnectionConfigService connConfigService;

    @PostMapping("/conn/save")
    public Result addNewConnectionInfo(ConnectionConfig config) {
        boolean save = connConfigService.save(config);
        return ResultGenerator.genSuccessResult(save);
    }

    /**
     * 查询数据库所有的表
     *
     * @return
     */
    @GetMapping("/tables")
    public Result getAllTables(@RequestParam("connName") String connName, @RequestParam("dbName") String dbName) {
        List<TableInfo> tables = connConfigService.getAllTables(connName, dbName);
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
     * 查询连接下的数据库名称
     *
     * @return 获取连接下所有数据库名称
     */
    @GetMapping("/conn/dbnames")
    public Result getAllConnectionNames(@RequestParam("connectionName") String connectionName) {
        List<String> dbNames = connConfigService.getAllDbNamesByConnectionName(connectionName);
        return ResultGenerator.genSuccessResult(dbNames);
    }
}
