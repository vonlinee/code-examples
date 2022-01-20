package code.pocket.base.utils;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {
	
	
	
	public static <T> List<T> emptyArrayList() {
		return new ArrayList<T>(0);
	}
	
	public static <T> List<T> arrayList(int intialCapacity) {
		return new ArrayList<T>(intialCapacity);
	}
}
