package code.example.java.newfeature.steam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestStreamApi {
	public static void main(String[] args) {
		
		List<String> list = new ArrayList<>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		
		Stream<String> stream = list.stream();
		
		stream.forEach(item -> {
			System.out.println(item);
		});
		
	}
}
