package io.maker.codegen.mbp.fill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * XML标签实体类映射
 */
public class XMLTag implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private boolean closed = false; // 是否封闭，例如<img src="..."/>，默认为false
	private List<Attribute> attributes;
	private String content;
	private List<XMLTag> directSubTags;

	public XMLTag() {
		this.attributes = new ArrayList<>();
		this.directSubTags = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<XMLTag> getDirectSubTags() {
		return directSubTags;
	}

	public void setDirectSubTags(List<XMLTag> directSubTags) {
		this.directSubTags = directSubTags;
	}
}
