package code.fxutils.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
	}
	
	public static void main(String[] args) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File("jdbc.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean result = DbUtils.loadDriver(prop.getProperty("mysql.jdbc.driver"));
		if (result) {
			try {
				Connection connection = DriverManager.getConnection(prop.getProperty("mysql.jdbc.url"), prop.getProperty("mysql.jdbc.username"), prop.getProperty("mysql.jdbc.password"));
				System.out.println(connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
