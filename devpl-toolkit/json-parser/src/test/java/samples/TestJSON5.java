package samples;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import de.marhali.json5.Json5;
import de.marhali.json5.Json5Element;

public class TestJSON5 {

	@Test
	public void test1() {
		String absolutePath = new File("").getAbsolutePath();
		String parent = new File(absolutePath).getParent();
		File jsonFile = new File(parent + "/test.json");
		Json5 json5 = new Json5();
		
		try (InputStream is = new FileInputStream(jsonFile)){
			Json5Element root = json5.parse(is);
			
			System.out.println(root);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
