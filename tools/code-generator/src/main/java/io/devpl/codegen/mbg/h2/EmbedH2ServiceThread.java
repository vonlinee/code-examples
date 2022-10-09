package io.devpl.codegen.mbg.h2;

import org.h2.server.web.WebServer;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>
 * https://www.letianbiji.com/java/java-h2-database.html
 * </p>
 * https://blog.csdn.net/wangmx1993328/article/details/87965971
 */
public class EmbedH2ServiceThread extends Thread {

    private Server h2ConsoleServer;

    private Server h2TcpServer;

    private static final String WEB_ADMIN_PASSWORD = "123456";

    public EmbedH2ServiceThread() {
        setName("h2-server");
        setDaemon(true);
    }

    private final Logger log = LoggerFactory.getLogger(EmbedH2ServiceThread.class);

    @Override
    public void run() {
        startConsoleWebServer();
        startTcpServer();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> h2ConsoleServer.shutdown()));
    }

    private void startConsoleWebServer() {
        try {
            h2ConsoleServer = new Server(new WebServer(), "-webAllowOthers", "-webAdminPassword", WEB_ADMIN_PASSWORD);
            h2ConsoleServer.setShutdownHandler(() -> log.info("h2 server shutdown"));
            h2ConsoleServer.start();
        } catch (SQLException throwables) {
            log.error("failed to start h2 console server", throwables);
        }
        if (h2ConsoleServer.isRunning(true)) {
            log.info("h2 server is running, web console => {}", h2ConsoleServer.getURL());
        }
    }

    private void startTcpServer() {
        try {
            h2TcpServer = Server.createTcpServer("-ifNotExists", "-tcpPort", "8888", "-tcpAllowOthers");
            h2TcpServer.start();
        } catch (SQLException throwables) {
            log.error("failed to start h2 tcp server", throwables);
        }
        if (h2TcpServer.isRunning(true)) {
            log.info("h2 server is running, tcp server => {}", h2TcpServer.getURL());
            String url = "jdbc:h2:" + h2TcpServer.getURL() + "/~";
            try (Connection connection = DriverManager.getConnection(url, "root", "123456");
                 Statement statement = connection.createStatement()) {
                // 以 Generic H2 (Embedded) 方式进行连接
                boolean result = statement.execute("create user if not exists root password '123456'");
                boolean result2 = statement.execute("alter user root admin true");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        // Generic H2 (Server)
    }
}
