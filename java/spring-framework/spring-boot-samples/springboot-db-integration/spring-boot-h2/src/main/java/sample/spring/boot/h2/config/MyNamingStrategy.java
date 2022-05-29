package sample.spring.boot.h2.config;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.cfg.NamingStrategy;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyNamingStrategy extends ImprovedNamingStrategy implements NamingStrategy {

	private String currentTablePrefix;

	@Override
	public String classToTableName(String className) {
		currentTablePrefix = className.substring(0, 3).toUpperCase() + "_";
		return "T" + currentTablePrefix + tableName(className);
	}

	@Override
	public String propertyToColumnName(String propertyName) {
		return currentTablePrefix + addUnderscores(propertyName).toUpperCase();
	}

	@Override
	public String columnName(String columnName) {
		return addUnderscores(columnName).toUpperCase();
	}

	@Override
	public String tableName(String tableName) {
		log.info(tableName);
		return addUnderscores(tableName).toUpperCase();
	}
}