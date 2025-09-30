package apps.healthmanage.utils;

/**
 * 数据处理工具
 * @author huhailong
 *
 */
public class DataUtil {

	public static int getIndexForArray(String[]array, String item) {
		for(int i=0; i<array.length; i++) {
			if(array[i].equals(item)) {
				return i;
			}
		}
		return -1;
	}
}
