package code.magicode.generator.db.meta;

import java.io.Serializable;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import code.magicode.generator.db.meta.table.TableMetaData;
import code.magicode.generator.db.meta.table.TableMetaDataLoader;
import io.maker.base.ResourceLoader;

public class Database implements Serializable {

    private String databaseName;
    
    public static void main(String[] args) throws Exception {
//    	Properties properties = ResourceLoader.loadProperties("druid.properties");
//    	DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
//    	TableMetaData tableMetaData = TableMetaDataLoader.load(dataSource, "course", "MySQL");
//    	System.out.println(tableMetaData);
//    	
    	System.out.println(Integer.class == Integer.TYPE);
	}
}
