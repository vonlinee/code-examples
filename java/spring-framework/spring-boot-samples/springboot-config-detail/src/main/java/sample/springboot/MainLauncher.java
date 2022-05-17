package sample.springboot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import sample.springboot.config.props.Bean;

@SpringBootApplication
public class MainLauncher {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = null;
		try {
			context = SpringApplication.run(MainLauncher.class, args);
		} catch (Exception e) {
			System.exit(0);
		}

		Resource resource = context.getResource("C:\\Users\\ly-wangliang\\jdbc.properties");

		System.out.println(resource.exists());

	}

	public static void test1() {
		Bean bean = new Bean();
		Map<String, Object> map = new HashMap<>();
		map.put("age", "30");
		map.put("name", "Taylor");
		ConfigurationPropertySource source = new MapConfigurationPropertySource(map);
		ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
		aliases.addAliases("age", "jdbc-url");
		aliases.addAliases("name", "user");
		Binder binder = new Binder(source.withAliases(aliases));
		binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(bean));
		System.out.println(bean);
	}
}
