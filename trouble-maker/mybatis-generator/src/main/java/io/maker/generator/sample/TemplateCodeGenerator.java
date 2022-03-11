package io.maker.generator.sample;

import com.alibaba.druid.pool.DruidDataSource;
import io.maker.extension.poi.ExcelUtils;
import io.maker.generator.db.JdbcUtils;
import io.maker.generator.db.meta.resultset.MapListHandler;
import io.maker.generator.db.meta.table.TableMetaDataLoader;
import io.maker.generator.db.pool.DruidPool;

import java.util.*;

/**
 * 模板代码生成
 */
public class TemplateCodeGenerator {

    public static void main(String[] args) throws Exception {
        Properties properties = JdbcUtils.getLocalProperties();
        DruidDataSource dataSource = DruidPool.druidDataSource(properties);
        HashMap<String, List<String>> map = new HashMap<>();
        List<String> tableNames1 = new ArrayList<>();
        tableNames1.add("");
        map.put("mp", tableNames1);

        List<String> tableNames2 = new ArrayList<>();
        map.put("csc", tableNames2);

        List<Map<String, Object>> list = TableMetaDataLoader.loadInfomationSchema(dataSource, "mp", "t_sac_onetask_receive_object", new MapListHandler());
        ExcelUtils.writeExcelAndShow(list, "course");
    }
}
