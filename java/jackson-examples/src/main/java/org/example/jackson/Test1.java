package org.example.jackson;

import java.io.IOException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Test1 {

	JsonNodeFactory factory = JsonNodeFactory.instance;

	@Test
	public void test1() {
		System.out.println("------ValueNode值节点示例------");
		// 数字节点
		JsonNode node = factory.numberNode(1);
		System.out.println(node.isNumber() + ":" + node.intValue());

		// null节点
		node = factory.nullNode();
		System.out.println(node.isNull() + ":" + node.asText());

		// missing节点
//		node = factory.missingNode();
//		System.out.println(node.isMissingNode() + "_" + node.asText());

		// POJONode节点
		node = factory.pojoNode(new Person("YourBatman", 18, null));
		System.out.println(node.isPojo() + ":" + node.asText());

		System.out.println("---" + node.isValueNode() + "---");
	}

	@Test
	public void test2() {
		System.out.println("------构建一个JSON结构数据------");
		ObjectNode rootNode = factory.objectNode();

		// 添加普通值节点
		rootNode.put("zhName", "A哥"); // 效果完全同：rootNode.set("zhName", factory.textNode("A哥"))
		rootNode.put("enName", "YourBatman");
		rootNode.put("age", 18);

		// 添加数组容器节点
		ArrayNode arrayNode = factory.arrayNode();
		arrayNode.add("java").add("javascript").add("python");
		rootNode.set("languages", arrayNode);

		// 添加对象节点
		ObjectNode dogNode = factory.objectNode();
		dogNode.put("name", "大黄").put("age", 3);
		rootNode.set("dog", dogNode);

		System.out.println(rootNode);
		System.out.println(rootNode.get("dog").get("name"));
	}

	ObjectMapper mapper = new ObjectMapper();

	@Test
	public void test3() {
		Person person = new Person();
		person.setName("YourBatman");
		person.setAge(18);

		person.setDog(new Dog("旺财", 3));

		JsonNode node = mapper.valueToTree(person);

		System.out.println(person);
		// 遍历打印所有属性
		Iterator<JsonNode> it = node.iterator();
		while (it.hasNext()) {
			JsonNode nextNode = it.next();
			if (nextNode.isContainerNode()) {
				if (nextNode.isObject()) {
					System.out.println("狗的属性：：：");

					System.out.println(nextNode.get("name"));
					System.out.println(nextNode.get("age"));
				}
			} else {
				System.out.println(nextNode.asText());
			}
		}

		// 直接获取
		System.out.println("---------------------------------------");
		System.out.println(node.get("dog").get("name"));
		System.out.println(node.get("dog").get("age"));

	}

	@Test
	public void test4() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = new JsonFactory();
		try (JsonGenerator jsonGenerator = factory.createGenerator(System.err, JsonEncoding.UTF8)) {

			// 1、得到一个jsonNode（为了方便我直接用上面API生成了哈）
			Person person = new Person();
			person.setName("YourBatman");
			person.setAge(18);
			JsonNode jsonNode = mapper.valueToTree(person);

			// 使用JsonGenerator写到输出流
			mapper.writeTree(jsonGenerator, jsonNode);
		}

	}

	@Test
	public void test5() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = "{\"name\":\"YourBatman\",\"age\":18,\"dog\":null}";
		// 直接映射为一个实体对象
		// mapper.readValue(jsonStr, Person.class);
		// 读取为一个树模型
		JsonNode node = mapper.readTree(jsonStr);

		// ... 略
	}
	
	@Test
	public void test6() throws JsonProcessingException, IOException {
		String jsonStr = "{\"name\":\"YourBatman\",\"age\":18,\"dog\":{\"name\":\"旺财\",\"color\":\"WHITE\"},\"hobbies\":[\"篮球\",\"football\"]}";
		JsonNode node = mapper.readTree(jsonStr);

		System.out.println(node.get("dog").get("color").asText());

	}
	
	@Test
	public void test7() throws JsonProcessingException, IOException {
		String jsonStr = "{\"name\":\"YourBatman\",\"age\":18}";
		JsonNode node = new ObjectMapper().readTree(jsonStr);

		System.out.println("-------------向结构里动态添加节点------------");
		// 动态添加一个myDiy节点，并且该节点还是ObjectNode节点
		((ObjectNode) node).with("myDiy").put("contry", "China");

		System.out.println(node);
	}
}
