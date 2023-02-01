package io.devpl.toolkit.fxui.bridge;

import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * @see org.mybatis.generator.internal.DefaultShellCallback
 */
public class InternalShellCallback implements ShellCallback {

    private final Log log = LogFactory.getLog(InternalShellCallback.class);

    private final boolean overwrite;

    public InternalShellCallback(boolean overwrite) {
        super();
        this.overwrite = overwrite;
    }

    @Override
    public File getDirectory(String targetProject, String targetPackage) throws ShellException {
        // targetProject is interpreted as a directory that must exist
        //
        // targetPackage is interpreted as a subdirectory, but in package
        // format (with dots instead of slashes). The subdirectory will be
        // created if it does not already exist

        File project = new File(targetProject);

        Path targetProjectPath = Path.of(targetProject).normalize();

        if (!Files.isDirectory(targetProjectPath)) {
            System.out.println("不是目录");
            throw new ShellException(getString("Warning.9", targetProject)); //$NON-NLS-1$
        }

        if (!Files.exists(targetProjectPath)) {
            try {
                Files.createDirectories(targetProjectPath);
            } catch (IOException e) {
                System.out.println("创建目录失败");
                throw new RuntimeException(e);
            }
        }

        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(targetPackage, "."); //$NON-NLS-1$
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken());
            sb.append(File.separatorChar);
        }

        File directory = new File(project, sb.toString());
        if (!directory.isDirectory()) {
            boolean rc = directory.mkdirs();
            if (!rc) {
                throw new ShellException(getString("Warning.10", directory.getAbsolutePath())); //$NON-NLS-1$
            }
        }
        return directory;
    }

    @Override
    public boolean isOverwriteEnabled() {
        return overwrite;
    }
}
