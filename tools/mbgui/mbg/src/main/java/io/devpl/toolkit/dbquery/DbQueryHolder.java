package io.devpl.toolkit.dbquery;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.IDbQuery;
import com.baomidou.mybatisplus.generator.config.querys.DbQueryRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

// @Component
public class DbQueryHolder {

    private final Map<DbType, IDbQuery> dbQueryMap = new EnumMap<>(DbType.class);

    public DbQueryHolder() {
        dbQueryMap.put(DbType.SQL_SERVER, new SqlServerQuery());
    }

    /**
     * 先查找本地是否有自定义的query实现，没有的话再去找mp内置的DbQuery实现
     */
    public IDbQuery getDbQuery(DbType dbType) {
        IDbQuery dbQuery = dbQueryMap.get(dbType);
        if (dbQuery != null) {
            return dbQuery;
        }

        DbQueryRegistry dbQueryRegistry = new DbQueryRegistry();
        // 默认 MYSQL
        dbQuery = Optional.ofNullable(dbQueryRegistry.getDbQuery(dbType))
                .orElseGet(() -> dbQueryRegistry.getDbQuery(DbType.MYSQL));

        return dbQuery;
    }
}
