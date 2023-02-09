package utils;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;

public class FxmlLoader {

	/**
	 * 加载FXML
	 * @param <T>
	 * @param clazz
	 * @param name
	 * @return
	 */
	public static <T> T load(Class<?> clazz, String name) {
		URL url = ResourceLoader.load(clazz, name);
		if (url == null) {
			throw new RuntimeException("scenegraph.fxml load failed: " + name);
		}
		try {
			return FXMLLoader.load(url);
		} catch (IOException e) {
			throw new RuntimeException("scenegraph.fxml load failed: " + name + "\n" + e.getMessage());
		}
	}
	
	public static FXMLLoader getFxmlLoader(String name) {
		URL url = ResourceLoader.load(name);
		if (url == null) {
			throw new RuntimeException("scenegraph.fxml load failed: " + name);
		}
		try {
			FXMLLoader loader = new FXMLLoader(url);
			loader.load();
			return loader;
		} catch (IOException e) {
			throw new RuntimeException("scenegraph.fxml load failed: " + name + "\n" + e.getMessage());
		}
	}
	
	public static FXMLLoader getFxmlLoader(Class<?> clazz, String name) {
		URL url = null;
		try {
			url = ResourceLoader.load(clazz, name);
		} catch (Exception e) {
			throw new RuntimeException("scenegraph.fxml load failed: " + name + ", maybe it does not exist!\n" + e.getMessage());
		}
		try {
			FXMLLoader loader = new FXMLLoader(url);
			loader.load();
			return loader;
		} catch (IOException e) {
			throw new RuntimeException("scenegraph.fxml load failed: " + name + "\n" + e.getMessage());
		}
	}
}
