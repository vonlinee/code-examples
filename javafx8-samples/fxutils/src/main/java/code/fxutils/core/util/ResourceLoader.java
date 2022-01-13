package code.fxutils.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class ResourceLoader {
	
	/**
	 * 
	 * @param path
	 * @return
	 * Properties
	 */
	public static Properties loadProperties(String path) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
	
	public static File loadXML(String path) {
		return null;
	}
	
	
	
	
}
