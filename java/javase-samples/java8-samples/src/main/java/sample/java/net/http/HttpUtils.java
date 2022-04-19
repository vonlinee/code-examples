package sample.java.net.http;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

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

	public void initTrustManager() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException,
			IOException {
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()); // Using
		// Get hold of the default trust manager
		X509TrustManager defaultTm = null;
		for (TrustManager tm : tmf.getTrustManagers()) {
			if (tm instanceof X509TrustManager) {
				defaultTm = (X509TrustManager) tm;
				break;
			}
		}
		FileInputStream myKeys = new FileInputStream("truststore.jks");
		// Do the same with your trust store this time Adapt how you load the keystore to your needs
		KeyStore myTrustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		myTrustStore.load(myKeys, "password".toCharArray());
		myKeys.close();
		tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(myTrustStore); 
		// Get hold of the default trust manager
		X509TrustManager myTm = null;
		for (TrustManager tm : tmf.getTrustManagers()) {
			if (tm instanceof X509TrustManager) {
				myTm = (X509TrustManager) tm;
				break;
			}
		}
		// Wrap it in your own class.
		final X509TrustManager finalDefaultTm = defaultTm;
		final X509TrustManager finalMyTm = myTm;
		final X509TrustManager customTm = new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				// If you're planning to use client-cert auth, merge results from "defaultTm" and "myTm".
				return finalDefaultTm.getAcceptedIssuers();
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				try {
					finalMyTm.checkServerTrusted(chain, authType);
				} catch (CertificateException e) {
					// This will throw another CertificateException if this fails too.
					finalDefaultTm.checkServerTrusted(chain, authType);
				}
			}

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException { 
				finalDefaultTm.checkClientTrusted(chain, authType);
			}
		};
		SSLContext sslContext = SSLContext.getInstance("TLS");
		// If You don't have to set this as the default context, it depends on the library you're using.
		sslContext.init(null, new TrustManager[] { customTm }, null); 
		SSLContext.setDefault(sslContext);
	}
}
