package sample.java.net.tcp;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class Main {

	public static void main(String[] args) {
		
		X509TrustManager x509TrustManager = new X509TrustManager() {
			
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
			
			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				
			}
			
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				int i = chain.hashCode();
				if (i == 10) {
					System.out.println("Hello World!!");
				}
			}
		};

		
		
		
	}
}
