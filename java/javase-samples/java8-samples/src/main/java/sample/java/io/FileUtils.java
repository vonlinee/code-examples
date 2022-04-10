package sample.java.io;

import sun.nio.fs.WindowsFileSystemProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtils {
	
	private static final Logger LOG = Logger.getLogger(FileUtils.class.getName());
	
	public static boolean delete(File file) {
		Path path = Paths.get("");
		try {
			path.getFileSystem().provider().delete(path);
		} catch (IOException e) {
			if (e instanceof DirectoryNotEmptyException) {
				LOG.log(Level.SEVERE, "DirectoryNotEmptyException");
			}
		}
		return false;
	}
	
}
