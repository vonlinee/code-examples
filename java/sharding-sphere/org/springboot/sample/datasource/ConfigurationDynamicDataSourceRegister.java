<!DOCTYPE html><html><head><meta http-equiv="content-type" content="text/html; charset=UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1 user-scalable=0"><meta name="apple-mobile-web-app-capable" content="yes"><meta name="apple-mobile-web-app-status-bar-style" content="black"><title>ConfigurationDynamicDataSourceRegister.java example</title><meta name="description" content="This class describes the usage of ConfigurationDynamicDataSourceRegister.java." /><link rel="canonical" href="https://www.javatips.net/api/spring-boot-sample-master/src/main/java/org/springboot/sample/datasource/ConfigurationDynamicDataSourceRegister.java" /><style>	@media screen and (max-width: 768px) { div#sidemenu { display: none !important; } div.col1 { width: 100% !important; } div.col2 { display: none !important; } div#code { margin-left: 1px !important; overflow: unset !important; } } </style><script src="/cdn-cgi/scripts/7d0fa10a/cloudflare-static/rocket-loader.min.js" data-cf-settings="157e08de8531f852200a282d-|49"></script><link rel="stylesheet" href="/themes/api/css/bootstrap.css" media="none" onload="if(media!='all')media='all'" /><noscript><link rel="stylesheet" href="/themes/api/css/bootstrap.css"></noscript><script src="/cdn-cgi/scripts/7d0fa10a/cloudflare-static/rocket-loader.min.js" data-cf-settings="157e08de8531f852200a282d-|49"></script><link rel="stylesheet" href="/themes/imby/css/style.css" media="none" onload="if(media!='all')media='all'" /><noscript><link rel="stylesheet" href="/themes/imby/css/style.css"></noscript><script src="/cdn-cgi/scripts/7d0fa10a/cloudflare-static/rocket-loader.min.js" data-cf-settings="157e08de8531f852200a282d-|49"></script><link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.8/themes/default/style.min.css" media="none" onload="if(media!='all')media='all'" /><noscript><link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.8/themes/default/style.min.css"></noscript><script type="157e08de8531f852200a282d-text/javascript" src='//services.vlitag.com/adv1/?q=47cad670105f3beb543c54e8471429c6' defer='' async=''></script><script type="157e08de8531f852200a282d-text/javascript">var vitag = vitag || {}; vitag.outStreamConfig = { enablePC:false, enableMobile:false, position: 'right'}; vitag.smartBannerConfig = { enablePC: false, enableMobile: false, disableHorizontalPosition: 'right left', disableVerticalPosition: 'top middle' };</script></head><script src="/cdn-cgi/scripts/7d0fa10a/cloudflare-static/rocket-loader.min.js" data-cf-settings="157e08de8531f852200a282d-|49"></script><body onload="prettyPrint() "><script async src="//m.servedby-buysellads.com/monetization.js" type="157e08de8531f852200a282d-text/javascript"></script><header id="sticky" class="main-header" style="top: 0px; position: fixed;"><div class="container"><div id="header"><div class="logo-wrap"><h2><a class='social javatips' title="Javatips.net" href="https://www.javatips.net"></a></h2></div><div class="main-navigation"><nav id="navigation" class="clearfix"><ul id="menu-menu" class="menu clearfix sf-js-enabled"><li id="home"><a href="/">Home</a></li><li id="java"><a href="/blog/category/java">Java</a></li><li id="jstl"><a href="/blog/category/jstl">JSTL</a></li><li id="struts"><a href="/blog/category/struts">Struts</a></li><li id="spring"><a href="/blog/category/spring">Spring</a></li><li id="hibernate"><a href="/blog/category/hibernate">Hibernate</a></li><li id="webservice"><a href="/blog/category/webservice">Webservice</a></li><li id="eclipse"><a href="/blog/category/eclipse">Eclipse</a></li><li id="api"><a href="/api/">API</a></li><li id="guest"><a href="/blog/page/guest">Guest Post</a></li></ul><a href="#" id="pull">Menu</a></nav></div></div></div></header><div id='view' style='display:block'><div id='sidemenu'><div class='ui-tabs'><ul class='ui-tabs-nav ui-helper-clearfix ui-widget-header'><li class='ui-state-active'><a class='ui-tabs-anchor'>Explorer</a></li></ul></div><div id='jstree' style='display:block'><ul><li>spring-boot-sample-master<ul><li>src<ul><li>main<ul><li>java<ul><li>org<ul><li>springboot<ul><li>sample<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/SpringBootSampleApplication.java">SpringBootSampleApplication.java</a></li><li>config<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/MShiroFilterFactoryBean.java">MShiroFilterFactoryBean.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/MyWebAppConfigurer.java">MyWebAppConfigurer.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/ResourceUrlProviderController.java">ResourceUrlProviderController.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/SchedulingConfig.java">SchedulingConfig.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/Shanhy.java">Shanhy.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/ShanhyA.java">ShanhyA.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/ShanhyB.java">ShanhyB.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/ShanhyDataSourceProperties.java">ShanhyDataSourceProperties.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/ShiroConfiguration.java">ShiroConfiguration.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/WebJarsController.java">WebJarsController.java</a></li><li>jsonp<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/jsonp/ContentNegotiationStrategyWrap.java">ContentNegotiationStrategyWrap.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/jsonp/ResponseBodyProcessor.java">ResponseBodyProcessor.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/jsonp/ResponseBodyWrapHandler.java">ResponseBodyWrapHandler.java</a></li></ul><li>mybatis<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/mybatis/MyBatisMapperScannerConfig.java">MyBatisMapperScannerConfig.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/mybatis/MyMapper.java">MyMapper.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/mybatis/MybatisAutoConfiguration.java">MybatisAutoConfiguration.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/mybatis/MybatisProperties.java">MybatisProperties.java</a></li></ul><li>viewresolver<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/viewresolver/JsonViewResolver.java">JsonViewResolver.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/viewresolver/PdfViewResolver.java">PdfViewResolver.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/viewresolver/XlsViewResolver.java">XlsViewResolver.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/viewresolver/XmlViewResolver.java">XmlViewResolver.java</a></li><li>view<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/viewresolver/view/AbstractPdfView.java">AbstractPdfView.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/viewresolver/view/MMappingJackson2JsonView.java">MMappingJackson2JsonView.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/viewresolver/view/PdfView.java">PdfView.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/viewresolver/view/XlsView.java">XlsView.java</a></li></ul></ul></ul><li>controller<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/FileDownloadController.java">FileDownloadController.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/HelloController.java">HelloController.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/ModelAttributeTestController1.java">ModelAttributeTestController1.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/ModelAttributeTestController2.java">ModelAttributeTestController2.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/ModelAttributeTestController3.java">ModelAttributeTestController3.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/ModelAttributeTestController4.java">ModelAttributeTestController4.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/PageController.java">PageController.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/ScoreController.java">ScoreController.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/ShiroController.java">ShiroController.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/StudentController.java">StudentController.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/ValidatorController.java">ValidatorController.java</a></li></ul><li>dao<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/dao/IScoreDao.java">IScoreDao.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/dao/IUserDao.java">IUserDao.java</a></li></ul><li>datasource<ul><li data-jstree='{"icon":"jstree-file","selected":true}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/datasource/ConfigurationDynamicDataSourceRegister.java"><b>ConfigurationDynamicDataSourceRegister.java</b></a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/datasource/DynamicDataSource.java">DynamicDataSource.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/datasource/DynamicDataSourceAspect.java">DynamicDataSourceAspect.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/datasource/DynamicDataSourceContextHolder.java">DynamicDataSourceContextHolder.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/datasource/DynamicDataSourceRegister.java">DynamicDataSourceRegister.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/datasource/TargetDataSource.java">TargetDataSource.java</a></li></ul><li>entity<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/entity/Permission.java">Permission.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/entity/Role.java">Role.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/entity/Score.java">Score.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/entity/Student.java">Student.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/entity/User.java">User.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/entity/ValidatorTest.java">ValidatorTest.java</a></li></ul><li>filter<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/filter/DruidStatFilter.java">DruidStatFilter.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/filter/MyFilter.java">MyFilter.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/filter/PageEhCacheFilter.java">PageEhCacheFilter.java</a></li></ul><li>interceptor<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/interceptor/JsonErrorMsgInterceptor.java">JsonErrorMsgInterceptor.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/interceptor/MyInterceptor1.java">MyInterceptor1.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/interceptor/MyInterceptor2.java">MyInterceptor2.java</a></li></ul><li>listener<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/listener/MyHttpSessionListener.java">MyHttpSessionListener.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/listener/MyServletContextListener.java">MyServletContextListener.java</a></li></ul><li>mapper<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/mapper/StudentMapper.java">StudentMapper.java</a></li></ul><li>runner<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/runner/MyStartupRunner1.java">MyStartupRunner1.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/runner/MyStartupRunner2.java">MyStartupRunner2.java</a></li></ul><li>security<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/security/MySessionDao.java">MySessionDao.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/security/MyShiroRealm.java">MyShiroRealm.java</a></li></ul><li>service<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/service/HelloWorldAspect.java">HelloWorldAspect.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/service/HelloWorldService.java">HelloWorldService.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/service/IStudentService.java">IStudentService.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/service/StudentService.java">StudentService.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/service/UserService.java">UserService.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/service/ValidatorTestService.java">ValidatorTestService.java</a></li></ul><li>servlet<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/servlet/CometServlet.java">CometServlet.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/servlet/DruidStatViewServlet.java">DruidStatViewServlet.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/servlet/MyServlet.java">MyServlet.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/servlet/MyServlet2.java">MyServlet2.java</a></li></ul><li>util<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/util/BeanValidatorUtils.java">BeanValidatorUtils.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/util/ErrorHolder.java">ErrorHolder.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/util/HttpClientUtils.java">HttpClientUtils.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/util/RestClient.java">RestClient.java</a></li></ul></ul></ul></ul></ul></ul><li>test<ul><li>java<ul><li>org<ul><li>springboot<ul><li>sample<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/test/java/org/springboot/sample/HelloControllerTest.java">HelloControllerTest.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/test/java/org/springboot/sample/ParameterTest.java">ParameterTest.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/test/java/org/springboot/sample/SpringBootSampleApplicationTests.java">SpringBootSampleApplicationTests.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/test/java/org/springboot/sample/StudentControllerTest.java">StudentControllerTest.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/test/java/org/springboot/sample/SuiteTest.java">SuiteTest.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/test/java/org/springboot/sample/Test.java">Test.java</a></li></ul></ul></ul></ul></ul></ul></ul></li></ul></div></div><div id='code'><br /><br /><br /><div class='adsbyvli' data-ad-slot='vi_14173526'></div> <script type="157e08de8531f852200a282d-text/javascript">(vitag.Init = window.vitag.Init || []).push(function () { viAPItag.display('vi_14173526') })</script><pre class='prettyprint' style='background-color:#FFFFFF;font-size:14px;padding: 0px;border:none'><code><div class="bottom"><div class="col1">package org.springboot.sample.datasource;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 注册动态数据源
 *
 * @author   单红宇(365384722)
 * @myblog  http://blog.csdn.net/catoop/
 * @create    2016年1月23日
 */
//@Configuration
@Deprecated// 改用DynamicDataSourceRegister类
public class ConfigurationDynamicDataSourceRegister
		implements ApplicationContextAware, ApplicationListener&lt;ContextRefreshedEvent>, EnvironmentAware {

	// 如配置文件中未指定数据源类型，使用该默认值
	private static final Object DATASOURCE_TYPE_DEFAULT = "org.apache.tomcat.jdbc.pool.DataSource";
	// private static final Object DATASOURCE_TYPE_DEFAULT = "com.zaxxer.hikari.HikariDataSource";

	private ApplicationContext applicationContext;
	
	// 存放DataSource配置的集合，模型&lt;dataSourceName,dataSourceMap>
	private Map&lt;String, Map&lt;String, Object>> dataSourceInfoMap = new HashMap&lt;>();

	@Resource(name="dataSource")
	private javax.sql.DataSource dataSource;
	
	/**
	 * 获得ApplicationContext
	 * 
	 * @param event
	 * @author SHANHY
	 * @create 2016年1月23日
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * 监听容器刷新事件 OR Start Event
	 *
	 * @param event
	 * @author SHANHY
	 * @create 2016年1月23日
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		registerDynamicDataSource();
	}

	/**
	 * 注册动态数据源
	 *
	 * @author SHANHY
	 * @create 2016年1月23日
	 */
	private void registerDynamicDataSource() {
		// 把数据源bean注册到容器中
		addBeanToApplication(dataSourceInfoMap);  
	}

	/**
	 * 功能说明：根据DataSource创建bean并注册到容器中
	 * 
	 * @param acf
	 * @param customDataSourceMap
	 */
	private void addBeanToApplication(Map&lt;String, Map&lt;String, Object>> customDataSourceMap) {
		DefaultListableBeanFactory acf = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
		BeanDefinitionBuilder bdb;

		Map&lt;Object, Object> targetDataSources = new HashMap&lt;Object, Object>();

		// 将默认数据源放入 targetDataSources map中
		targetDataSources.put("dataSource", dataSource);
		DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");

		// 根据数据源得到数据，动态创建数据源bean 并将bean注册到applicationContext中去
		Map&lt;String, Object> dsMap = null;
		for (Entry&lt;String, Map&lt;String, Object>> entry : customDataSourceMap.entrySet()) {
			// bean ID
			String key = entry.getKey();
			dsMap = entry.getValue();
			Object type = dsMap.get("type");
			if (type == null)
				type = DATASOURCE_TYPE_DEFAULT;// 默认DataSource
			
			// 创建bean
			bdb = BeanDefinitionBuilder.rootBeanDefinition(type.toString());
			bdb.getBeanDefinition().setAttribute("id", key);
			bdb.addPropertyValue("driverClassName", dsMap.get("driver-class-name"));
			bdb.addPropertyValue("url", dsMap.get("url"));
			bdb.addPropertyValue("username", dsMap.get("username"));
			bdb.addPropertyValue("password", dsMap.get("password"));
			// 注册bean
			acf.registerBeanDefinition(key, bdb.getBeanDefinition());
			// 放入map中，注意一定是刚才创建bean对象
			targetDataSources.put(key, applicationContext.getBean(key));
			DynamicDataSourceContextHolder.dataSourceIds.add(key);
		}
		
		bdb = BeanDefinitionBuilder.rootBeanDefinition(DynamicDataSource.class);
		bdb.getBeanDefinition().setAttribute("id", "dynamicDataSource");

		bdb.addPropertyValue("defaultTargetDataSource", dataSource);
		bdb.addPropertyValue("targetDataSources", targetDataSources);

		// 注册Bean
		acf.registerBeanDefinition("dynamicDataSource", bdb.getBeanDefinition());
//		// 必须重新初始化 AbstractRoutingDataSource 中的 resolvedDataSources，动态切换才会生效
		DynamicDataSource dynamicDataSource = (DynamicDataSource)applicationContext.getBean("dynamicDataSource");
        dynamicDataSource.afterPropertiesSet();
        
        autowiredDynamicDataSource(dynamicDataSource);
	}
	
	/**
	 * 注入动态数据源到jdbcTemplate、sqlSessoinTemplate
	 *
	 * @author SHANHY
	 * @create  2016年1月24日
	 */
	private void autowiredDynamicDataSource(DynamicDataSource dynamicDataSource){
        //>>>>>>>>>>>>>>>>> JdbcTemplate &lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;//	
		// 读取jdbcTemplate，设置dynamicDataSource为它的dataSource
		JdbcTemplate jdbcTemplate = (JdbcTemplate)applicationContext.getBean(JdbcTemplate.class);
		jdbcTemplate.setDataSource(dynamicDataSource);
		jdbcTemplate.afterPropertiesSet();
		//>>>>>>>>>>>>>>>>> SqlSessionTemplate &lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt;//
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory)applicationContext.getBean(SqlSessionFactory.class);
		org.apache.ibatis.mapping.Environment myBatisEnvironment = sqlSessionFactory.getConfiguration().getEnvironment();
		sqlSessionFactory.getConfiguration().setEnvironment(new org.apache.ibatis.mapping.Environment(myBatisEnvironment.getId(), myBatisEnvironment.getTransactionFactory(), dynamicDataSource));
	}

	/**
	 * 加载多数据源配置
	 */
	@Override
	public void setEnvironment(Environment env) {
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "custom.datasource.");
		String dsPrefixs = propertyResolver.getProperty("names");
		for (String dsPrefix : dsPrefixs.split(",")) {// 多个数据源
			Map&lt;String, Object> dsMap = propertyResolver.getSubProperties(dsPrefix + ".");
			dataSourceInfoMap.put(dsPrefix, dsMap);
		}
	}

}
</div><div class="col2"><br /><br /><div id="sticky-parent"><div class='adsbyvli' data-ad-slot='vi_14173525'></div> <script type="157e08de8531f852200a282d-text/javascript">(vitag.Init = window.vitag.Init || []).push(function () { viAPItag.display('vi_14173525') })</script><div id='carbon-block'></div><script type="157e08de8531f852200a282d-text/javascript">try{fetch(new Request('https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js', { method: 'HEAD', mode: 'no-cors' })).then(function(response) {return true;}).catch(function(e) {var carbonScript = document.createElement('script'); carbonScript.src = '//cdn.carbonads.com/carbon.js?serve=CE7D62JN&placement=wwwjavatipsnet';carbonScript.id = '_carbonads_js';document.getElementById('carbon-block').appendChild(carbonScript);});} catch (error) {console.log(error);}</script><br /><br /><div class='adsbyvli' data-ad-slot='vi_141733456'></div> <script type="157e08de8531f852200a282d-text/javascript">(vitag.Init = window.vitag.Init || []).push(function () { viAPItag.display('vi_141733456') })</script></div></div></div></code></pre><div class='adsbyvli' data-ad-slot='vi_141733718'></div> <script type="157e08de8531f852200a282d-text/javascript">(vitag.Init = window.vitag.Init || []).push(function () { viAPItag.display('vi_141733718') })</script><br /><br /></div></div><script defer type="157e08de8531f852200a282d-text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script><script defer type="157e08de8531f852200a282d-text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.8/jstree.min.js"></script><script defer type="157e08de8531f852200a282d-text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/prettify/r298/prettify.js"></script><script defer type="157e08de8531f852200a282d-text/javascript" src='https://cdnjs.cloudflare.com/ajax/libs/jquery.sticky/1.0.4/jquery.sticky.min.js'></script><script type="157e08de8531f852200a282d-text/javascript">window.addEventListener("load",function(){
$(function(){$("#jstree").jstree();$("#jstree").on("click",".jstree-anchor",function(b){$(this).jstree(true).toggle_node(b.target)}).jstree();$("#jstree").bind("select_node.jstree",function(f,d){var e=d.node.a_attr.href;window.location=e})});jQuery(function(){var b=jQuery("#pull");menu=jQuery("nav>ul");menuHeight=menu.height();jQuery(b).on("click",function(a){a.preventDefault();menu.slideToggle()})});$("#code").scroll(function(){if($(this).scrollTop()>0){$("._bsa_flexbar").fadeOut()}else{$("._bsa_flexbar").fadeIn()}});(function(){if(typeof _bsa!=="undefined"&&_bsa){_bsa.init("stickybox","CKYICK3U","placement:wwwjavatipsnet")}})();(function(){if(typeof _bsa!=="undefined"&&_bsa){_bsa.init("flexbar","CKYICK3Y","placement:wwwjavatipsnet")}})();$("#sticky-parent").sticky({topSpacing:0,bottomSpacing:600});$("#sticky-parent").css({position:"fixed","margin-left":"50px"});});</script><script type="157e08de8531f852200a282d-text/javascript"> var _gaq = _gaq || []; _gaq.push(['_setAccount', 'UA-22006649-1']); _gaq.push(['_trackPageview']);  (function() { var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true; ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js'; var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s); })(); </script><script src="/cdn-cgi/scripts/7d0fa10a/cloudflare-static/rocket-loader.min.js" data-cf-settings="157e08de8531f852200a282d-|49" defer=""></script></body></html>