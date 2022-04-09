package io.maker.generator.sample;

import java.util.Map;

import io.maker.base.utils.Maps;

public class LookupValueHelper {

	public static final String LOOKUP_VALUE_DB_NAME = "mp";
	public static final String LOOKUP_VALUE_TABLE_NAME = "T_prc_MDS_LOOKUP_VALUE";

	public static void main(String[] args) {

	}

	public void insertSql(String lookupTypeCode, String lookupTypeName, String lookupValueCode,
			String lookupValueName) {
		
		Map<String, String> builder = Maps.stringMapBuilder()
				.put("1", "")
				.put("2", "")
				.put("3", "")
				.build();
	}

}
