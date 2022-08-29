package io.devpl.sdk.support.spring.db;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class EmbedH2Configuration {

    @Bean
    public Server h2Server() {
        Server server = null;
        try {
            server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-ifNotExists", "-tcpPort", "6666");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return server;
    }
}
