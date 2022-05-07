package sample.spring.boot.token.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

//@Configuration
public class MyBatisConfiguration {

	@Autowired
	private Environment env;

	private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

	public static String setTypeAliasesPackage(String typeAliasesPackage) {
		ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
		List<String> allResult = new ArrayList<>();
		try {
			for (String aliasesPackage : typeAliasesPackage.split(",")) {
				List<String> result = new ArrayList<>();
				aliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
						+ ClassUtils.convertClassNameToResourcePath(aliasesPackage.trim()) + "/"
						+ DEFAULT_RESOURCE_PATTERN;
				Resource[] resources = resolver.getResources(aliasesPackage);
				if (resources != null && resources.length > 0) {
					MetadataReader metadataReader = null;
					for (Resource resource : resources) {
						if (resource.isReadable()) {
							metadataReader = metadataReaderFactory.getMetadataReader(resource);
							try {
								result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage()
										.getName());
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
						}
					}
				}
				if (!result.isEmpty()) {
					HashSet<String> hashResult = new HashSet<>(result);
					allResult.addAll(hashResult);
				}
			}
			if (!allResult.isEmpty()) {
				typeAliasesPackage = String.join(",", (String[]) allResult.toArray(new String[0]));
			} else {
				throw new RuntimeException(
						"mybatis typeAliasesPackage 路径扫描错误,参数typeAliasesPackage:” + typeAliasesPackage + “未找到任何包");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return typeAliasesPackage;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		String typeAliasesPackage = env.getProperty("mybatis.typeAliasesPackage");
		String mapperLocations = env.getProperty("mybatis.mapperLocations");
		String configLocation = env.getProperty("mybatis.configLocation");
		typeAliasesPackage = setTypeAliasesPackage(typeAliasesPackage);
		VFS.addImplClass(SpringBootVFS.class);
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
		if (mapperLocations == null || mapperLocations.length() == 0) {
			mapperLocations = "classpath:mybatis/mapping/*.xml";
		}
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
		sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
		return sessionFactory.getObject();
	}
}
