package tools.file;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.*;
import java.util.Objects;
import java.util.Set;

public final class FileUtils {
    private static final String PROTOCOL_FILE = "file";
    private static final String JBOSS_FILE = "vfsfile";

    private FileUtils() {
    }

    public static File fileFromUri(URI uri) {
        if (uri != null && (uri.getScheme() == null || "file".equals(uri.getScheme()) || "vfsfile".equals(uri.getScheme()))) {
            String path;
            if (uri.getScheme() == null) {
                File file = new File(uri.toString());
                if (file.exists()) {
                    return file;
                }

                try {
                    path = uri.getPath();
                    file = new File(path);
                    if (file.exists()) {
                        return file;
                    }

                    uri = (new File(path)).toURI();
                } catch (Exception var5) {
                    // LOGGER.warn("Invalid URI {}", uri);
                    return null;
                }
            }

            String charsetName = StandardCharsets.UTF_8.name();

            try {
                path = uri.toURL().getFile();
                if ((new File(path)).exists()) {
                    return new File(path);
                }

                path = URLDecoder.decode(path, charsetName);
                return new File(path);
            } catch (MalformedURLException var3) {
                // LOGGER.warn("Invalid URL {}", uri, var3);
            } catch (UnsupportedEncodingException var4) {
                // LOGGER.warn("Invalid encoding: {}", charsetName, var4);
            }
            return null;
        } else {
            return null;
        }
    }

    public static boolean isFile(URL url) {
        return url != null && (url.getProtocol().equals("file") || url.getProtocol().equals("vfsfile"));
    }

    public static String getFileExtension(File file) {
        String fileName = file.getName();
        return fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0 ? fileName.substring(fileName.lastIndexOf(".") + 1) : null;
    }

    public static void mkdir(File dir, boolean createDirectoryIfNotExisting) throws IOException {
        if (!dir.exists()) {
            if (!createDirectoryIfNotExisting) {
                throw new IOException("The directory " + dir.getAbsolutePath() + " does not exist.");
            }

            if (!dir.mkdirs()) {
                throw new IOException("Could not create directory " + dir.getAbsolutePath());
            }
        }

        if (!dir.isDirectory()) {
            throw new IOException("File " + dir + " exists and is not a directory. Unable to create directory.");
        }
    }

    public static void makeParentDirs(File file) throws IOException {
        File parent = ((File) Objects.requireNonNull(file, "file")).getCanonicalFile().getParentFile();
        if (parent != null) {
            mkdir(parent, true);
        }
    }

    public static void defineFilePosixAttributeView(Path path, Set<PosixFilePermission> filePermissions, String fileOwner, String fileGroup) throws IOException {
        PosixFileAttributeView view = (PosixFileAttributeView) Files.getFileAttributeView(path, PosixFileAttributeView.class);
        if (view != null) {
            UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
            if (fileOwner != null) {
                UserPrincipal userPrincipal = lookupService.lookupPrincipalByName(fileOwner);
                if (userPrincipal != null) {
                    view.setOwner(userPrincipal);
                }
            }

            if (fileGroup != null) {
                GroupPrincipal groupPrincipal = lookupService.lookupPrincipalByGroupName(fileGroup);
                if (groupPrincipal != null) {
                    view.setGroup(groupPrincipal);
                }
            }

            if (filePermissions != null) {
                view.setPermissions(filePermissions);
            }
        }
    }

    public static boolean isFilePosixAttributeViewSupported() {
        return FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
    }
}
