package sample.java.net.http;

import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * 基于JDK8的网络API
 */
public class HttpUtils {

	public static void main(String[] args) {
		System.out.println("输入URL =============================");
		
	}
	
	private static final int DEFAULT_TIME_OUT = 1000;
	
	private void defaultSettings(URLConnection connection) {
		connection.setDefaultUseCaches(true);
		connection.setDefaultRequestProperty("", null);
		connection.setConnectTimeout(DEFAULT_TIME_OUT);
		connection.setDefaultAllowUserInteraction(true);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setReadTimeout(DEFAULT_TIME_OUT);
	}
	
	public URLConnection openConnection(String url, Map<String, String> headers, Map<String, String> params) {
		URLConnection connection = null;
		try {
			connection = new URL(url).openConnection();
			// Request Header
			if (headers != null && !headers.isEmpty()) {
				for (String headerName : headers.keySet()) {
					connection.setConnectTimeout(300);
				}
			}
			// Request Param
			if (params != null && !params.isEmpty()) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					connection.addRequestProperty(entry.getKey(), entry.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		defaultSettings(connection);
		return connection;
	}
	
	public static <T> HttpResult<T> doPost() {
		HttpResult<T> result = new HttpResult<T>();
		return result;
	}
}
