package code.pocket.base.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {
	
	public static List<File> listFiles(File directory, FileFilter filter) {
		File[] files = directory.listFiles(filter);
		List<File> fileList = new ArrayList<>();
		listFiles(fileList, directory, filter);
		for (File file : files) {
			if (file.isDirectory()) {
				listFiles(fileList, directory, filter);
			}
		}
		return fileList;
	}
	
	public static void listFiles(List<File> fileList, File directory, FileFilter filter) {
		File[] files = directory.listFiles(filter);
		if (Validator.isEmpty(files)) {
			return;
		}
		fileList.addAll(Arrays.asList(files));
	}
	
	public static List<File> listFilesOf(File directory, FileFilter filter) {
		File[] files = directory.listFiles(filter);
		if (Validator.isEmpty(files)) {
			return new ArrayList<>(0);
		}
		return Arrays.asList(files);
	}
}
