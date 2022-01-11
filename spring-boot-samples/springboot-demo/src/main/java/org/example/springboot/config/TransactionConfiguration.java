package org.example.springboot.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

//原文链接：https://blog.csdn.net/u011930054/article/details/106856750
//@Configuration
//public class TransactionConfiguration implements TransactionManagementConfigurer {
public class TransactionConfiguration {
//	// 注入基于第一个数据源生成的会话工厂
//	@Autowired
//	@Qualifier("sessionFactory")
//	private SessionFactory sessionFactory;
//
//	// 事务管理交给 HibernateTransactionManager
//	// 基于第一个数据源的事务管理
//	@Bean("transactionManager")
//	public HibernateTransactionManager getTransactionManager() {
//		HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
//		hibernateTransactionManager.setSessionFactory(sessionFactory);
//		return hibernateTransactionManager;
//	}
//
//	// 实现接口 TransactionManagementConfigurer 方法，其返回值代表默认使用的事务管理器
//	// 注意，此处返回的事务管理器就是@Transactional的默认值，如果不返回则需要指明@Transactional使用的事务管理器名称
//	// 多事务管理器时指明@Transactional(value="transactionManager"),则代表使用的那个事务
//	@Override
//	public PlatformTransactionManager annotationDrivenTransactionManager() {
//		return getTransactionManager();
//	}
//
//	// 注入基于第二个数据源生成的会话工厂
//	@Autowired
//	@Qualifier("secondSessionFactory")
//	private SessionFactory secondSessionFactory;
//
//	// 事务管理交给 HibernateTransactionManager
//	@Bean("queryTransactionManager")
//	public HibernateTransactionManager queryTransactionManager() {
//		HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
//		hibernateTransactionManager.setSessionFactory(secondSessionFactory);
//		return hibernateTransactionManager;
//	}
//
//	// // DAO中使用的组件实例，在service中使用,主从数据库时负责查询
//	// @Bean(name = "queryDaoBulider")
//	// public QueryDaoBuilder queryDaoBuilder() {
//	// QueryDaoBuilder daoBuilder = new QueryDaoBuilder();
//	// daoBuilder.setSessionFactory(secondSessionFactory);
//	// return daoBuilder;
//	// }
//	//
//	// // DAO中使用的组件实例，在service中使用
//	// @Bean(name = "daoBuilder")
//	// public DaoBuilder daoBuilder() {
//	// DaoBuilder daoBuilder = new DaoBuilder();
//	// daoBuilder.setSessionFactory(sessionFactory);
//	// return daoBuilder;
//	// }
}
