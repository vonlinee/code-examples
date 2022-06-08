package io.maker.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.calcite.adapter.jdbc.JdbcSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.commons.dbcp2.BasicDataSource;

public class CalciteTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

//创建Calcite Connection对象
        Class.forName("org.apache.calcite.jdbc.Driver");
        Properties info = new Properties();
        info.setProperty("lex", "JAVA");
        Connection connection =
                DriverManager.getConnection("jdbc:calcite:", info);
        CalciteConnection calciteConnection =
                connection.unwrap(CalciteConnection.class);
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
//创建Mysql的数据源schema
        Class.forName("com.mysql.jdbc.Driver");
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/db_mysql");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        Schema schema = JdbcSchema.create(rootSchema, "hr", dataSource,
                null, "name");
        rootSchema.add("hr", schema);
//执行查询
        Statement statement = calciteConnection.createStatement();
        
        String sql = "select * from t1";
        
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.close();
        statement.close();
        connection.close();
    }

    public static void sqlParse() {
    	
    }
}
