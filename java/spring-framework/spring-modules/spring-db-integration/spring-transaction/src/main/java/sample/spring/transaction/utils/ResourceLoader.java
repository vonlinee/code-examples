package sample.spring.transaction.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ResourceLoader {

	public static final String PROJECT_ROOT_PATH = new File("").getAbsolutePath();
	
	public static URL load(String name) {
		String prefix = "";
		if (isMavenProject()) {
			prefix = "src/main/resources/";
		}
		return null;
	}
	
	public static File loadLocalFile(Class<?> clazz, String name) throws FileNotFoundException {
		String prefix = "";
		if (isMavenProject()) {
			prefix = "src/main/java/";
		}
		String packagePath = clazz.getPackage().getName().replace(".", "/");
		String location = PROJECT_ROOT_PATH + prefix + packagePath + File.separator + name;
		File file = new File(location);
		if (file.exists()) {
			return file;
		}
		throw new FileNotFoundException(file.getAbsolutePath());
	}

	public static boolean isMavenProject() {
		File file = new File(PROJECT_ROOT_PATH);
		File[] files = file.listFiles(path -> path.isFile() && "pom.xml".equals(path.getName()));
		return files != null && files.length == 1;
	}
	
	public static ApplicationContext loadSpringXml(Class<?> mainClass) {
		try {
			File file = loadLocalFile(mainClass, "spring.xml");
			return new FileSystemXmlApplicationContext(file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
