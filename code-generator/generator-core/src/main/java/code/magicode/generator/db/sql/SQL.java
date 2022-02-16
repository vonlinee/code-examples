package code.magicode.generator.db.sql;

public class SQL {
	// https://blog.csdn.net/weixin_41490593/article/details/94745433

	String sql = "SELECT * FROM `information_schema`.`TABLES` T \r\n"
			+ "	LEFT JOIN `information_schema`.`COLUMNS` C ON T.TABLE_NAME = C.TABLE_NAME \r\n"
			+ "WHERE T.TABLE_NAME = 't_usc_mdm_user_dlr'\r\n" + "ORDER BY\r\n" + "    T.TABLE_NAME, C.ORDINAL_POSITION";

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
