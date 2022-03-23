package code.example.jdbc.primary;

public class DataSourceTest {
	
	//驱动包内有DataSource的实现：MysqlDataSource
	//A JNDI DataSource for a Mysql JDBC connection
	
	public static void main(String[] args) {
		
		
	}
}
//作为 DriverManager 工具的替代项，DataSource 对象是获取连接的首选方法。
//DataSource接口由驱动程序供应商实现。共有三种类型的实现：
//1. 基本实现 - 生成标准的 Connection 对象
//2. 连接池实现 - 生成自动参与连接池的 Connection 对象。此实现与中间层连接池管理器一起使用。
//3. 分布式事务实现 - 生成一个 Connection 对象，该对象可用于分布式事务，大多数情况下总是参与连接池。
//		此实现与中间层事务管理器一起使用，大多数情况下总是与连接池管理器一起使用。
