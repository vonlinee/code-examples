<!DOCTYPE html><html><head><meta http-equiv="content-type" content="text/html; charset=UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1 user-scalable=0"><meta name="apple-mobile-web-app-capable" content="yes"><meta name="apple-mobile-web-app-status-bar-style" content="black"><title>DynamicDataSourceRegister.java example</title><meta name="description" content="This class describes the usage of DynamicDataSourceRegister.java." /><link rel="canonical" href="https://www.javatips.net/api/spring-boot-sample-master/src/main/java/org/springboot/sample/datasource/DynamicDataSourceRegister.java" /><style>	@media screen and (max-width: 768px) { div#sidemenu { display: none !important; } div.col1 { width: 100% !important; } div.col2 { display: none !important; } div#code { margin-left: 1px !important; overflow: unset !important; } } </style><script src="/cdn-cgi/scripts/7d0fa10a/cloudflare-static/rocket-loader.min.js" data-cf-settings="1315743a9277122aa5d29ca1-|49"></script><link rel="stylesheet" href="/themes/api/css/bootstrap.css" media="none" onload="if(media!='all')media='all'" /><noscript><link rel="stylesheet" href="/themes/api/css/bootstrap.css"></noscript><script src="/cdn-cgi/scripts/7d0fa10a/cloudflare-static/rocket-loader.min.js" data-cf-settings="1315743a9277122aa5d29ca1-|49"></script><link rel="stylesheet" href="/themes/imby/css/style.css" media="none" onload="if(media!='all')media='all'" /><noscript><link rel="stylesheet" href="/themes/imby/css/style.css"></noscript><script src="/cdn-cgi/scripts/7d0fa10a/cloudflare-static/rocket-loader.min.js" data-cf-settings="1315743a9277122aa5d29ca1-|49"></script><link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.8/themes/default/style.min.css" media="none" onload="if(media!='all')media='all'" /><noscript><link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.8/themes/default/style.min.css"></noscript><script type="1315743a9277122aa5d29ca1-text/javascript" src='//services.vlitag.com/adv1/?q=47cad670105f3beb543c54e8471429c6' defer='' async=''></script><script type="1315743a9277122aa5d29ca1-text/javascript">var vitag = vitag || {}; vitag.outStreamConfig = { enablePC:false, enableMobile:false, position: 'right'}; vitag.smartBannerConfig = { enablePC: false, enableMobile: false, disableHorizontalPosition: 'right left', disableVerticalPosition: 'top middle' };</script></head><script src="/cdn-cgi/scripts/7d0fa10a/cloudflare-static/rocket-loader.min.js" data-cf-settings="1315743a9277122aa5d29ca1-|49"></script><body onload="prettyPrint() "><script async src="//m.servedby-buysellads.com/monetization.js" type="1315743a9277122aa5d29ca1-text/javascript"></script><header id="sticky" class="main-header" style="top: 0px; position: fixed;"><div class="container"><div id="header"><div class="logo-wrap"><h2><a class='social javatips' title="Javatips.net" href="https://www.javatips.net"></a></h2></div><div class="main-navigation"><nav id="navigation" class="clearfix"><ul id="menu-menu" class="menu clearfix sf-js-enabled"><li id="home"><a href="/">Home</a></li><li id="java"><a href="/blog/category/java">Java</a></li><li id="jstl"><a href="/blog/category/jstl">JSTL</a></li><li id="struts"><a href="/blog/category/struts">Struts</a></li><li id="spring"><a href="/blog/category/spring">Spring</a></li><li id="hibernate"><a href="/blog/category/hibernate">Hibernate</a></li><li id="webservice"><a href="/blog/category/webservice">Webservice</a></li><li id="eclipse"><a href="/blog/category/eclipse">Eclipse</a></li><li id="api"><a href="/api/">API</a></li><li id="guest"><a href="/blog/page/guest">Guest Post</a></li></ul><a href="#" id="pull">Menu</a></nav></div></div></div></header><div id='view' style='display:block'><div id='sidemenu'><div class='ui-tabs'><ul class='ui-tabs-nav ui-helper-clearfix ui-widget-header'><li class='ui-state-active'><a class='ui-tabs-anchor'>Explorer</a></li></ul></div><div id='jstree' style='display:block'><ul><li>spring-boot-sample-master<ul><li>src<ul><li>main<ul><li>java<ul><li>org<ul><li>springboot<ul><li>sample<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/SpringBootSampleApplication.java">SpringBootSampleApplication.java</a></li><li>config<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/MShiroFilterFactoryBean.java">MShiroFilterFactoryBean.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/MyWebAppConfigurer.java">MyWebAppConfigurer.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/ResourceUrlProviderController.java">ResourceUrlProviderController.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/SchedulingConfig.java">SchedulingConfig.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/Shanhy.java">Shanhy.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/ShanhyA.java">ShanhyA.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/ShanhyB.java">ShanhyB.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/ShanhyDataSourceProperties.java">ShanhyDataSourceProperties.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/ShiroConfiguration.java">ShiroConfiguration.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/WebJarsController.java">WebJarsController.java</a></li><li>jsonp<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/jsonp/ContentNegotiationStrategyWrap.java">ContentNegotiationStrategyWrap.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/jsonp/ResponseBodyProcessor.java">ResponseBodyProcessor.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/jsonp/ResponseBodyWrapHandler.java">ResponseBodyWrapHandler.java</a></li></ul><li>mybatis<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/mybatis/MyBatisMapperScannerConfig.java">MyBatisMapperScannerConfig.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/mybatis/MyMapper.java">MyMapper.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/mybatis/MybatisAutoConfiguration.java">MybatisAutoConfiguration.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/mybatis/MybatisProperties.java">MybatisProperties.java</a></li></ul><li>viewresolver<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/viewresolver/JsonViewResolver.java">JsonViewResolver.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/viewresolver/PdfViewResolver.java">PdfViewResolver.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/viewresolver/XlsViewResolver.java">XlsViewResolver.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/viewresolver/XmlViewResolver.java">XmlViewResolver.java</a></li><li>view<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/viewresolver/view/AbstractPdfView.java">AbstractPdfView.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/viewresolver/view/MMappingJackson2JsonView.java">MMappingJackson2JsonView.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/viewresolver/view/PdfView.java">PdfView.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/config/viewresolver/view/XlsView.java">XlsView.java</a></li></ul></ul></ul><li>controller<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/FileDownloadController.java">FileDownloadController.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/HelloController.java">HelloController.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/ModelAttributeTestController1.java">ModelAttributeTestController1.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/ModelAttributeTestController2.java">ModelAttributeTestController2.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/ModelAttributeTestController3.java">ModelAttributeTestController3.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/ModelAttributeTestController4.java">ModelAttributeTestController4.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/PageController.java">PageController.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/ScoreController.java">ScoreController.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/ShiroController.java">ShiroController.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/StudentController.java">StudentController.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/controller/ValidatorController.java">ValidatorController.java</a></li></ul><li>dao<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/dao/IScoreDao.java">IScoreDao.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/dao/IUserDao.java">IUserDao.java</a></li></ul><li>datasource<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/datasource/ConfigurationDynamicDataSourceRegister.java">ConfigurationDynamicDataSourceRegister.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/datasource/DynamicDataSource.java">DynamicDataSource.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/datasource/DynamicDataSourceAspect.java">DynamicDataSourceAspect.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/datasource/DynamicDataSourceContextHolder.java">DynamicDataSourceContextHolder.java</a></li><li data-jstree='{"icon":"jstree-file","selected":true}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/datasource/DynamicDataSourceRegister.java"><b>DynamicDataSourceRegister.java</b></a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/datasource/TargetDataSource.java">TargetDataSource.java</a></li></ul><li>entity<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/entity/Permission.java">Permission.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/entity/Role.java">Role.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/entity/Score.java">Score.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/entity/Student.java">Student.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/entity/User.java">User.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/entity/ValidatorTest.java">ValidatorTest.java</a></li></ul><li>filter<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/filter/DruidStatFilter.java">DruidStatFilter.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/filter/MyFilter.java">MyFilter.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/filter/PageEhCacheFilter.java">PageEhCacheFilter.java</a></li></ul><li>interceptor<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/interceptor/JsonErrorMsgInterceptor.java">JsonErrorMsgInterceptor.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/interceptor/MyInterceptor1.java">MyInterceptor1.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/interceptor/MyInterceptor2.java">MyInterceptor2.java</a></li></ul><li>listener<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/listener/MyHttpSessionListener.java">MyHttpSessionListener.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/listener/MyServletContextListener.java">MyServletContextListener.java</a></li></ul><li>mapper<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/mapper/StudentMapper.java">StudentMapper.java</a></li></ul><li>runner<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/runner/MyStartupRunner1.java">MyStartupRunner1.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/runner/MyStartupRunner2.java">MyStartupRunner2.java</a></li></ul><li>security<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/security/MySessionDao.java">MySessionDao.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/security/MyShiroRealm.java">MyShiroRealm.java</a></li></ul><li>service<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/service/HelloWorldAspect.java">HelloWorldAspect.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/service/HelloWorldService.java">HelloWorldService.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/service/IStudentService.java">IStudentService.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/service/StudentService.java">StudentService.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/service/UserService.java">UserService.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/service/ValidatorTestService.java">ValidatorTestService.java</a></li></ul><li>servlet<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/servlet/CometServlet.java">CometServlet.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/servlet/DruidStatViewServlet.java">DruidStatViewServlet.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/servlet/MyServlet.java">MyServlet.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/servlet/MyServlet2.java">MyServlet2.java</a></li></ul><li>util<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/util/BeanValidatorUtils.java">BeanValidatorUtils.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/util/ErrorHolder.java">ErrorHolder.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/util/HttpClientUtils.java">HttpClientUtils.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/main/java/org/springboot/sample/util/RestClient.java">RestClient.java</a></li></ul></ul></ul></ul></ul></ul><li>test<ul><li>java<ul><li>org<ul><li>springboot<ul><li>sample<ul><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/test/java/org/springboot/sample/HelloControllerTest.java">HelloControllerTest.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/test/java/org/springboot/sample/ParameterTest.java">ParameterTest.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/test/java/org/springboot/sample/SpringBootSampleApplicationTests.java">SpringBootSampleApplicationTests.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/test/java/org/springboot/sample/StudentControllerTest.java">StudentControllerTest.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/test/java/org/springboot/sample/SuiteTest.java">SuiteTest.java</a></li><li data-jstree='{"icon":"jstree-file"}'><a href="/api/spring-boot-sample-master/src/test/java/org/springboot/sample/Test.java">Test.java</a></li></ul></ul></ul></ul></ul></ul></ul></li></ul></div></div><div id='code'><br /><br /><br /><div class='adsbyvli' data-ad-slot='vi_14173526'></div> <script type="1315743a9277122aa5d29ca1-text/javascript">(vitag.Init = window.vitag.Init || []).push(function () { viAPItag.display('vi_14173526') })</script><pre class='prettyprint' style='background-color:#FFFFFF;font-size:14px;padding: 0px;border:none'><code><div class="bottom"><div class="col1">package org.springboot.sample.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 动态数据源注册&lt;br/>
 * 启动动态数据源请在启动类中（如SpringBootSampleApplication）
 * 添加 @Import(DynamicDataSourceRegister.class)
 *
 * @author 单红宇(365384722)
 * @myblog http://blog.csdn.net/catoop/
 * @create 2016年1月24日
 */
public class DynamicDataSourceRegister
		implements ImportBeanDefinitionRegistrar, EnvironmentAware {

	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);

	private ConversionService conversionService = new DefaultConversionService(); 
	private PropertyValues dataSourcePropertyValues;
	
	// 如配置文件中未指定数据源类型，使用该默认值
	private static final Object DATASOURCE_TYPE_DEFAULT = "org.apache.tomcat.jdbc.pool.DataSource";
	// private static final Object DATASOURCE_TYPE_DEFAULT =
	// "com.zaxxer.hikari.HikariDataSource";

	// 数据源
	private DataSource defaultDataSource;
	private Map&lt;String, DataSource> customDataSources = new HashMap&lt;>();

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Map&lt;Object, Object> targetDataSources = new HashMap&lt;Object, Object>();
		// 将主数据源添加到更多数据源中
		targetDataSources.put("dataSource", defaultDataSource);
		DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
		// 添加更多数据源
		targetDataSources.putAll(customDataSources);
		for (String key : customDataSources.keySet()) {
			DynamicDataSourceContextHolder.dataSourceIds.add(key);
		}

		// 创建DynamicDataSource
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(DynamicDataSource.class);
		beanDefinition.setSynthetic(true);
		MutablePropertyValues mpv = beanDefinition.getPropertyValues();
		mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
		mpv.addPropertyValue("targetDataSources", targetDataSources);
		registry.registerBeanDefinition("dataSource", beanDefinition);

		logger.info("Dynamic DataSource Registry");
	}

	/**
	 * 创建DataSource
	 *
	 * @param type
	 * @param driverClassName
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 * @author SHANHY
	 * @create 2016年1月24日
	 */
	@SuppressWarnings("unchecked")
	public DataSource buildDataSource(Map&lt;String, Object> dsMap) {
		try {
			Object type = dsMap.get("type");
			if (type == null)
				type = DATASOURCE_TYPE_DEFAULT;// 默认DataSource

			Class&lt;? extends DataSource> dataSourceType;
			dataSourceType = (Class&lt;? extends DataSource>) Class.forName((String) type);

			String driverClassName = dsMap.get("driver-class-name").toString();
			String url = dsMap.get("url").toString();
			String username = dsMap.get("username").toString();
			String password = dsMap.get("password").toString();

			DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
					.username(username).password(password).type(dataSourceType);
			return factory.build();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加载多数据源配置
	 */
	@Override
	public void setEnvironment(Environment env) {
		initDefaultDataSource(env);
		initCustomDataSources(env);
	}

	/**
	 * 初始化主数据源
	 *
	 * @author SHANHY
	 * @create 2016年1月24日
	 */
	private void initDefaultDataSource(Environment env) {
		// 读取主数据源
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
		Map&lt;String, Object> dsMap = new HashMap&lt;>();
		dsMap.put("type", propertyResolver.getProperty("type"));
		dsMap.put("driver-class-name", propertyResolver.getProperty("driver-class-name"));
		dsMap.put("url", propertyResolver.getProperty("url"));
		dsMap.put("username", propertyResolver.getProperty("username"));
		dsMap.put("password", propertyResolver.getProperty("password"));

		defaultDataSource = buildDataSource(dsMap);
		
		dataBinder(defaultDataSource, env);
	}
	
	/**
	 * 为DataSource绑定更多数据
	 *
	 * @param dataSource
	 * @param env
	 * @author SHANHY
	 * @create  2016年1月25日
	 */
	private void dataBinder(DataSource dataSource, Environment env){
		RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
		//dataBinder.setValidator(new LocalValidatorFactory().run(this.applicationContext));
		dataBinder.setConversionService(conversionService);
		dataBinder.setIgnoreNestedProperties(false);//false
		dataBinder.setIgnoreInvalidFields(false);//false
		dataBinder.setIgnoreUnknownFields(true);//true
		if(dataSourcePropertyValues == null){
			Map&lt;String, Object> rpr = new RelaxedPropertyResolver(env, "spring.datasource").getSubProperties(".");
			Map&lt;String, Object> values = new HashMap&lt;>(rpr);
			// 排除已经设置的属性
			values.remove("type");
			values.remove("driver-class-name");
			values.remove("url");
			values.remove("username");
			values.remove("password");
			dataSourcePropertyValues = new MutablePropertyValues(values);
		}
		dataBinder.bind(dataSourcePropertyValues);
	}

	/**
	 * 初始化更多数据源
	 *
	 * @author SHANHY
	 * @create 2016年1月24日
	 */
	private void initCustomDataSources(Environment env) {
		// 读取配置文件获取更多数据源，也可以通过defaultDataSource读取数据库获取更多数据源
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "custom.datasource.");
		String dsPrefixs = propertyResolver.getProperty("names");
		for (String dsPrefix : dsPrefixs.split(",")) {// 多个数据源
			Map&lt;String, Object> dsMap = propertyResolver.getSubProperties(dsPrefix + ".");
			DataSource ds = buildDataSource(dsMap);
			customDataSources.put(dsPrefix, ds);
			dataBinder(ds, env);
		}
	}

}
</div><div class="col2"><br /><br /><div id="sticky-parent"><div class='adsbyvli' data-ad-slot='vi_14173525'></div> <script type="1315743a9277122aa5d29ca1-text/javascript">(vitag.Init = window.vitag.Init || []).push(function () { viAPItag.display('vi_14173525') })</script><div id='carbon-block'></div><script type="1315743a9277122aa5d29ca1-text/javascript">try{fetch(new Request('https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js', { method: 'HEAD', mode: 'no-cors' })).then(function(response) {return true;}).catch(function(e) {var carbonScript = document.createElement('script'); carbonScript.src = '//cdn.carbonads.com/carbon.js?serve=CE7D62JN&placement=wwwjavatipsnet';carbonScript.id = '_carbonads_js';document.getElementById('carbon-block').appendChild(carbonScript);});} catch (error) {console.log(error);}</script><br /><br /><div class='adsbyvli' data-ad-slot='vi_141733456'></div> <script type="1315743a9277122aa5d29ca1-text/javascript">(vitag.Init = window.vitag.Init || []).push(function () { viAPItag.display('vi_141733456') })</script></div></div></div></code></pre><div class='adsbyvli' data-ad-slot='vi_141733718'></div> <script type="1315743a9277122aa5d29ca1-text/javascript">(vitag.Init = window.vitag.Init || []).push(function () { viAPItag.display('vi_141733718') })</script><br /><br /></div></div><script defer type="1315743a9277122aa5d29ca1-text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script><script defer type="1315743a9277122aa5d29ca1-text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.3.8/jstree.min.js"></script><script defer type="1315743a9277122aa5d29ca1-text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/prettify/r298/prettify.js"></script><script defer type="1315743a9277122aa5d29ca1-text/javascript" src='https://cdnjs.cloudflare.com/ajax/libs/jquery.sticky/1.0.4/jquery.sticky.min.js'></script><script type="1315743a9277122aa5d29ca1-text/javascript">window.addEventListener("load",function(){
$(function(){$("#jstree").jstree();$("#jstree").on("click",".jstree-anchor",function(b){$(this).jstree(true).toggle_node(b.target)}).jstree();$("#jstree").bind("select_node.jstree",function(f,d){var e=d.node.a_attr.href;window.location=e})});jQuery(function(){var b=jQuery("#pull");menu=jQuery("nav>ul");menuHeight=menu.height();jQuery(b).on("click",function(a){a.preventDefault();menu.slideToggle()})});$("#code").scroll(function(){if($(this).scrollTop()>0){$("._bsa_flexbar").fadeOut()}else{$("._bsa_flexbar").fadeIn()}});(function(){if(typeof _bsa!=="undefined"&&_bsa){_bsa.init("stickybox","CKYICK3U","placement:wwwjavatipsnet")}})();(function(){if(typeof _bsa!=="undefined"&&_bsa){_bsa.init("flexbar","CKYICK3Y","placement:wwwjavatipsnet")}})();$("#sticky-parent").sticky({topSpacing:0,bottomSpacing:600});$("#sticky-parent").css({position:"fixed","margin-left":"50px"});});</script><script type="1315743a9277122aa5d29ca1-text/javascript"> var _gaq = _gaq || []; _gaq.push(['_setAccount', 'UA-22006649-1']); _gaq.push(['_trackPageview']);  (function() { var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true; ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js'; var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s); })(); </script><script src="/cdn-cgi/scripts/7d0fa10a/cloudflare-static/rocket-loader.min.js" data-cf-settings="1315743a9277122aa5d29ca1-|49" defer=""></script></body></html>