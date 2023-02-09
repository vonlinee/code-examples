package utils;

import javafx.scene.layout.BackgroundFill;

public class Printer {

	
	public static void printBackgroundFill(BackgroundFill fill) {
		StringBuilder sb = new StringBuilder();
		sb.append("Fill:").append(fill.getFill());
		sb.append("Insets:").append(fill.getInsets());
		sb.append("Radii:").append(fill.getRadii());
	}
}
