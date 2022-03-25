package org.example.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/ftp.properties")
@ConfigurationProperties(prefix = "ftp")
public class FTPConfiguration {
	
//	When using @ConfigurationProperties it is recommended to add 
//	'spring-boot-configuration-processor' to your classpath to generate configuration metadata
	
	private String host;
	private int port;
	private String username;
	private String password;
	private int connectTimeOut;
	private String controlEncoding;
	private int bufferSize;
	private int fileType;
	private int dataTimeout;
	private boolean useEPSVwithIPv4;
	private boolean passiveMode;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getConnectTimeOut() {
		return connectTimeOut;
	}

	public void setConnectTimeOut(int connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}

	public String getControlEncoding() {
		return controlEncoding;
	}

	public void setControlEncoding(String controlEncoding) {
		this.controlEncoding = controlEncoding;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public int getDataTimeout() {
		return dataTimeout;
	}

	public void setDataTimeout(int dataTimeout) {
		this.dataTimeout = dataTimeout;
	}

	public boolean isUseEPSVwithIPv4() {
		return useEPSVwithIPv4;
	}

	public void setUseEPSVwithIPv4(boolean useEPSVwithIPv4) {
		this.useEPSVwithIPv4 = useEPSVwithIPv4;
	}

	public boolean isPassiveMode() {
		return passiveMode;
	}

	public void setPassiveMode(boolean passiveMode) {
		this.passiveMode = passiveMode;
	}

	@Override
	public String toString() {
		return "FTPConfigBean [host=" + host + ", port=" + port + ", username=" + username + ", password=" + password
				+ ", connectTimeOut=" + connectTimeOut + ", controlEncoding=" + controlEncoding + ", bufferSize="
				+ bufferSize + ", fileType=" + fileType + ", dataTimeout=" + dataTimeout + ", useEPSVwithIPv4="
				+ useEPSVwithIPv4 + ", passiveMode=" + passiveMode + "]";
	}
}
