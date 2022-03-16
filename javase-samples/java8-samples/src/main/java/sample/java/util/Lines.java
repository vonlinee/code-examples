package sample.java.util;

import java.util.List;

public class Lines {

	private List<String> lines;
	
	public static Lines of(List<String> lines) {
		Lines textLines = new Lines();
		textLines.setLines(lines);
		return textLines;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}
}
