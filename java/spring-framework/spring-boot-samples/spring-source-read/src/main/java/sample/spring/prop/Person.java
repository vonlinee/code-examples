package sample.spring.prop;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component // 或者@Configuration
@ConfigurationProperties(prefix = "person")
public class Person {
	private Map<String, String> maps;
	private List<String> list;
	private String name;

	public Map<String, String> getMaps() {
		return maps;
	}

	public void setMaps(Map<String, String> maps) {
		this.maps = maps;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
