package code.magicode.generator.db.meta;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringJoiner;
import java.util.function.Predicate;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.google.common.base.CaseFormat;

import code.magicode.generator.db.meta.table.TableMetaData;
import code.magicode.generator.db.meta.table.TableMetaDataLoader;
import io.maker.base.ResourceLoader;

public class Database implements Serializable {

	private String databaseName;
	

}
