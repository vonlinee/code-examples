package org.example.springboot.common;

public enum ContentType {

	APPLICATION_JSON("application/json", "JSON"),
	HTML("text/html", "HTML");
	
	String type;
	String description;
	
	private ContentType(String type, String description) {
		this.type = type;
		this.description = description;
	}
}
