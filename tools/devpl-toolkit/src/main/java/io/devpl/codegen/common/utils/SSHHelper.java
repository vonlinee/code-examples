package io.devpl.codegen.common.utils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import io.devpl.codegen.ui.fxui.model.DatabaseConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Optional;
import java.util.Properties;

import static io.devpl.codegen.common.utils.Validator.isBlank;

public class SSHHelper {

    private static final JSch jsch = new JSch();

    private static final Properties DEFAULT_CONFIG = new Properties();

    static {
        // Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
        DEFAULT_CONFIG.put("StrictHostKeyChecking", "no");
    }

    public static Optional<Session> openSession(String username, String host, int port) {
        Session session = null;
        try {
            session = jsch.getSession(username, host, port);
            session.setConfig(DEFAULT_CONFIG);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(session);
    }

    public static Session openSession1(DatabaseConfiguration databaseConfig) {
        Session session = null;
        try {
            Integer sshPort = Integer.decode(databaseConfig.getSshPort());
            session = jsch.getSession(databaseConfig.getSshUser(), databaseConfig.getSshHost(), sshPort);
            if (!isBlank(databaseConfig.getPrivateKey())) {
                //使用秘钥方式认证
                jsch.addIdentity(databaseConfig.getPrivateKey(), StringUtils.defaultIfBlank(databaseConfig.getPrivateKeyPassword(), null));
            } else {
                session.setPassword(databaseConfig.getSshPassword());
            }
            session.setConfig(DEFAULT_CONFIG);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return session;
    }

    public static Session openSession(DatabaseConfiguration databaseConfig) {
        if (StringUtils.isBlank(databaseConfig.getSshHost())
                || StringUtils.isBlank(databaseConfig.getSshPort())
                || StringUtils.isBlank(databaseConfig.getSshUser())
                || (StringUtils.isBlank(databaseConfig.getPrivateKey()) && StringUtils.isBlank(databaseConfig.getSshPassword()))
        ) {
            return null;
        }
        Session session = null;
        try {
            //Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
            Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Integer sshPort = NumberUtils.createInteger(databaseConfig.getSshPort());
            int port = sshPort == null ? 22 : sshPort;
            session = jsch.getSession(databaseConfig.getSshUser(), databaseConfig.getSshHost(), port);
            if (StringUtils.isNotBlank(databaseConfig.getPrivateKey())) {
                //使用秘钥方式认证
                jsch.addIdentity(databaseConfig.getPrivateKey(), StringUtils.defaultIfBlank(databaseConfig.getPrivateKeyPassword(), null));
            } else {
                session.setPassword(databaseConfig.getSshPassword());
            }
            session.setConfig(config);
        } catch (JSchException e) {
            //Ignore
        }
        return session;
    }
}